package com.sophie.store.backend.context.product.application.usecase;

import com.sophie.store.backend.context.product.domain.model.Product;
import com.sophie.store.backend.context.product.domain.port.ProductRepository;
import com.sophie.store.backend.context.subcategory.domain.model.Subcategory;
import com.sophie.store.backend.context.subcategory.domain.port.SubcategoryRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.DuplicatedException;
import com.sophie.store.backend.utils.exceptions.InvalidBodyException;
import com.sophie.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateProductUseCase {

    private final ProductRepository productRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Product create(Product product, Long idSubcategory) throws NoResultsException, DuplicatedException, InvalidBodyException {

        Optional<Subcategory> optSubcategory = subcategoryRepository.findById(idSubcategory);
        if(optSubcategory.isEmpty()) throw new NoResultsException(errorMessages.NO_CATEGORY_RESULTS);

        product.setSubcategory(optSubcategory.get());

        if(!product.isValid(product)) throw new InvalidBodyException(errorMessages.INVALID_BODY);

        if(productRepository.findByName(product.getName()).isPresent()) throw new DuplicatedException(errorMessages.DUPLICATED);

        return productRepository.create(product);
    }

}
