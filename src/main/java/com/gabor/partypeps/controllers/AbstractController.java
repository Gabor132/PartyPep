package com.gabor.partypeps.controllers;

import com.gabor.partypeps.models.dao.AbstractEntity;
import com.gabor.partypeps.services.AbstractService;

public abstract class AbstractController<T extends AbstractEntity> {

    /**
     * Method that must be implemented to get the domain specific service
     *
     * @return AbstractService
     */
    public abstract AbstractService getService();

}
