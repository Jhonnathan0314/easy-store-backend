package com.easy.store.backend.context.product.application.usecase;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
import com.easy.store.backend.context.product.application.util.ProductUtils;
import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.product.domain.port.ProductRepository;
import com.easy.store.backend.context.s3.model.S3File;
import com.easy.store.backend.context.s3.service.S3Service;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.constants.FileConstants;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeleteImageProductUseCase {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductUtils productUtils;
    private final S3Service s3Service;

    public Product deleteImages(Long productId, List<S3File> images, Long userId) throws NonExistenceException, NoResultsException {
        Optional<Product> productOpt = productRepository.findById(productId);
        if(productOpt.isEmpty()) throw new NonExistenceException(ErrorMessages.NON_EXISTENT_DATA);

        Product product = productOpt.get();

        Optional<Category> categoryOpt = categoryRepository.findById(product.getCategoryId());
        if (categoryOpt.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);

        Category category = categoryOpt.get();

        product.getSubcategory().setCategory(category);
        product.setUpdateBy(userId);
        product.setUpdateDate(Timestamp.valueOf(LocalDateTime.now()));

        String updatedImageNames = deleteAndGetNewImageNames(images, product);

        product.setImageName(updatedImageNames);

        Product updatedProduct = productRepository.update(product);
        updatedProduct.getSubcategory().setCategory(category);

        productUtils.findAllImages(updatedProduct);

        return updatedProduct;
    }

    private String deleteAndGetNewImageNames(List<S3File> images, Product product) {
        Long accountId = product.getSubcategory().getCategory().getAccount().getId();
        List<String> imageNames = product.getImageName().equals(FileConstants.DEFAULT_PRODUCT_IMG) ?
                new ArrayList<>() :
                new ArrayList<>(List.of(product.getImageName().split(",")));

        for (S3File image : images) {
            if(imageNames.contains(image.getName())) {
                try {
                    s3Service.deleteObject(accountId, FileConstants.PRODUCT_CONTEXT, image.getName());
                    imageNames.remove(image.getName());
                    product.setImageNumber(product.getImageNumber() - 1);
                } catch (Exception ignored) {}
            }
        }

        return imageNames.isEmpty() ? FileConstants.DEFAULT_PRODUCT_IMG : String.join(",", imageNames);
    }

}
