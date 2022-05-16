package com.tools.namestore.controller;

import com.tools.namestore.model.Audio;
import com.tools.namestore.model.ResponseMessage;
import com.tools.namestore.model.StorageInfo;
import com.tools.namestore.service.ObjectStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/store")
@Slf4j
public class ObjectStoreController {
	@Autowired
	private ObjectStoreService objectStoreService;

	@PostMapping(value = "/user/{uuid}" 	)
	public ResponseEntity<ResponseMessage> storeUserFileData(@RequestParam("file") MultipartFile file, @PathVariable String uuid) {
		String message = "";
		try {
			StorageInfo metadata = objectStoreService.storeUserData(uuid,file);
			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(message,metadata));
		} catch (Exception e) {
			log.error("Failed to upload the file "+ e.getMessage());
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message,null));
		}
	}

	@GetMapping(value = "/users")
	public List<String> getAllUserKeys() {
		return objectStoreService.getAllKeys();
	}

	@PostMapping("/user/{uuid}/audio")
	public ResponseEntity<ResponseMessage> storeUserData(@RequestBody Audio audio, @PathVariable String uuid) {
		String message = "";
		try {
			StorageInfo metadata = objectStoreService.storeBinaryUserData(uuid,audio);
			message = "Data processed successfully";
			return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(message,metadata));
		} catch (Exception e) {
			log.error("Failed to upload the Data "+ e.getMessage());
			message = "Error Processing data!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message,null));
		}
	}

	@GetMapping(value = "/user/{uuid}/data")
	public ResponseEntity<ResponseMessage>  getFileMetaData(@PathVariable String uuid) {
		StorageInfo storeInfo = objectStoreService.getStorageInfo(uuid);
		if(Objects.nonNull(storeInfo)){
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Object Info for user "+uuid,storeInfo));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Object not found for user "+uuid,null));
	}

}
