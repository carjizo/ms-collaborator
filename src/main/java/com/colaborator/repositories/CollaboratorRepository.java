package com.colaborator.repositories;

import com.colaborator.entities.Collaborator;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CollaboratorRepository extends JpaRepository<Collaborator, String>, JpaSpecificationExecutor<Collaborator> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE collaborator SET full_name=?2, phone=?3, role=?4, status=?5  WHERE id_document=?1")
    void update(String idDocument, String fullName, String phone, String role, boolean status);
}
