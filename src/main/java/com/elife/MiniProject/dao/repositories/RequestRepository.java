package com.elife.MiniProject.dao.repositories;

import com.elife.MiniProject.dao.entities.Request;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
        List<Request> findByCollaboratorId(Long collaboratorId);

}
