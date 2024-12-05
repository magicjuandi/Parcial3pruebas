package GestionInventario.services.impl;

import GestionInventario.domain.entities.Product;
import GestionInventario.repositories.ProductRepository;
import GestionInventario.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(int id, String name) {
        Product product = productRepository.findById(id).get();
        product.setName(name);
        return productRepository.save(product);
    }

    @Override
    public String deleteProduct(int id) {
        productRepository.deleteById(id);
        return "Product Deleted";
    }

    @Override
    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Product findById(int id) {
        return productRepository.findById(id).get();
    }

}
