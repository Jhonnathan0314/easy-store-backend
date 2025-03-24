package com.easy.store.backend.context.codes.infrastructure.adapter;

import com.easy.store.backend.context.codes.domain.model.Code;
import com.easy.store.backend.context.codes.domain.port.CodeRepository;
import com.easy.store.backend.context.codes.infrastructure.mapper.CodeMapper;
import com.easy.store.backend.context.codes.infrastructure.persistence.CodeEntity;
import com.easy.store.backend.context.codes.infrastructure.persistence.CodeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CodeRepositoryJpaAdapter implements CodeRepository {

    private final CodeJpaRepository codeJpaRepository;

    private final CodeMapper codeMapper = new CodeMapper();

    @Override
    public Code findByUserId(Long userId) {
        CodeEntity codeEntity = codeJpaRepository.findByUserId(userId);
        if(codeEntity == null) return null;
        return codeMapper.entityToModel(codeEntity);
    }

    @Override
    public Code create(Code code) {
        return codeMapper.entityToModel(codeJpaRepository.save(codeMapper.modelToEntity(code)));
    }

    @Override
    public void deleteByUserId(Long userId) {
        codeJpaRepository.deleteByUserId(userId);
    }
}
