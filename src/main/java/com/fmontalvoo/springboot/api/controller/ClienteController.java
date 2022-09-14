package com.fmontalvoo.springboot.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fmontalvoo.springboot.api.entities.Cliente;
import com.fmontalvoo.springboot.api.service.ClienteService;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = { "http://localhost:4200" })
public class ClienteController {

	@Autowired
	private ClienteService service;

	@GetMapping
	public List<Cliente> findAll() {
		return service.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente save(@RequestBody Cliente cliente) {
		return service.save(cliente);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente update(@PathVariable Long id, @RequestBody Cliente nuevo) {
		return service.findById(id).map(cliente -> {
			cliente.setEmail(nuevo.getEmail());
			cliente.setNombre(nuevo.getNombre());
			cliente.setApellido(nuevo.getApellido());
			cliente.setCreatedAt(cliente.getCreatedAt());
			return service.save(cliente);
		}).orElse(null);
	}

	@GetMapping("/{id}")
	public Cliente findById(@PathVariable Long id) {
		return service.findById(id).orElse(null);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}

}
