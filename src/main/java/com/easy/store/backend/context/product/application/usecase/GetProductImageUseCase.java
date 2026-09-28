package com.easy.store.backend.context.product.application.usecase;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.product.domain.port.ProductRepository;
import com.easy.store.backend.context.s3.model.S3ObjectContent;
import com.easy.store.backend.context.s3.service.S3Service;
import com.easy.store.backend.utils.constants.FileConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetProductImageUseCase {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final S3Service s3Service;

    public Optional<S3ObjectContent> getImage(Long productId, String imageName) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) return Optional.empty();

        Optional<Category> categoryOpt = categoryRepository.findById(productOpt.get().getCategoryId());
        if (categoryOpt.isEmpty()) return Optional.empty();

        Long accountId = categoryOpt.get().getAccount().getId();
        return s3Service.getObjectContent(accountId, FileConstants.PRODUCT_CONTEXT, imageName);
    }

}
