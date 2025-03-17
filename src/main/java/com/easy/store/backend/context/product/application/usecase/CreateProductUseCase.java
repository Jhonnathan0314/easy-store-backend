package com.easy.store.backend.context.product.application.usecase;

import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.product.domain.port.ProductRepository;
import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.domain.port.SubcategoryRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.DuplicatedException;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class CreateProductUseCase {

    private final Logger logger = Logger.getLogger(CreateProductUseCase.class.getName());

    private final ProductRepository productRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Product create(Product product) throws NoResultsException, DuplicatedException, InvalidBodyException {

        logger.info("ACCION CREATE PRODUCT -> Iniciando proceso con body: " + product.toString());

        Optional<Subcategory> optSubcategory = subcategoryRepository.findById(product.getSubcategory().getId());
        if(optSubcategory.isEmpty()) throw new NoResultsException(errorMessages.NO_CATEGORY_RESULTS);
        logger.info("ACCION CREATE PRODUCT -> Subcategoria encontrada con éxito");

        product.setSubcategory(optSubcategory.get());

        if(!product.isValid(product)) throw new InvalidBodyException(errorMessages.INVALID_BODY);
        logger.info("ACCION CREATE PRODUCT -> Validé cuerpo de la petición");

        if(productRepository.findByName(product.getName()).isPresent()) throw new DuplicatedException(errorMessages.DUPLICATED);
        logger.info("ACCION CREATE PRODUCT -> Validé producto no duplicado");

        if(product.getImageName().isEmpty()) product.setImageName("product.png");
        if(product.getImageNumber() == null) product.setImageNumber(0);

        logger.info("ACCION CREATE PRODUCT -> Creando producto");

        return productRepository.create(product);
    }

}
