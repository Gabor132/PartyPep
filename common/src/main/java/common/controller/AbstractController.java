package common.controller;

import common.model.AbstractEntity;
import common.service.AbstractService;

public abstract class AbstractController<T extends AbstractEntity> {

    /**
     * Method that must be implemented to get the domain specific service
     *
     * @return AbstractService
     */
    public abstract AbstractService getService();

}
