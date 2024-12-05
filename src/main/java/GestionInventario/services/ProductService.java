package GestionInventario.services;

import GestionInventario.domain.entities.Product;

import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);
    Product updateProduct(int id, String name);
    String deleteProduct(int id);
    List<Product> findAll();
    Product findById(int id);
}
