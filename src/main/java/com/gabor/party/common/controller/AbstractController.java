package com.gabor.party.common.controller;

import com.gabor.party.common.model.AbstractEntity;
import com.gabor.party.common.service.AbstractService;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.Servlet;

public abstract class AbstractController<T extends AbstractEntity> {

    /**
     * Method that must be implemented to get the domain specific service
     *
     * @return AbstractService
     */
    public abstract AbstractService getService();

}
