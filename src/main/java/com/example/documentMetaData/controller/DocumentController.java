package com.example.documentMetaData.controller;

import com.example.documentMetaData.entity.DocumentClass;
import com.example.documentMetaData.entity.MetadataField;
import com.example.documentMetaData.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DocumentController {

    @Autowired
    DocumentService documentService;

    @PostMapping("/classes")
    public DocumentClass create(@RequestBody DocumentClass documentClass) {
        return documentService.defineClass(documentClass);
    }

    @PostMapping("/classes/{className}/fields")
    public MetadataField add(@PathVariable String className, @RequestBody MetadataField metadataField) {
        return documentService.addFields(className, metadataField);
    }
}
