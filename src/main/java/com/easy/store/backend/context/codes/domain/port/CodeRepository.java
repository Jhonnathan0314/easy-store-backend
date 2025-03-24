package com.easy.store.backend.context.codes.domain.port;

import com.easy.store.backend.context.codes.domain.model.Code;

import java.util.List;

public interface CodeRepository {

    List<Code> findAll();
    Code findByUserId(Long userId);
    Code create(Code code);
    void deleteByUserId(Long userId);

}
