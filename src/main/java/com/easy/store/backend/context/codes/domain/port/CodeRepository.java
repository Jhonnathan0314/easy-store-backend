package com.easy.store.backend.context.codes.domain.port;

import com.easy.store.backend.context.codes.domain.model.Code;

public interface CodeRepository {

    Code findByUserId(Long userId);
    Code create(Code code);
    void deleteByUserId(Long userId);

}
