package com.tools.namestore.model;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.StorageClass;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class StorageInfo {
    private  BlobId blobId;
    private  String selfLink;
    private  String cacheControl;
    private  List<Acl> acl;
    private  Acl.Entity owner;
    private  Long size;
    private  String etag;
    private  String md5;
    private  String crc32c;
    private  Long customTime;
    private  String mediaLink;
    private  Map<String, String> metadata;
    private  Long metageneration;
    private  Long deleteTime;
    private  Long updateTime;
    private  Long createTime;
    private  String contentType;
    private  String contentEncoding;
    private  String contentDisposition;
    private  String contentLanguage;
    private  StorageClass storageClass;
    private  Long timeStorageClassUpdated;
    private  Integer componentCount;
    private  boolean isDirectory;
    private  BlobInfo.CustomerEncryption customerEncryption;
    private  String kmsKeyName;
    private  Boolean eventBasedHold;
    private  Boolean temporaryHold;
    private  Long retentionExpirationTime;

}
