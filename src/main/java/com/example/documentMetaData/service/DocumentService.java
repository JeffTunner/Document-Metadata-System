package com.example.documentMetaData.service;

import com.example.documentMetaData.entity.Document;
import com.example.documentMetaData.entity.DocumentClass;
import com.example.documentMetaData.entity.MetadataField;
import com.example.documentMetaData.repository.DocumentClassRepository;
import com.example.documentMetaData.repository.MetadataFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

    @Autowired
    DocumentClassRepository classRepository;

    @Autowired
    MetadataFieldRepository fieldRepository;

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

    }
}
