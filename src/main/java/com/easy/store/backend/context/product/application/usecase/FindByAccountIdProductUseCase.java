package com.easy.store.backend.context.product.application.usecase;

import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.product.application.util.ProductUtils;
import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.product.domain.port.ProductRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.FileException;
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
    private final ProductUtils productUtils;

    public List<Product> findByAccountId(Long accountId, boolean loadImages) throws NoResultsException, FileException {

        log.info("ACCION FINDBYACCOUNTID PRODUCT -> Iniciando búsqueda con id: {}", accountId);

        List<Product> products = productRepository.findByAccountId(accountId);
        if(products == null || products.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION FINDBYACCOUNTID PRODUCT -> Encontré productos con éxito");

        products = products.stream().peek(p -> {
            Category category = new Category();
            category.setAccount(new Account());
            category.getAccount().setId(accountId);
            p.getSubcategory().setCategory(category);
        }).toList();

        productUtils.validateProductsImages(products, loadImages);

        return products;
    }

}
