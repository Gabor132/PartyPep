package com.gabor.partypeps.controllers;

import com.gabor.partypeps.models.dao.NotificationAuthorization;
import com.gabor.partypeps.models.dto.NotificationAuthorizationDTO;
import com.gabor.partypeps.services.AbstractService;
import com.gabor.partypeps.services.NotificationAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * This is the main controller for all services related with the User data
 * model.
 * (basic data)
 *
 * @author dragos.gabor
 */
@RestController(value = "notification")
@RequestMapping(path = "notification")
public class NotificationAuthorizationController extends AbstractController<NotificationAuthorization> {

    @Autowired
    public NotificationAuthorizationService notifyService;


    @GetMapping(path = "/all")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<NotificationAuthorizationDTO> getNotificationAuthorization(Principal principal) {
        return notifyService.getNotificationAuthorizations(principal.getName());
    }

    @PostMapping(path = "/add")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Long addNotificationAuthorization(Principal principal, @RequestBody NotificationAuthorizationDTO notify) {
        return notifyService.addNotificationAuthorization(principal.getName(), notify);
    }

    @PostMapping(path = "/notify/{username}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Boolean notifyUser(Principal principal, @PathVariable String username) {
        return notifyService.notifyUser(principal.getName(), username);
    }

    @GetMapping(path = "/notify/admin")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Boolean notifyUser() {
        return notifyService.notifyAdmin();
    }

    @GetMapping(path = "/notify/publicKey")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getPublicKey() {
        return "Nope";
    }

    @Override
    public AbstractService getService() {
        return notifyService;
    }
}
