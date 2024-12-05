package GestionInventario.services.impl;

import GestionInventario.domain.entities.Product;
import GestionInventario.domain.entities.Sale;
import GestionInventario.repositories.SaleRepository;
import GestionInventario.services.SaleService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    public SaleServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }
    @Override
    public Sale saveSale(Sale sale) {
        return saleRepository.save(sale);
    }

    @Override
    public List<Sale> getAllSalesByProduct(Product product) {
        return saleRepository.getAllByProduct(product);
    }
}
