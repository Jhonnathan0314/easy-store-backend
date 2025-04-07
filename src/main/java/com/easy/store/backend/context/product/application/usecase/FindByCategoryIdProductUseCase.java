package com.easy.store.backend.context.product.application.usecase;

import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.product.domain.port.ProductRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class FindByCategoryIdProductUseCase {

    private final ProductRepository productRepository;

    public List<Product> findByCategoryId(Long categoryId) throws NoResultsException {

        log.info("ACCION FINDBYCATEGORYID PRODUCT -> Iniciando búsqueda con id: {}", categoryId);

        List<Product> products = productRepository.findByCategoryId(categoryId);
        if(products == null || products.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION FINDBYCATEGORYID PRODUCT -> Encontré productos con éxito");

        return products;
    }

}
