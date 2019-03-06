package com.epam.lab.rto.repository.interfaces;

import com.epam.lab.rto.dto.Request;
import com.epam.lab.rto.dto.RequestStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface IRequestRepository {

    Request addRequest(Request request);

    Request getRequestById(long requestId);

    List<Request> getActiveRequestsByUserId(long userId);

    List<Request> getInactiveRequestsByUserId(long userId);

    List<Request> getInactiveRequestsBetweenDates(LocalDateTime firstDate, LocalDateTime secondDate);

    List<Request> getActiveRequestsBetweenDates(LocalDateTime firstDate, LocalDateTime secondDate);

    int setRequestStatusById(long requestId, RequestStatus status);

    int setRequestPaymentStateById(long requestId, boolean paid);
}