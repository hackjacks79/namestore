package com.tools.namestore.model;

import lombok.Data;

@Data
public class ResponseMessage {
    private String message;
    private StorageInfo blobInfo;
    public ResponseMessage(String message,StorageInfo blobInfo) {
        this.message = message;
        this.blobInfo = blobInfo;
    }
}
