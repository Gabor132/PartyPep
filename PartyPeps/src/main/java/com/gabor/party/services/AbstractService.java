package com.gabor.party.services;

import com.gabor.party.main.models.dto.AbstractDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface AbstractService {

    @Transactional
    public abstract List<? extends AbstractDTO> findAll();

    @Transactional
    public abstract AbstractDTO findById(Long id);

    @Transactional
    public abstract long insert(AbstractDTO dto);

    @Transactional
    public abstract boolean delete(AbstractDTO dto);

    @Transactional
    public abstract boolean update(AbstractDTO dto);
}
