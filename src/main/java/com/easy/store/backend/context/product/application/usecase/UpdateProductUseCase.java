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

@Service
@RequiredArgsConstructor
public class UpdateProductUseCase {

    private final ErrorMessages errorMessages = new ErrorMessages();
    private final ProductRepository productRepository;
    private final SubcategoryRepository subcategoryRepository;

    public Product update(Product product, Long idSubcategory) throws NoIdReceivedException, NoResultsException, NoChangesException, InvalidBodyException {

        Optional<Subcategory> optSubcategory = subcategoryRepository.findById(idSubcategory);
        if(optSubcategory.isEmpty()) throw new NoResultsException(errorMessages.NO_CATEGORY_RESULTS);

        product.setSubcategory(optSubcategory.get());

        if(product.getId() == null) throw new NoIdReceivedException(errorMessages.NO_ID_RECEIVED);

        if(!product.isValid(product)) throw new InvalidBodyException(errorMessages.INVALID_BODY);

        Optional<Product> optProduct = productRepository.findById(product.getId());
        if(optProduct.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);

        Product productDb = optProduct.get();
        if(Objects.equals(productDb.getSubcategory().getId(), product.getSubcategory().getId()) &&
                productDb.getName().equals(product.getName()) &&
                productDb.getDescription().equals(product.getDescription()) &&
                productDb.getPrice().compareTo(product.getPrice()) == 0 &&
                Objects.equals(productDb.getQuantity(), product.getQuantity()) &&
                Objects.equals(productDb.getQualification(), product.getQualification())) {
            throw new NoChangesException(errorMessages.NO_CHANGES);
        }

        product.setState(productDb.getState());

        return productRepository.update(product);
    }

}
