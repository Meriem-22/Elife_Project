package com.elife.MiniProject.businiss.servicesImpl;

import com.elife.MiniProject.businiss.services.RequestService;
import com.elife.MiniProject.dao.entities.Request;
import com.elife.MiniProject.dao.enums.RequestStatus;
import com.elife.MiniProject.dao.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Override
    public Request getRequestById(Long id) {
        return requestRepository.findById(id).orElse(null);
    }

    @Override
    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    @Override
    public Request saveRequest(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public Request updateRequest(Long id, Request request) {
        Optional<Request> existingRequest = requestRepository.findById(id);
        if (existingRequest.isPresent()) {
            Request updatedRequest = existingRequest.get();
            updatedRequest.setStatus(request.getStatus());
            updatedRequest.setSubmissionDate(request.getSubmissionDate());
            updatedRequest.setResolutionDate(request.getResolutionDate());
            updatedRequest.setComments(request.getComments());
            return requestRepository.save(updatedRequest);
        } else {
            return null; 
        }
    }

    @Override
    public void deleteRequest(Long id) {
        requestRepository.deleteById(id);
    }

    @Override
    public void acceptRequest(Long requestId) {
        Optional<Request> requestOpt = requestRepository.findById(requestId);
        if (requestOpt.isPresent()) {
            Request request = requestOpt.get();
            request.setStatus(RequestStatus.ACCEPTED);
            requestRepository.save(request);
        }
    }

    @Override
    public void refuseRequest(Long requestId) {
        Optional<Request> requestOpt = requestRepository.findById(requestId);
        if (requestOpt.isPresent()) {
            Request request = requestOpt.get();
            request.setStatus(RequestStatus.REFUSED);
            requestRepository.save(request);
        }
    }

    @Override
    public List<Request> findRequestsByCollaboratorId(Long collaboratorId) {
        return requestRepository.findByCollaboratorId(collaboratorId);
    }

}
