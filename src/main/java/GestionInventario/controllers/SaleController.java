package GestionInventario.controllers;

import GestionInventario.domain.entities.Product;
import GestionInventario.domain.entities.Sale;
import GestionInventario.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class SaleController {
    @Autowired
    private SaleService saleService;
    @PostMapping("/create")
    public ResponseEntity<Sale> create(@RequestBody Sale sale) {
        return ResponseEntity.ok(saleService.saveSale(sale));
    }
    @PostMapping("/getByProduct")
    public ResponseEntity<List<Sale>> getByProduct(@RequestBody Product product) {
        return ResponseEntity.ok(saleService.getAllSalesByProduct(product));
    }
}
