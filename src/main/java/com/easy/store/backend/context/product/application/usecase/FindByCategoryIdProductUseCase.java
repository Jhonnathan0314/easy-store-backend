package com.easy.store.backend.context.product.application.usecase;

import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.product.domain.port.ProductRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FindByCategoryIdProductUseCase {

    private final Logger logger = Logger.getLogger(FindByCategoryIdProductUseCase.class.getName());

    private final ProductRepository productRepository;

    public List<Product> findByCategoryId(Long categoryId) throws NoResultsException {

        logger.info("ACCION FINDBYCATEGORYID PRODUCT -> Iniciando búsqueda con id: " + categoryId);

        List<Product> products = productRepository.findByCategoryId(categoryId);
        if(products == null || products.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        logger.info("ACCION FINDBYCATEGORYID PRODUCT -> Encontré productos con éxito");

        return products;
    }

}
