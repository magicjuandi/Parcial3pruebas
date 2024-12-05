package GestionInventario.services;

import GestionInventario.domain.entities.Product;
import GestionInventario.domain.entities.Sale;

import java.util.List;

public interface SaleService {
    Sale saveSale(Sale sale);
    List<Sale> getAllSalesByProduct(Product product);
}
