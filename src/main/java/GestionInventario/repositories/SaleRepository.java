package GestionInventario.repositories;

import GestionInventario.domain.entities.Product;
import GestionInventario.domain.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SaleRepository extends CrudRepository<Sale, Integer> {
    List<Sale> getAllByProduct(Product product);
}
