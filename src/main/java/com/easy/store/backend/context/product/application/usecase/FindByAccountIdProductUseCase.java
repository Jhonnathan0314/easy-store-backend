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
public class FindByAccountIdProductUseCase {

    private final ProductRepository productRepository;

    public List<Product> findByAccountId(Long accountId) throws NoResultsException {

        log.info("ACCION FINDBYACCOUNTID PRODUCT -> Iniciando búsqueda con id: {}", accountId);

        List<Product> products = productRepository.findByAccountId(accountId);
        if(products == null || products.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION FINDBYACCOUNTID PRODUCT -> Encontré productos con éxito");

        return products;
    }

}
