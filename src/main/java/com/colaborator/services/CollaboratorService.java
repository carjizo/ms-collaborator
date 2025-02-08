package com.colaborator.services;

import com.colaborator.dtos.CollaboratorCreateDTO;
import com.colaborator.dtos.CollaboratorRequestPaginationDTO;
import com.colaborator.dtos.CollaboratorUpdateDTO;
import com.colaborator.entities.Collaborator;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

public interface CollaboratorService {
    public HashMap<String, Object> findById(String idDocument) throws Exception;
    public HashMap<String, Object> update(CollaboratorUpdateDTO updateDTO) throws Exception;
    public HashMap<String, Object> create(CollaboratorCreateDTO createDTO) throws Exception;
    public Page<Collaborator> listPagination(CollaboratorRequestPaginationDTO filtro);
    public List<Collaborator> allCollaborators();
}
