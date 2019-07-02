package com.gabor.party.common.mapper;

import com.gabor.party.common.dto.AbstractDTO;
import com.gabor.party.common.model.AbstractEntity;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractMapper<T extends AbstractEntity, S extends AbstractDTO> {

    public abstract S mapToDTO(T entity);

    public abstract T mapToDAO(S dto);

    public List<S> mapListOfDTO(List<T> entities){
        List<S> list = new LinkedList<>();
        for(T entity : entities){
            list.add(this.mapToDTO(entity));
        }
        return list;
    }

    public List<T> mapListOfDAO(List<S> dtos){
        List<T> list = new LinkedList<>();
        for(S dto : dtos){
            list.add(this.mapToDAO(dto));
        }
        return list;
    }
}
