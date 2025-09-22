package com.easy.store.backend.context.product.application.usecase;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
import com.easy.store.backend.context.product.application.util.ProductUtils;
import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.product.domain.port.ProductRepository;
import com.easy.store.backend.context.s3.model.S3File;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.constants.FileConstants;
import com.easy.store.backend.utils.exceptions.FileException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FindByIdProductUseCase {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductUtils productUtils;

    public Product findById(Long id, boolean loadImages) throws NoResultsException, FileException {

        log.info("ACCION FINDBYID PRODUCT -> Iniciando búsqueda con id: {}", id);

        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION FINDBYID PRODUCT -> Encontré producto con éxito");

        Product product = optionalProduct.get();

        Optional<Category> categoryOpt = categoryRepository.findById(product.getCategoryId());
        if (categoryOpt.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);

        product.getSubcategory().setCategory(categoryOpt.get());

        if(product.getImageNumber() == 0) return product;

        if(loadImages) productUtils.findAllImages(product);
        else productUtils.findFirstImage(product);

        return product;
    }

}
