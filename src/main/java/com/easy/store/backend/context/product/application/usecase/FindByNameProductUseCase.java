package com.easy.store.backend.context.product.application.usecase;

import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.product.domain.port.ProductRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FindByNameProductUseCase {

    private final Logger logger = Logger.getLogger(FindByNameProductUseCase.class.getName());

    private final ProductRepository productRepository;

    public Optional<Product> findByName(String name) throws NoResultsException {

        logger.info("ACCION FINDBYNAME PRODUCT -> Iniciando búsqueda");

        Optional<Product> optionalProduct = productRepository.findByName(name);
        if(optionalProduct.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        logger.info("ACCION FINDBYNAME PRODUCT -> Encontré producto con éxito");

        return productRepository.findByName(name);
    }

}
