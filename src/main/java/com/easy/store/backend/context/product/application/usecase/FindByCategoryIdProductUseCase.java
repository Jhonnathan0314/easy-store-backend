package com.easy.store.backend.context.product.application.usecase;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
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
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class FindByCategoryIdProductUseCase {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductUtils productUtils;

    public List<Product> findByCategoryId(Long categoryId, boolean loadImages) throws NoResultsException, FileException {

        log.info("ACCION FINDBYCATEGORYID PRODUCT -> Iniciando búsqueda con id: {}", categoryId);

        List<Product> products = productRepository.findByCategoryId(categoryId);
        if(products == null || products.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION FINDBYCATEGORYID PRODUCT -> Encontré productos con éxito");

        Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
        if (categoryOpt.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);

        Category category = categoryOpt.get();
        products = products.stream().peek(p -> p.getSubcategory().setCategory(category)).toList();

        productUtils.validateProductsImages(products, loadImages);

        return products;
    }

}
