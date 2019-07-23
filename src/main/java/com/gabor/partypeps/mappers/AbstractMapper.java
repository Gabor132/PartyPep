package com.gabor.partypeps.mappers;

import com.gabor.partypeps.models.dao.AbstractEntity;
import com.gabor.partypeps.models.dto.AbstractDTO;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMapper<T extends AbstractEntity, S extends AbstractDTO> {

    public abstract S mapToDTO(T entity);

    public abstract T mapToDAO(S dto);

    public List<S> mapListOfDTO(List<T> entities) {
        List<S> list = new ArrayList<>();
        for (T entity : entities) {
            list.add(this.mapToDTO(entity));
        }
        return list;
    }

    public List<T> mapListOfDAO(List<S> dtos) {
        List<T> list = new ArrayList<>();
        for (S dto : dtos) {
            list.add(this.mapToDAO(dto));
        }
        return list;
    }
}
