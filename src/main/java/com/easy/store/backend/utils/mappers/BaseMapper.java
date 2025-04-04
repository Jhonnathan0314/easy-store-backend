package com.easy.store.backend.utils.mappers;

import java.util.List;

public abstract class BaseMapper<E, M, D> implements Mapper<E, M, D> {

    @Override
    public List<M> entitiesToModels(List<E> entities) {
        return entities.stream().map(this::entityToModel).toList();
    }

    @Override
    public List<E> modelsToEntities(List<M> models) {
        return models.stream().map(this::modelToEntity).toList();
    }

    @Override
    public List<D> modelsToDtos(List<M> models) {
        return models.stream().map(this::modelToDto).toList();
    }

    @Override
    public List<M> dtosToModels(List<D> dtos) {
        return dtos.stream().map(this::dtoToModel).toList();
    }

}
