package com.elife.MiniProject.businiss.services;

import com.elife.MiniProject.dao.entities.Request;

import java.util.List;

public interface RequestService {
    Request getRequestById(Long id);
    List<Request> getAllRequests();
    Request saveRequest(Request request);
    Request updateRequest(Long id, Request request);
    void deleteRequest(Long id);
    void acceptRequest(Long requestId);
    void refuseRequest(Long requestId);
}
