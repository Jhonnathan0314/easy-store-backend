package com.easy.store.backend.context.subcategory.application.usecase;

import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.domain.port.SubcategoryRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ChangeStateByIdSubcategoryUseCase {

    private final Logger logger = Logger.getLogger(ChangeStateByIdSubcategoryUseCase.class.getName());

    private final SubcategoryRepository subcategoryRepository;

    public Subcategory changeStateById(Long id, Long updateBy) throws NonExistenceException {

        logger.info("ACCION CHANGESTATEBYID SUBCATEGORY -> Iniciando proceso con id: " + id);

        Optional<Subcategory> optSubcategory = subcategoryRepository.findById(id);
        if(optSubcategory.isEmpty()) throw new NonExistenceException(ErrorMessages.NON_EXISTENT_DATA);
        logger.info("ACCION CHANGESTATEBYID SUBCATEGORY -> Subcategoria encontrada con éxito" + optSubcategory.get().getState());

        Subcategory subcategory = optSubcategory.get();
        subcategory.setState(subcategory.getState().equals("active") ? "inactive" : "active");
        subcategory.setUpdateBy(updateBy);

        logger.info("ACCION CHANGESTATEBYID SUBCATEGORY -> Actualizando estado");
        return subcategoryRepository.update(subcategory);
    }

}
