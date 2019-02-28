package com.epam.lab.rto.controller;

import com.epam.lab.rto.manager.UserManager;
import com.epam.lab.rto.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;


public class RequestController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private UserManager userManager;

    @GetMapping("/request/close")
    public void closeRequest(long requestId) {
        requestService.closeRequest(requestId, userManager.getUser());
    }

    @GetMapping("/request/paid")
    public void paidRequest(long requestId) {
        requestService.paidRequest(requestId, userManager.getUser());
    }

    @GetMapping("admin/request/paid")
    public void rejectRequest(long requestId) {
        requestService.rejectRequest(requestId, userManager.getUser());
    }

}
