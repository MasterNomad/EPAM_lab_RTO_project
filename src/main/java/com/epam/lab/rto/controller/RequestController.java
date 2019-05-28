package com.epam.lab.rto.controller;

import com.epam.lab.rto.repository.interfaces.IUserRepository;
import com.epam.lab.rto.service.interfaces.IRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Controller
public class RequestController {

    @Autowired
    private IRequestService requestService;

    @Autowired
    private IUserRepository userRepository;


    @GetMapping("/admin/requests")
    public ModelAndView showRequests() {
        ModelAndView model = new ModelAndView();

        LocalDate firstDate = LocalDate.now();
        LocalDate secondDate = firstDate.plusMonths(1);

        model.setViewName("requests/active");
        model.addObject("requests", requestService.getActiveRequestsBetweenDates(firstDate, secondDate));
        model.addObject("minDate", LocalDate.now());
        model.addObject("firstDate", firstDate);
        model.addObject("secondDate", secondDate);

        return model;
    }

    @PostMapping("/admin/requests")
    public ModelAndView showActiveRequestsBetweenDates(@RequestParam(value = "firstDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate firstDate,
                                                       @RequestParam(value = "secondDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate secondDate) {
        ModelAndView model = new ModelAndView();

        model.setViewName("requests/active");
        model.addObject("requests", requestService.getActiveRequestsBetweenDates(firstDate, secondDate));
        model.addObject("minDate", LocalDate.now());
        model.addObject("firstDate", firstDate);
        model.addObject("secondDate", secondDate);

        return model;
    }

    @GetMapping("/admin/requests/history")
    public ModelAndView showRequestsHistory() {
        ModelAndView model = new ModelAndView();

        LocalDate firstDate = LocalDate.now().minusMonths(1);
        LocalDate secondDate = LocalDate.now().plusMonths(1);

        model.setViewName("requests/history");
        model.addObject("requests", requestService.getInactiveRequestsBetweenDates(firstDate, secondDate));
        model.addObject("firstDate", firstDate);
        model.addObject("secondDate", secondDate);

        return model;
    }

    @PostMapping("/admin/requests/history")
    public ModelAndView showRequestsHistoryBetweenDates(@RequestParam(value = "firstDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate firstDate,
                                                        @RequestParam(value = "secondDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate secondDate) {
        ModelAndView model = new ModelAndView();

        model.setViewName("requests/history");
        model.addObject("requests", requestService.getInactiveRequestsBetweenDates(firstDate, secondDate));
        model.addObject("firstDate", firstDate);
        model.addObject("secondDate", secondDate);

        return model;
    }

    @GetMapping("/request/cancel")
    public ModelAndView cancelRequest(long requestId, Authentication authentication) {
        ModelAndView model = new ModelAndView();

        if (requestService.cancelRequest(requestId, userRepository.getUserByEmail(authentication.getName()))) {
            model.setViewName("success");
            model.addObject("page", "personal-area");
            model.addObject("msg", "Отмена подтверждена");
            model.addObject("additional", "Билет №" + requestId + " был отменён");
            model.addObject("link", "/personal-area#tableMarker");
        }

        return model;
    }

    @GetMapping("/request/paid")
    public ModelAndView paidRequest(long requestId, Authentication authentication) {
        ModelAndView model = new ModelAndView();
        if (requestService.paidRequest(requestId, userRepository.getUserByEmail(authentication.getName()))) {
            model.setViewName("success");
            model.addObject("page", "personal-area");
            model.addObject("msg", "Оплата подтверждена");
            model.addObject("additional", "Билет №" + requestId + " успешно оплачен");
            model.addObject("link", "/personal-area#tableMarker");
        }

        return model;
    }

    @GetMapping("/admin/request/reject")
    public ModelAndView rejectRequest(long requestId, HttpServletRequest request, Authentication authentication) {
        ModelAndView model = new ModelAndView();

        requestService.rejectRequest(requestId, userRepository.getUserByEmail(authentication.getName()));
        model.setViewName("success");
        model.addObject("page", "requests");
        model.addObject("msg", "Отмена подтверждена");
        model.addObject("additional", "Билет №" + requestId + " был отменён");
        model.addObject("link", request.getHeader("referer"));

        return model;
    }
}