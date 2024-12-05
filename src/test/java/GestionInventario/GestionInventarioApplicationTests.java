package GestionInventario;

import GestionInventario.domain.entities.Product;
import GestionInventario.domain.entities.Sale;
import GestionInventario.services.ProductService;
import GestionInventario.services.SaleService;
import GestionInventario.services.impl.ProductServiceImpl;
import GestionInventario.services.impl.SaleServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class GestionInventarioApplicationTests {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private MockMvc mockMvc;
	@LocalServerPort
	private int port;
	@MockBean
	private ProductService productService;
	@MockBean
	private SaleService saleService;
    @InjectMocks
	private ProductServiceImpl productServiceImpl;
	@InjectMocks
	private SaleServiceImpl saleServiceImpl;
	private final Product product = new Product();
	private final Sale sale = new Sale();
	private final java.util.List<Product> products = new ArrayList<>();
	private final java.util.List<Sale> sales = new ArrayList<>();

	@Test
	void setUp() {
		RestAssured.port = port;
	}
	@Test
	void testDatabaseConnection() throws Exception {
		assertNotNull(dataSource, "El DataSource debe estar configurado");
		try(Connection connection = dataSource.getConnection()) {
			assertNotNull(connection, "La conexión a la BD es exitosa");
		}
	}
	@Test
	void getProduct() throws Exception {
		Product product = new Product();
		product.setId_product(1);
		Mockito.when(productService.findById(1)).thenReturn(product);
		mockMvc.perform(get("/products/byId/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	@Test
	void deleteProduct(){
		product.setId_product(1);
		product.setName("Producto Nuevo");
		Mockito.when(productService.deleteProduct(1)).thenReturn("Product Deleted");
		RestAssured.given()
				.when()
				.delete("/products/delete/"+ product.getId_product())
				.then()
				.statusCode(200)
				.contentType(ContentType.TEXT);
	}
	@Test
	void createProduct(){
		product.setId_product(1);
		product.setName("Producto Nuevo");
		Mockito.when(productService.saveProduct(any(Product.class))).thenReturn(product);
		RestAssured.given()
				.contentType(ContentType.JSON)
				.body(product)
				.when()
				.post("/products/create")
				.then()
				.statusCode(200);
	}
	@Test
	void updateProduct() throws Exception {
		product.setId_product(1);
		product.setName("Producto Nuevo");
		Mockito.when(productService.updateProduct(anyInt(),anyString())).thenReturn(product);
		mockMvc.perform(put("/products/update/"+product.getId_product())
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(product)))
				.andExpect(status().isOk());
	}
	@Test
	void getProducts() throws Exception {
		product.setId_product(1);
		product.setName("Producto Nuevo");
		products.add(product);
		Mockito.when(productService.findAll()).thenReturn(products);
		mockMvc.perform(get("/products/get")
		.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	@Test
	void getSales() {
		product.setId_product(1);
		product.setName("Producto Nuevo");
		sale.setId_sale(1);
		sale.setProduct(product);
		sales.add(sale);
		Mockito.when(saleService.getAllSalesByProduct(product)).thenReturn(sales);
		RestAssured.given()
				.contentType(ContentType.JSON)
				.body(sales)
				.when()
				.post("/sales/getByProduct")
				.then()
				.statusCode(200);
	}
	@Test
	void createSale() {
		sale.setId_sale(1);
		sale.setProduct(product);
		Mockito.when(saleService.saveSale(any(Sale.class))).thenReturn(sale);
		RestAssured.given()
				.contentType(ContentType.JSON)
				.body(sale)
				.when()
				.post("/sales/create")
				.then()
				.statusCode(200);
	}

	/////////////////////////////

	@Test
	public void testSaveProduct(){
		Product product = new Product();
		product.setId_product(1);
		product.setName("New Product");
		Mockito.when(productService.saveProduct(any())).thenReturn(product);
		Product result = productServiceImpl.saveProduct(product);
		assertNotNull(result);
		verify(productService, times(1)).saveProduct(any());
	}
	@Test
	public void testUpdateProduct(){
		Product product = new Product();
		product.setId_product(1);
		product.setName("New Product");
		Mockito.when(productService.findById(anyInt())).thenReturn(product);
		Mockito.when(productService.saveProduct(any())).thenReturn(product);
		Product result = productServiceImpl.updateProduct(product.getId_product(), "New Product Name");
		assertNotNull(result);
		verify(productService, times(1)).saveProduct(any());
	}
	@Test
	public void testDeleteProduct(){
		int productId = 1;
		Product productNew = new Product();
		productNew.setId_product(productId);
		productNew.setName("New Product");
		Mockito.when(productService.findById(anyInt())).thenReturn(productNew);
		String result = productServiceImpl.deleteProduct(productId);
		assertNotNull(result);
		verify(productService, times(1)).deleteProduct(productId);
	}
	@Test
	public void testFindProduct(){
		Product product = new Product();
		product.setId_product(1);
		product.setName("New Product");
		Mockito.when(productService.findById(1)).thenReturn(product);
		Product result = productServiceImpl.findById(product.getId_product());
		assertNotNull(result);
		verify(productService, times(1)).findById(1);
	}
	@Test
	public void testFindProducts(){
		Product product = new Product();
		product.setId_product(1);
		product.setName("New Product");
		products.add(product);
		Mockito.when(productService.findAll()).thenReturn(products);
		List<Product> result = productServiceImpl.findAll();
		assertNotNull(result);
		verify(productService, times(1)).findAll();
	}
	@Test
	public void testCreateSale(){
		Sale sale = new Sale();
		sale.setId_sale(1);
		sale.setProduct(product);
		Mockito.when(saleService.saveSale(any())).thenReturn(sale);
		Sale result = saleServiceImpl.saveSale(sale);
		assertNotNull(result);
		verify(saleService, times(1)).saveSale(any());
	}
	@Test
	public void testFindSales() {
		Sale sale = new Sale();
		sale.setId_sale(1);
		sale.setProduct(product);
		sales.add(sale);
		Mockito.when(saleServiceImpl.getAllSalesByProduct(product)).thenReturn(sales);
		List<Sale> result = saleServiceImpl.getAllSalesByProduct(product);
		assertNotNull(result);
		verify(saleService, times(1)).getAllSalesByProduct(product);
	}
}
