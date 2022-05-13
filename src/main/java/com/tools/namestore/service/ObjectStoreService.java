package com.tools.namestore.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class ObjectStoreService {

	public static final String BUCKET_NAME = "name_store_well";

	public String storeUserData(String uuid, MultipartFile file) {

		Storage storage = StorageOptions.getDefaultInstance().getService();
		try {
			BlobInfo blobInfo = storage.create(
					BlobInfo.newBuilder(BUCKET_NAME, uuid).build(), //get original file name
					file.getBytes(), // the file
					Storage.BlobTargetOption.predefinedAcl(Storage.PredefinedAcl.PUBLIC_READ) // Set file permission
			);
			log.info("Uploaded file successfully to cloud storage "+blobInfo.toString());
			return blobInfo.toString(); // Return file url
		}catch(IllegalStateException | IOException e){
			throw new RuntimeException(e);
		}
	}
	
	public Blob getUserData(String uuid) {

		Storage storage = StorageOptions.getDefaultInstance().getService();

		Blob blob = storage.get(BUCKET_NAME,uuid);
		
		return blob;
	}
	
}
