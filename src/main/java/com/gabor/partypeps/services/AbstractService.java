package com.gabor.partypeps.services;


import com.gabor.partypeps.mappers.AbstractMapper;
import com.gabor.partypeps.models.dao.AbstractEntity;
import com.gabor.partypeps.models.dto.AbstractDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public abstract class AbstractService<T extends AbstractEntity, S extends AbstractDTO> {

    /**
     * Function meant to retrieve all the entities that exist of a specific type in the database
     *
     * @return List<AbstractDTO>
     */
    @Transactional
    public List<S> findAll() {
        List<T> entities = getRepository().findAll();
        return getMapper().mapListOfDTO(entities);
    }

    /**
     * Function meant to retrieve only one entity of a specific type by an ID
     *
     * @param id
     * @return AbstractDTO
     */
    @Transactional
    public S findById(Long id) {
        Optional<T> entity = getRepository().findById(id);
        if (entity.isPresent()) {
            return getMapper().mapToDTO(entity.get());
        }
        return null;
    }

    /**
     * Function meant to handle the insert of a newly created entity inside the database
     *
     * @param dto
     * @return long - the ID of the newly inserted entity
     */
    @Transactional
    public abstract long insert(S dto);

    /**
     * Function meant to delete an existing entity from the database
     *
     * @param id
     * @return boolean - tells if the deletion has taken place or not
     */
    @Transactional
    public boolean delete(Long id) {
        return genericDelete(id, getRepository());
    }

    /**
     * Function meant to update an already existing entity from the database
     *
     * @param dto
     * @return boolean - tells if the update has taken place or not
     */
    @Transactional
    public abstract boolean update(S dto);

    /**
     * Generic function for deletion
     *
     * @param id
     * @param repository
     * @return
     */
    private boolean genericDelete(Long id, JpaRepository<T, Long> repository) {
        Optional<? extends AbstractEntity> entity = repository.findById(id);
        if (entity.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Function to retrieve the appropriate repository of this Service class
     *
     * @return JpaRepository<? extends AbstractEntity, Long>
     */
    public abstract JpaRepository<T, Long> getRepository();

    /**
     * Function to retrieve the appropriate mapper for this Service class
     *
     * @return AbstractMapper<? extends AbstractEntity, ? extends AbstractDTO>
     */
    public abstract AbstractMapper<T, S> getMapper();
}
