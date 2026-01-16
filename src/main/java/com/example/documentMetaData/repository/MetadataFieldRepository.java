package com.example.documentMetaData.repository;

import com.example.documentMetaData.entity.MetadataField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetadataFieldRepository extends JpaRepository<MetadataField, Long> {
}
