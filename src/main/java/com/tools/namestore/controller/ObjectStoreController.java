package com.tools.namestore.controller;

import com.google.cloud.storage.Blob;
import com.google.common.io.ByteSource;
import com.tools.namestore.model.ResponseMessage;
import com.tools.namestore.service.ObjectStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/store")
@Slf4j
public class ObjectStoreController {
	@Autowired
	private ObjectStoreService objectStoreService;

	@PostMapping("/user/{uuid}")
	public ResponseEntity<ResponseMessage> storeUserData(@RequestParam("file") MultipartFile file, @PathVariable String uuid) {
		String message = "";
		try {
			String matadata = objectStoreService.storeUserData(uuid,file);
			file.getContentType();
			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(message+" "+matadata));
		} catch (Exception e) {
			log.error("Failed to upload the file "+ e.getMessage());
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}

	@GetMapping(value = "/user/{uuid}")
	public ResponseEntity<InputStreamResource> getFile(@PathVariable String uuid) {
		Blob blob = objectStoreService.getUserData(uuid);
		try {
			InputStreamResource resource = new InputStreamResource(ByteSource.wrap(blob.getContent()).openStream());
			return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType(blob.getContentType()))
					.contentLength(blob.getContent().length)
					.body(resource);

		} catch (IOException e) {
			log.error("Failed to get the data + "+e.getMessage());
			return new ResponseEntity<InputStreamResource>(HttpStatus.EXPECTATION_FAILED);
		}
	}

}
