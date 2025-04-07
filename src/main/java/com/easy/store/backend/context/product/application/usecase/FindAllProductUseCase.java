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
public class FindAllProductUseCase {

    private final ProductRepository productRepository;

    public List<Product> findAll() throws NoResultsException {

        log.info("ACCION FINDALL PRODUCT -> Iniciando búsqueda");

        List<Product> products = productRepository.findAll();
        if(products == null || products.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION FINDALL PRODUCT -> Encontré productos con éxito");

        return products;
    }

}
