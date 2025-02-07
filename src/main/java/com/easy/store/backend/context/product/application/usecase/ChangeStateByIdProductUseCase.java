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
public class ChangeStateByIdProductUseCase {

    private final Logger logger = Logger.getLogger(ChangeStateByIdProductUseCase.class.getName());

    private final ProductRepository productRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Product changeStateById(Long id) throws NonExistenceException {

        logger.info("ACCION CHANGESTATEBYID PRODUCT -> Iniciando proceso con id: " + id);

        Optional<Product> optProduct = productRepository.findById(id);
        if(optProduct.isEmpty()) throw new NonExistenceException(errorMessages.NON_EXISTENT_DATA);
        logger.info("ACCION CHANGESTATEBYID PRODUCT -> Producto encontrado con éxito");

        Product product = optProduct.get();
        product.setState(product.getState().equals("active") ? "inactive" : "active");

        logger.info("ACCION CHANGESTATEBYID PRODUCT -> Actualizando estado");
        return productRepository.update(product);
    }

}
