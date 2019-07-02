package com.gabor.party.common.service;


import com.gabor.party.common.dto.AbstractDTO;
import com.gabor.party.common.model.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public abstract class AbstractService<S extends AbstractDTO> {

    @Transactional
    public abstract List<S> findAll();

    @Transactional
    public abstract S findById(Long id);

    @Transactional
    public abstract long insert(S dto);

    @Transactional
    public abstract boolean delete(Long id);

    @Transactional
    public abstract boolean update(S dto);

    protected static boolean genericDelete(Long id, JpaRepository<? extends AbstractEntity, Long> repository){
        Optional<? extends AbstractEntity> entity = repository.findById(id);
        if(entity.isPresent()){
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
