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
public class FindByIdProductUseCase {

    private final Logger logger = Logger.getLogger(FindByIdProductUseCase.class.getName());

    private final ProductRepository productRepository;

    public Product findById(Long id) throws NoResultsException {

        logger.info("ACCION FINDBYID PRODUCT -> Iniciando búsqueda con id: " + id);

        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        logger.info("ACCION FINDBYID PRODUCT -> Encontré producto con éxito");

        return optionalProduct.get();
    }

}
