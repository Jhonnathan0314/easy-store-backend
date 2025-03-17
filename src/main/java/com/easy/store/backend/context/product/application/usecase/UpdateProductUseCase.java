package com.easy.store.backend.context.product.application.usecase;

import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.product.domain.port.ProductRepository;
import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.domain.port.SubcategoryRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoChangesException;
import com.easy.store.backend.utils.exceptions.NoIdReceivedException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class UpdateProductUseCase {

    private final Logger logger = Logger.getLogger(UpdateProductUseCase.class.getName());

    private final ErrorMessages errorMessages = new ErrorMessages();
    private final ProductRepository productRepository;
    private final SubcategoryRepository subcategoryRepository;

    public Product update(Product product) throws NoIdReceivedException, NoResultsException, NoChangesException, InvalidBodyException {

        logger.info("ACCION UDPATE PRODUCT -> Inicia el proceso");

        Optional<Subcategory> optSubcategory = subcategoryRepository.findById(product.getSubcategory().getId());
        if(optSubcategory.isEmpty()) throw new NoResultsException(errorMessages.NO_CATEGORY_RESULTS);
        logger.info("ACCION UDPATE PRODUCT -> Categoria encontrada con éxito");

        product.setSubcategory(optSubcategory.get());

        if(product.getId() == null) throw new NoIdReceivedException(errorMessages.NO_ID_RECEIVED);
        logger.info("ACCION UDPATE PRODUCT -> Validé id");

        if(!product.isValid(product)) throw new InvalidBodyException(errorMessages.INVALID_BODY);
        logger.info("ACCION UDPATE PRODUCT -> Validé cuerpo de la petición");

        Optional<Product> optProduct = productRepository.findById(product.getId());
        if(optProduct.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        logger.info("ACCION UDPATE PRODUCT -> Validé existencia del producto");

        Product productDb = optProduct.get();
        if(areNoChanges(productDb, product)) {
            throw new NoChangesException(errorMessages.NO_CHANGES);
        }
        logger.info("ACCION UDPATE PRODUCT -> Validé que hayan cambios a aplicar");

        product.setState(productDb.getState());

        logger.info("ACCION UDPATE PRODUCT -> Actualizando producto");

        return productRepository.update(product);
    }

    private boolean areNoChanges(Product productDb, Product product) {
        return Objects.equals(productDb.getSubcategory().getId(), product.getSubcategory().getId()) &&
                productDb.getName().equals(product.getName()) &&
                productDb.getDescription().equals(product.getDescription()) &&
                productDb.getImageName().equals(product.getImageName()) &&
                Objects.equals(productDb.getImageNumber(), product.getImageNumber()) &&
                productDb.getPrice().compareTo(product.getPrice()) == 0 &&
                Objects.equals(productDb.getQuantity(), product.getQuantity()) &&
                Objects.equals(productDb.getQualification(), product.getQualification());
    }

}
