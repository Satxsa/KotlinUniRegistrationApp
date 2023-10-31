package com.javainuse.controller;

import com.javainuse.model.HelloResponse;
import com.javainuse.model.JwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin()
public class HelloWorldController {

	@RequestMapping({ "/hello" })
	public ResponseEntity<?> hello() {
		return ResponseEntity.ok(new HelloResponse("Hello World"));
	}

}
