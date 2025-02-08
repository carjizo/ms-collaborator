package com.colaborator.services.impl;

import com.colaborator.dtos.CollaboratorCreateDTO;
import com.colaborator.dtos.CollaboratorRequestPaginationDTO;
import com.colaborator.dtos.CollaboratorResponseDTO;
import com.colaborator.dtos.CollaboratorUpdateDTO;
import com.colaborator.entities.Collaborator;
import com.colaborator.entities.CollaboratorSpecifications;
import com.colaborator.repositories.CollaboratorRepository;
import com.colaborator.services.CollaboratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class CollaboratorServiceImpl implements CollaboratorService {

    @Autowired
    CollaboratorRepository collaboratorRepository;

    @Override
    public HashMap<String, Object> findById(String idDocument) throws Exception {
        HashMap<String, Object> response = new HashMap<>();

        try {
            Optional<Collaborator> collabdb = collaboratorRepository.findById(idDocument);
            Optional<CollaboratorResponseDTO> collaboratorResponseDTO = Optional.ofNullable(CollaboratorResponseDTO.builder()
                    .idDocument(collabdb.get().getIdDocument())
                    .fullName(collabdb.get().getFullName())
                    .phone(collabdb.get().getPhone())
                    .role(collabdb.get().getRole())
                    .status(collabdb.get().isStatus())
                    .build());
            response.put("message", "Succes");
            response.put("response", collaboratorResponseDTO);
            response.put("isSucces", true);
            return response;
        } catch (Exception e) {
            response.put("message", "Error Exception");
            response.put("isSucces", false);
            System.out.println("Error Exception: " + e.getMessage());
            return response;
        }
    }

    @Override
    public HashMap<String, Object> update(CollaboratorUpdateDTO updateDTO) throws Exception {
        HashMap<String, Object> response = new HashMap<>();
        try {
            Optional<Collaborator> collaboratordb = collaboratorRepository.findById(updateDTO.getIdDocument());
            if (collaboratordb.isEmpty()) {
                response.put("message", "Collaborator not registered");
                response.put("isSucces", false);
                return response;
            }
            collaboratorRepository.update(updateDTO.getIdDocument(), updateDTO.getFullName().toUpperCase(),
                    updateDTO.getPhone(), updateDTO.getRole(), updateDTO.isStatus());
            response.put("message", "Collaborator updated successfully");
            response.put("isSucces", true);
            return response;
        } catch (Exception e) {
            response.put("message", "Error Exception");
            response.put("isSucces", false);
            System.out.println("Error Exception: " + e.getMessage());
            return response;
        }
    }

    @Override
    public HashMap<String, Object> create(CollaboratorCreateDTO createDTO) throws Exception {
        HashMap<String, Object> response = new HashMap<>();
        try {
            Optional<Collaborator> collaboratordb = collaboratorRepository.findById(createDTO.getIdDocument());
            if (collaboratordb.isPresent()) {
                response.put("message", "Collaborator registered");
                response.put("isSucces", false);
                return response;
            }
            Collaborator collaborator = Collaborator.builder()
                    .idDocument(createDTO.getIdDocument())
                    .role(createDTO.getRole())
                    .status(true)
                    .build();
            collaboratorRepository.save(collaborator);
            response.put("message", "Collaborator create successfully");
            response.put("isSucces", true);
            return response;
        } catch (Exception e) {
            response.put("message", "Error Exception");
            response.put("isSucces", false);
            System.out.println("Error Exception: " + e.getMessage());
            return response;
        }
    }

    @Override
    public Page<Collaborator> listPagination(CollaboratorRequestPaginationDTO filtro) {
        Pageable pageable = PageRequest.of(filtro.getPage(), filtro.getSize());
        return collaboratorRepository.findAll(CollaboratorSpecifications.filtrarPorCriterios(filtro),pageable);
    }

    @Override
    public List<Collaborator> allCollaborators() {
        return collaboratorRepository.findAll();
    }
}
