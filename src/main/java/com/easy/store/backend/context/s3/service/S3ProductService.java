package com.easy.store.backend.context.s3.service;

import com.easy.store.backend.context.product.application.usecase.FindByIdProductUseCase;
import com.easy.store.backend.context.product.application.usecase.UpdateProductUseCase;
import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.s3.model.S3File;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class S3ProductService {

    private Logger logger = Logger.getLogger(S3ProductService.class.getName());

    private final S3Service s3Service;
    private final FindByIdProductUseCase findByIdProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;

    public Product addFile(S3File s3File, Long productId) throws NoResultsException, NoIdReceivedException,
            NoChangesException, InvalidBodyException, InvalidActionException {
        logger.info("ACCION ADDFILE PRODUCT -> Inicia carga de archivo para el producto: " + productId);
        Product product = findByIdProductUseCase.findById(productId);
        product.setImageNumber(product.getImageNumber() + 1);
        product.setImageLastNumber(product.getImageLastNumber() + 1);
        s3File.setName(productId + "-" + product.getImageLastNumber() + ".png");

        if(product.getImageNumber() > 5) throw new InvalidActionException(ErrorMessages.LIMIT_ERROR);

        logger.info("ACCION ADDFILE PRODUCT -> Subiendo archivo: " + s3File);
        s3Service.putObject(s3File);

        if (product.getImageName().isEmpty() || product.getImageName().equals("product.png")) {
            product.setImageName(s3File.getName());
        }else{
            product.setImageName(product.getImageName().concat("," + s3File.getName()));
        }
        logger.info("ACCION ADDFILE PRODUCT -> Actualizando producto: " + product);
        product = updateProductUseCase.update(product);
        logger.info("ACCION ADDFILE PRODUCT -> Finalizo actualizacion de producto");
        return product;
    }

    public Product deleteFile(S3File s3File, Long productId, Long accountId) throws NoResultsException, NoIdReceivedException, NoChangesException, InvalidBodyException {
        logger.info("ACCION DELETEFILE PRODUCT -> Inicia eliminado de archivo para el producto: " + productId);

        logger.info("ACCION DELETEFILE PRODUCT -> Eliminando archivo: " + s3File);
        s3Service.deleteObject(accountId, "product", s3File.getName());

        logger.info("ACCION DELETEFILE PRODUCT -> Ajustando atributos producto");
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

        logger.info("ACCION DELETEFILE PRODUCT -> Actualizando producto: " + product);
        product = updateProductUseCase.update(product);
        logger.info("ACCION DELETEFILE PRODUCT -> Producto actualizado");
        return product;
    }
}
