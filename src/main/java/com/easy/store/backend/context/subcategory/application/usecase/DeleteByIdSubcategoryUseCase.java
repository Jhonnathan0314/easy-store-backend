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
public class DeleteByIdSubcategoryUseCase {

    private final Logger logger = Logger.getLogger(DeleteByIdSubcategoryUseCase.class.getName());

    private final SubcategoryRepository subcategoryRepository;

    public void deleteById(Long id) throws NonExistenceException {

        logger.info("ACCION DELETEBYID SUBCATEGORY -> Iniciando proceso con id: " + id);

        Optional<Subcategory> subcategory = subcategoryRepository.findById(id);
        if(subcategory.isEmpty()) throw new NonExistenceException(ErrorMessages.NON_EXISTENT_DATA);
        logger.info("ACCION DELETEBYID SUBCATEGORY -> Subcategoria encontrada con éxito");

        logger.info("ACCION DELETEBYID SUBCATEGORY -> Eliminando subcategoria");

        subcategoryRepository.deleteById(id);
    }

}
