package com.fmontalvoo.springboot.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fmontalvoo.springboot.api.entities.Cliente;
import com.fmontalvoo.springboot.api.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService service;

	@GetMapping
	public List<Cliente> findAll() {
		return service.findAll();
	}

}
