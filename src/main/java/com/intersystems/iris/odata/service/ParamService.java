package com.intersystems.iris.odata.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.intersystems.iris.odata.model.Param;
import com.intersystems.iris.odata.repository.ParamRepository;

@RestController
public class ParamService {

	@Autowired
	private ParamRepository repository;

	@GetMapping("/param")
	public List<Param> getDatabaseInfoList() {
		return repository.findAll();
	}

	@GetMapping("/param/{id}")
	public ResponseEntity<Param> findById(@PathVariable(value = "id") Integer id) {
		Param param = repository.findById(id).get();
		return ResponseEntity.ok().body(param);
	}

	@PostMapping("/param")
	public Param save(@RequestBody Param param) {
		return repository.save(param);
	}

	@DeleteMapping("/param/{id}")
	public Map<String, Boolean> delete(@PathVariable(value = "id") Integer id) {
		
		Param param = repository.findById(id).get();
		
		repository.delete(param);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		
		return response;
	}

}
