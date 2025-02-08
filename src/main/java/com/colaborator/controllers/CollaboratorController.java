package com.colaborator.controllers;

import com.colaborator.dtos.CollaboratorCreateDTO;
import com.colaborator.dtos.CollaboratorRequestPaginationDTO;
import com.colaborator.dtos.CollaboratorUpdateDTO;
import com.colaborator.entities.Collaborator;
import com.colaborator.services.CollaboratorService;
import com.colaborator.utils.ExcelGenerator;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/collaborator")
public class CollaboratorController {

    @Autowired
    private CollaboratorService collaboratorService;

    @GetMapping("/find-by-id/{idDocument}")
    public ResponseEntity<HashMap<String, Object>> findById(@PathVariable String idDocument) throws Exception {
        return new ResponseEntity<>(collaboratorService.findById(idDocument), HttpStatus.OK);
    }

    @PostMapping("/update")
    private ResponseEntity<HashMap<String, Object>> update(@RequestBody CollaboratorUpdateDTO updateDTO) throws Exception {
        HashMap<String, Object> rsp = collaboratorService.update(updateDTO);
        return new ResponseEntity<>(rsp, HttpStatus.OK);
    }

    @PostMapping("/create")
    private ResponseEntity<HashMap<String, Object>> create(@RequestBody CollaboratorCreateDTO createDTO) throws Exception {
        HashMap<String, Object> rsp = collaboratorService.create(createDTO);
        return new ResponseEntity<>(rsp, HttpStatus.OK);
    }

    @PostMapping("/list/pagination")
    public Page<Collaborator> listPagination(@RequestBody CollaboratorRequestPaginationDTO filtro) {
        return collaboratorService.listPagination(filtro);
    }

    @GetMapping("/export-to-excel")
    public void exportIntoExcelFile(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=colaboradores_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Collaborator> collaboratorList = collaboratorService.allCollaborators();
        ExcelGenerator generator = new ExcelGenerator(collaboratorList);
        generator.generateExcelFile(response);
    }
}
