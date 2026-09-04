package com.easy.store.backend.context.product.application.usecase;

import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.product.domain.port.ProductRepository;
import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.domain.port.SubcategoryRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class CreateProductUseCase {

    private final ProductRepository productRepository;
    private final SubcategoryRepository subcategoryRepository;

    public Product create(Product product) throws NoResultsException, InvalidBodyException {

        log.info("ACCION CREATE PRODUCT -> Iniciando proceso con body: {}", product.toString());

        Optional<Subcategory> optSubcategory = subcategoryRepository.findById(product.getSubcategory().getId());
        if(optSubcategory.isEmpty()) throw new NoResultsException(ErrorMessages.NO_CATEGORY_RESULTS);
        log.info("ACCION CREATE PRODUCT -> Subcategoria encontrada con éxito");

        product.setSubcategory(optSubcategory.get());

        if(!product.isValid()) throw new InvalidBodyException(ErrorMessages.INVALID_BODY);
        log.info("ACCION CREATE PRODUCT -> Validé cuerpo de la petición");

        // Se valida null antes de isEmpty(): imageName es un campo opcional en ProductCreateDTO
        // (no tiene @NotBlank ni se valida en Product.isValid()), asi que un cliente que no lo
        // envie llega aqui con imageName == null y no con cadena vacia. Antes esto lanzaba un
        // NullPointerException que terminaba en un 500 generico.
        if(product.getImageName() == null || product.getImageName().isEmpty()) product.setImageName("product.png");
        if(product.getImageNumber() == null) product.setImageNumber(0);

        log.info("ACCION CREATE PRODUCT -> Creando producto");

        return productRepository.create(product);
    }

}
