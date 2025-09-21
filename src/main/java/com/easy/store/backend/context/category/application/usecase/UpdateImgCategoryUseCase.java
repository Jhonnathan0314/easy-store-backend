package com.easy.store.backend.context.category.application.usecase;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
import com.easy.store.backend.context.s3.model.S3File;
import com.easy.store.backend.context.s3.service.S3Service;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.constants.FileConstants;
import com.easy.store.backend.utils.exceptions.NoChangesException;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateImgCategoryUseCase {

    private final CategoryRepository categoryRepository;
    private final S3Service s3Service;

    public Category updateCategoryImg(Long categoryId, S3File img, Long updateBy) throws NoChangesException, NonExistenceException {
        Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
        if(categoryOpt.isEmpty()) throw new NonExistenceException(ErrorMessages.NON_EXISTENT_DATA);
        Category category = categoryOpt.get();
        category.setUpdateBy(updateBy);

        if(isDefaultImg(category) && isEmptyImg(img)) throw new NoChangesException(ErrorMessages.NO_CHANGES);
        boolean deleteImg = false;
        boolean createImg = false;

        if(!isDefaultImg(category) && isEmptyImg(img)) {
            deleteImg = true;
        }else if(isDefaultImg(category) && !isEmptyImg(img)) {
            createImg = true;
        }else if(!isDefaultImg(category) && !isEmptyImg(img)) {
            deleteImg = true;
            createImg = true;
        }
        return applyImageChanges(category, deleteImg, createImg, img);
    }

    private Category applyImageChanges(Category category, boolean deleteImg, boolean createImg, S3File img) {
        Long accountId = category.getAccount().getId();
        if(deleteImg) {
            s3Service.deleteObject(accountId, FileConstants.CATEGORY_CONTEXT, category.getImageName());
            category.setImageName(FileConstants.DEFAULT_CATEGORY_IMG);
        }
        if(createImg) {
            img.setName(category.getId() + ".png");
            img.setAccountId(accountId);
            img.setContext(FileConstants.CATEGORY_CONTEXT);
            s3Service.putObject(img);
            category.setImageName(img.getName());
            category.setImage(img);
        }
        Category categoryUpdated = categoryRepository.update(category);
        categoryUpdated.setImage(img);
        return categoryUpdated;
    }

    private boolean isEmptyImg(S3File img) {
        return img.getContent() == null || img.getContent().isEmpty();
    }

    private boolean isDefaultImg(Category category) {
        return category.getImageName().equals(FileConstants.DEFAULT_CATEGORY_IMG);
    }

}
