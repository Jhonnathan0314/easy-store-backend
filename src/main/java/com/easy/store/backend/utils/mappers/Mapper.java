package com.easy.store.backend.utils.mappers;

import java.util.List;

public interface Mapper<E, M, D> {
    M entityToModel(E entity);
    E modelToEntity(M model);
    D modelToDto(M model);
    M dtoToModel(D dto);
    List<M> entitiesToModels(List<E> entities);
    List<E> modelsToEntities(List<M> models);
    List<D> modelsToDtos(List<M> models);
    List<M> dtosToModels(List<D> dtos);
}
