package com.tools.namestore.service;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.tools.namestore.model.Audio;
import com.tools.namestore.model.StorageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class ObjectStoreService {

	public static final String BUCKET_NAME = "name_store_well";

	public StorageInfo storeUserData(String id, MultipartFile file) {
		String uuid = id.toLowerCase(Locale.ROOT);

		Storage storage = StorageOptions.getDefaultInstance().getService();
		try {
			BlobInfo blobInfo = storage.create(
					BlobInfo.newBuilder(BUCKET_NAME, uuid).build(), //get original file name
					file.getBytes(), // the file
					Storage.BlobTargetOption.predefinedAcl(Storage.PredefinedAcl.PUBLIC_READ) // Set file permission
			);
			log.info("Uploaded file successfully to cloud storage "+blobInfo.toString());
			return getMetaDataFromBlob(blobInfo);
		}catch(IllegalStateException | IOException e){
			throw new RuntimeException(e);
		}
	}
	
	public Blob getUserData(String uuid) {

		Storage storage = StorageOptions.getDefaultInstance().getService();

		return storage.get(BUCKET_NAME,uuid);
	}

	public StorageInfo storeBinaryUserData(String id, Audio audio) {

		String uuid = id.toLowerCase(Locale.ROOT);
		Storage storage = StorageOptions.getDefaultInstance().getService();
		BlobInfo blobInfo = storage.create(
				BlobInfo.newBuilder(BUCKET_NAME, uuid).build(), //get original file name
				Base64.getDecoder().decode(audio.getInboundEncodedData()), // the file
				Storage.BlobTargetOption.predefinedAcl(Storage.PredefinedAcl.PUBLIC_READ) // Set file permission
		);
		log.info("Uploaded file successfully to cloud storage "+blobInfo.toString());
		return getMetaDataFromBlob(blobInfo); // Return file url
	}

	public List<String> getAllKeys(){
		Storage storage = StorageOptions.getDefaultInstance().getService();
		Page<Blob> blobs = storage.list(BUCKET_NAME);
		List<String> listOfKeys = new ArrayList<>();
		for (Blob blob : blobs.iterateAll()) {
			listOfKeys.add(blob.getName());
		}
		return listOfKeys;
	}

	public StorageInfo getStorageInfo(String uuid){
		Storage storage = StorageOptions.getDefaultInstance().getService();
		Page<Blob> blobs = storage.list(BUCKET_NAME);
		for (Blob blob : blobs.iterateAll()) {
			if(blob.getName().equals(uuid)){
				return getMetaDataFromBlob(blob);
			};
		}
		return null;
	}

	private StorageInfo getMetaDataFromBlob(BlobInfo blobInfo) {
		StorageInfo metadata = new StorageInfo();
		metadata.setBlobId(blobInfo.getBlobId());
		metadata.setCreateTime(blobInfo.getCreateTime());
		metadata.setContentType(blobInfo.getContentType());
		metadata.setUpdateTime(blobInfo.getUpdateTime());
		metadata.setCreateTime(blobInfo.getCreateTime());
		metadata.setSize(blobInfo.getSize());
		metadata.setMediaLink(blobInfo.getMediaLink());
		metadata.setMediaLink(blobInfo.getMediaLink());
		return metadata;
	}
	
}
