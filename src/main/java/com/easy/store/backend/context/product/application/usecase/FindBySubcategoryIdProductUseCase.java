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
public class FindBySubcategoryIdProductUseCase {

    private final Logger logger = Logger.getLogger(FindBySubcategoryIdProductUseCase.class.getName());

    private final ProductRepository productRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public List<Product> findBySubcategoryId(Long subcategoryId) throws NoResultsException {

        logger.info("ACCION FINDBYSUBCATEGORYID PRODUCT -> Iniciando búsqueda con id: " + subcategoryId);

        List<Product> products = productRepository.findBySubcategoryId(subcategoryId);
        if(products == null || products.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        logger.info("ACCION FINDBYSUBCATEGORYID PRODUCT -> Encontré productos con éxito");

        return products;
    }

}
