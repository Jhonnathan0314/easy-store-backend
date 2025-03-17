package com.easy.store.backend.context.s3.service;

import com.easy.store.backend.context.product.application.usecase.FindByIdProductUseCase;
import com.easy.store.backend.context.product.application.usecase.UpdateProductUseCase;
import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.s3.model.S3File;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoChangesException;
import com.easy.store.backend.utils.exceptions.NoIdReceivedException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class S3ProductService {

    private final S3Service s3Service;
    private final FindByIdProductUseCase findByIdProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;

    public Product addFile(S3File s3File, Long id) throws NoResultsException, NoIdReceivedException, NoChangesException, InvalidBodyException {
        Product product = findByIdProductUseCase.findById(id);
        product.setImageNumber(product.getImageNumber() + 1);
        product.setImageLastNumber(product.getImageLastNumber() + 1);
        s3File.setName(id + "-" + product.getImageLastNumber() + ".png");

        s3Service.putObject(s3File);

        if (product.getImageName().isEmpty() || product.getImageName().equals("product.png")) {
            product.setImageName(s3File.getName());
        }else{
            product.setImageName(product.getImageName().concat("," + s3File.getName()));
        }
        product = updateProductUseCase.update(product);
        return product;
    }

    public Product deleteFile(S3File s3File, Long productId, Long accountId) throws NoResultsException, NoIdReceivedException, NoChangesException, InvalidBodyException {
        s3Service.deleteObject(accountId, "product", s3File.getName());
        Product product = findByIdProductUseCase.findById(productId);
        product.setImageNumber(product.getImageNumber() - 1);
        product.setImageName(product.getImageName().replace(s3File.getName(), ""));
        product.setImageName(product.getImageName().replace(",,", ","));
        if(product.getImageName().startsWith(",")){
            product.setImageName(product.getImageName().substring(1));
        }
        if(product.getImageName().endsWith(",")){
            product.setImageName(product.getImageName().substring(0, product.getImageName().length() - 1));
        }
        product = updateProductUseCase.update(product);
        return product;
    }
}
