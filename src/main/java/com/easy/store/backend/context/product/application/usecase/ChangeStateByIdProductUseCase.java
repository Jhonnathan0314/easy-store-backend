package com.easy.store.backend.context.product.application.usecase;

import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.product.domain.port.ProductRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ChangeStateByIdProductUseCase {

    private final ProductRepository productRepository;

    public Product changeStateById(Long id, Long updateBy) throws NonExistenceException {

        log.info("ACCION CHANGESTATEBYID PRODUCT -> Iniciando proceso con id: {}", id);

        Optional<Product> optProduct = productRepository.findById(id);
        if(optProduct.isEmpty()) throw new NonExistenceException(ErrorMessages.NON_EXISTENT_DATA);
        log.info("ACCION CHANGESTATEBYID PRODUCT -> Producto encontrado con éxito");

        Product product = optProduct.get();
        product.setState(product.getState().equals("active") ? "inactive" : "active");
        product.setUpdateBy(updateBy);

        log.info("ACCION CHANGESTATEBYID PRODUCT -> Actualizando estado");
        return productRepository.update(product);
    }

}
