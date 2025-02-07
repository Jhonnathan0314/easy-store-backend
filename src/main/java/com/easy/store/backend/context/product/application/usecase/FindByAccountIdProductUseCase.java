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
public class FindByAccountIdProductUseCase {

    private final Logger logger = Logger.getLogger(FindByAccountIdProductUseCase.class.getName());

    private final ProductRepository productRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public List<Product> findByAccountId(Long accountId) throws NoResultsException {

        logger.info("ACCION FINDBYACCOUNTID PRODUCT -> Iniciando búsqueda con id: " + accountId);

        List<Product> products = productRepository.findByAccountId(accountId);
        if(products == null || products.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        logger.info("ACCION FINDBYACCOUNTID PRODUCT -> Encontré productos con éxito");

        return products;
    }

}
