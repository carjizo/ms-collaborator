package com.colaborator.entities;

import com.colaborator.dtos.CollaboratorRequestPaginationDTO;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CollaboratorSpecifications {
    public static Specification<Collaborator> filtrarPorCriterios(CollaboratorRequestPaginationDTO filtro) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filtro.getIdDocument() != null && !filtro.getIdDocument().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("idDocument"), "%" + filtro.getIdDocument() + "%"));
            }
            if (filtro.getFullName() != null && !filtro.getFullName().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("fullName"), filtro.getFullName().toUpperCase()));
            }
            if (filtro.getRole() != null && !filtro.getRole().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("role"), filtro.getRole()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
