package com.example.documentMetaData.service;

import com.example.documentMetaData.dto.DocumentMetadataRequestDto;
import com.example.documentMetaData.entity.Document;
import com.example.documentMetaData.entity.DocumentClass;
import com.example.documentMetaData.entity.DocumentMetadata;
import com.example.documentMetaData.entity.MetadataField;
import com.example.documentMetaData.repository.DocumentClassRepository;
import com.example.documentMetaData.repository.DocumentMetadataRepository;
import com.example.documentMetaData.repository.DocumentRepository;
import com.example.documentMetaData.repository.MetadataFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {

    @Autowired
    DocumentClassRepository classRepository;

    @Autowired
    MetadataFieldRepository fieldRepository;

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    DocumentMetadataRepository metadataRepository;

    public DocumentClass defineClass(DocumentClass documentClass) {
        return classRepository.save(documentClass);
    }

    public MetadataField addFields(String className, MetadataField metadataField) {
        DocumentClass documentClass = classRepository.findByNameIgnoreCase(className);
        metadataField.setDocumentClass(documentClass);
        return fieldRepository.save(metadataField);
    }

    public Document createDocument(Document document) {
        String className = document.getClassName();
        DocumentClass documentClass = classRepository.findByNameIgnoreCase(className);
        document.setDocumentClass(documentClass);
        document.setStatus("DRAFT");
        return documentRepository.save(document);
    }

    public DocumentMetadata addingValues(Long id, DocumentMetadataRequestDto metadataRequestDto) {
        Document document = documentRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
        MetadataField metadataField = fieldRepository.findById(metadataRequestDto.getFieldId()).orElseThrow(() -> new RuntimeException("No MetadataField Found"));
        if(!metadataField.getDocumentClass().getId().equals(document.getDocumentClass().getId())) {
            throw new RuntimeException("Field does not belong to this Document Class");
        }
            DocumentMetadata documentMetadata = new DocumentMetadata();
            documentMetadata.setDocument(document);
            documentMetadata.setField(metadataField);
            documentMetadata.setFieldValue(metadataRequestDto.getFieldValue());
            return metadataRepository.save(documentMetadata);
    }

    public String finalizeDocument(Long id) {
        Document document = documentRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
        if(document.getStatus().equals("FINALIZED")) {
            throw new IllegalStateException("Already Finalized!");
        }
        List<MetadataField> fields = document.getDocumentClass().getFields();
        List<DocumentMetadata> fieldValues = metadataRepository.findByDocumentId(id);
        if (fields.stream().count() != fieldValues.stream().count()) {
            document.setStatus("ARCHIVED");
            documentRepository.save(document);
            return "Not Finalized";
        } else {
            document.setStatus("FINALIZED");
            documentRepository.save(document);
            return "Document is Finalized" ;
        }
    }
}
