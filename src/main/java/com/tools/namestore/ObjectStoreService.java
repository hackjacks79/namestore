package com.tools.namestore;

import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
public class ObjectStoreService {

	public void storeUserData(String uuid, String payLoad) {
		String bucketName = "name_store_well";
		Storage storage = StorageOptions.getDefaultInstance().getService();

		Bucket bucket = storage.get(bucketName, Storage.BucketGetOption.fields(Storage.BucketField.values()));

		if (bucket == null) {
			bucket = storage.create(BucketInfo.of(bucketName));
		}

		byte[] bytes = payLoad.getBytes(StandardCharsets.UTF_8);
		Blob blob = bucket.create(uuid, bytes);

	}
	
	public String getUserData(String uuid) {
		String bucketName = "name_store_well";
		Storage storage = StorageOptions.getDefaultInstance().getService();

		Bucket bucket = storage.get(bucketName, Storage.BucketGetOption.fields(Storage.BucketField.values()));

		if (bucket == null) {
			bucket = storage.create(BucketInfo.of(bucketName));
		}

		Blob blob = bucket.get(uuid);
		
		return new String(blob.getContent());
	}
	
}
