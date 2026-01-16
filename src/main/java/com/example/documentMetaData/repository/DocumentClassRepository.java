package com.example.documentMetaData.repository;

import com.example.documentMetaData.entity.DocumentClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentClassRepository extends JpaRepository<DocumentClass, Long> {

    DocumentClass findByNameIgnoreCase(String name);
}
