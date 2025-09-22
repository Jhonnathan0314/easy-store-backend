package com.easy.store.backend.context.product.application.util;

import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.s3.model.S3File;
import com.easy.store.backend.context.s3.service.S3Service;
import com.easy.store.backend.utils.constants.FileConstants;
import com.easy.store.backend.utils.exceptions.FileException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductUtils {

    private final S3Service s3Service;

    public void validateProductsImages(List<Product> products, boolean loadImages) throws FileException {
        for (Product product : products) {
            if(product.getImageNumber() == 0) continue;

            if(loadImages) findAllImages(product);
            else findFirstImage(product);
        }
    }

    public void findAllImages(Product product) {
        List<S3File> images = new ArrayList<>();
        List<String> imageNames = List.of(product.getImageName().split(","));
        Long accountId = product.getSubcategory().getCategory().getAccount().getId();
        for (String imageName : imageNames) {
            try {
                S3File image = s3Service.getObject(accountId, FileConstants.PRODUCT_CONTEXT, imageName);
                images.add(image);
                System.out.println("Loaded image: " + imageName);
            } catch (Exception ignored) {}
        }
        product.setImages(images);
    }

    public void findFirstImage(Product product) throws FileException {
        List<S3File> images = new ArrayList<>();
        Long accountId = product.getSubcategory().getCategory().getAccount().getId();
        String firstImageName = product.getImageName().split(",")[0];
        S3File firstImage = s3Service.getObject(accountId, FileConstants.PRODUCT_CONTEXT, firstImageName);
        images.add(firstImage);
        product.setImages(images);
    }

}
