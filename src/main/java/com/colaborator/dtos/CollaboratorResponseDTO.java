package com.colaborator.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CollaboratorResponseDTO {

    private String idDocument;
    private String fullName;
    private String phone;
    private String role;
    private boolean status;
}
