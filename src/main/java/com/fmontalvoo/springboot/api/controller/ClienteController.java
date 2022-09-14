package com.fmontalvoo.springboot.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ResponseEntity<?> findAll() {
		try {
			return new ResponseEntity<List<Cliente>>(service.findAll(), HttpStatus.OK);
		} catch (DataAccessException e) {
			Map<String, String> response = new HashMap<String, String>();
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody Cliente cliente, BindingResult result) {
		Map<String, Object> response = new HashMap<String, Object>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> err.getField().concat(": ").concat(err.getDefaultMessage()))
					.collect(Collectors.toList());

			response.put("mensaje", "Los datos ingresados no son correctos");
			response.put("datos_erroneos", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			service.save(cliente);
			return new ResponseEntity<Cliente>(cliente, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.put("mensaje", e.getMessage().concat("-> ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Cliente nuevo, BindingResult result) {
		Map<String, Object> response = new HashMap<String, Object>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> err.getField().concat("-> ").concat(err.getDefaultMessage()))
					.collect(Collectors.toList());

			response.put("mensaje", "Los datos ingresados no son correctos");
			response.put("datos_erroneos", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			Cliente cliente = service.findById(id).map(cli -> {
				cli.setEmail(nuevo.getEmail());
				cli.setNombre(nuevo.getNombre());
				cli.setApellido(nuevo.getApellido());
				cli.setCreatedAt(cli.getCreatedAt());
				return service.save(cli);
			}).orElse(null);

			if (cliente == null) {
				response.put("mensaje", "Usuario no encontrado");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<Cliente>(cliente, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Map<String, String> response = new HashMap<String, String>();
		try {
			Cliente cliente = service.findById(id).orElse(null);

			if (cliente == null) {
				response.put("mensaje", "Usuario no encontrado");
				return new ResponseEntity<Map<String, String>>(response, HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, String> response = new HashMap<String, String>();
		try {
			Cliente cliente = service.findById(id).orElse(null);

			if (cliente == null) {
				response.put("mensaje", "Usuario no encontrado");
				return new ResponseEntity<Map<String, String>>(response, HttpStatus.NOT_FOUND);
			}

			service.delete(id);

			response.put("mensaje", "Usuario: ".concat(cliente.getNombreCompleto()).concat(" eliminado"));
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
