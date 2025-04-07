package com.easy.store.backend.context.subcategory.application.usecase;

import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.domain.port.SubcategoryRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteByIdSubcategoryUseCase {

    private final SubcategoryRepository subcategoryRepository;

    public void deleteById(Long id) throws NonExistenceException {

        log.info("ACCION DELETEBYID SUBCATEGORY -> Iniciando proceso con id: {}", id);

        Optional<Subcategory> subcategory = subcategoryRepository.findById(id);
        if(subcategory.isEmpty()) throw new NonExistenceException(ErrorMessages.NON_EXISTENT_DATA);
        log.info("ACCION DELETEBYID SUBCATEGORY -> Subcategoria encontrada con éxito");

        log.info("ACCION DELETEBYID SUBCATEGORY -> Eliminando subcategoria");

        subcategoryRepository.deleteById(id);
    }

}
