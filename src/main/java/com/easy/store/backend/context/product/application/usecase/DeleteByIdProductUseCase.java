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
public class DeleteByIdProductUseCase {

    private final ProductRepository productRepository;

    public void deleteById(Long id) throws NonExistenceException {

        log.info("ACCION DELETEBYID PRODUCT -> Iniciando proceso con id: {}", id);

        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()) throw new NonExistenceException(ErrorMessages.NON_EXISTENT_DATA);
        log.info("ACCION DELETEBYID PRODUCT -> Produto encontrado con éxito");

        log.info("ACCION DELETEBYID PRODUCT -> Eliminando producto");

        productRepository.deleteById(id);
    }

}
