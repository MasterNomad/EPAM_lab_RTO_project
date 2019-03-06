package com.epam.lab.rto.service.interfaces;

import com.epam.lab.rto.dto.Request;
import com.epam.lab.rto.dto.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IRequestService {

    List<Request> createRequests(String departureCity, String destinationCity, LocalDateTime departureTime);

    Request createRequest(long tripId, String departureCity, String destinationCity, String carriageName, User user);

    Request addRequest(Request request);

    List<Request> getActiveRequestsByUser(User user);

    List<Request> getInactiveRequestsByUser(User user);

    List<Request> getActiveRequestsBetweenDates(LocalDate firstDate, LocalDate secondDate);

    List<Request> getInactiveRequestsBetweenDates(LocalDate firstDate, LocalDate secondDate);

    boolean cancelRequest(long requestId, User user);

    boolean paidRequest(long requestId, User user);

    boolean rejectRequest(long requestId, User user);
}