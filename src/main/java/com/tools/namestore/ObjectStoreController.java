package com.tools.namestore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ObjectStoreController {
	@Autowired
	private ObjectStoreService objectStoreService;

	@PostMapping("/store/user/{uuid}")
	public void storeUserData(@RequestBody String requestPayLoad, @PathVariable String uuid) {
		objectStoreService.storeUserData(uuid, requestPayLoad);
	}
	
	@GetMapping("/store/user/{uuid}")
	public String  getUserDate(@PathVariable String uuid) {
		return objectStoreService.getUserData(uuid);
	}
}
