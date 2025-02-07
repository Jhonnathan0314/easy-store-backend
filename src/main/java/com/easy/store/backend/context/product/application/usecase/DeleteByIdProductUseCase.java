package com.easy.store.backend.context.product.application.usecase;

import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.product.domain.port.ProductRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class DeleteByIdProductUseCase {

    private final Logger logger = Logger.getLogger(DeleteByIdProductUseCase.class.getName());

    private final ProductRepository productRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public void deleteById(Long id) throws NonExistenceException {

        logger.info("ACCION DELETEBYID PRODUCT -> Iniciando proceso con id: " + id);

        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()) throw new NonExistenceException(errorMessages.NON_EXISTENT_DATA);
        logger.info("ACCION DELETEBYID PRODUCT -> Produto encontrado con éxito");

        logger.info("ACCION DELETEBYID PRODUCT -> Eliminando producto");

        productRepository.deleteById(id);
    }

}
