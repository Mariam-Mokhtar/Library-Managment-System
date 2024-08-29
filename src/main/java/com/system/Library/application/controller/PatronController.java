package com.system.Library.application.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.system.Library.application.model.request.PatronReqModel;
import com.system.Library.application.model.response.PatronResModel;
import com.system.Library.application.service.PatronService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

//	   GET /api/patrons: Retrieve a list of all patrons.
//	 ● GET /api/patrons/{id}: Retrieve details of a specific patron by ID.
//	 ● POST /api/patrons: Add a new patron to the system.
//	 ● PUT /api/patrons/{id}: Update an existing patron's information.
//	 ● DELETE /api/patrons/{id}: Remove a patron from the system.

	@Autowired
	PatronService patronService;

	@Operation(summary = "Retrieve a list of all patrons")
	@GetMapping
	public ResponseEntity<List<PatronResModel>> getAllPatrons() {
		return new ResponseEntity<>(patronService.getAllPatrons(), HttpStatus.OK);
	}

	@Operation(summary = "Retrieve details of a specific patron by ID")
	@GetMapping("/{id}")
	public ResponseEntity<PatronResModel> getPatronById(@PathVariable("id") int id) {
		return new ResponseEntity<>(patronService.getPatronById(id), HttpStatus.OK);
	}

	@Operation(summary = "Add a new patron to the system")
	@PostMapping
	public ResponseEntity<Void> createPatron(@Valid @RequestBody PatronReqModel patronReqModel) {
		patronService.createPatron(patronReqModel);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(summary = "Update an existing patron's information")
	@PutMapping("/{id}")
	public ResponseEntity<PatronResModel> updatePatronById(@Valid @RequestBody PatronReqModel patronReqModel,
			@PathVariable("id") int id) {
		return new ResponseEntity<>(patronService.updatePatronById(patronReqModel, id), HttpStatus.OK);
	}

	@Operation(summary = "Remove a patron from the library")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePatron(@PathVariable("id") int id) {
		patronService.deletePatron(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
