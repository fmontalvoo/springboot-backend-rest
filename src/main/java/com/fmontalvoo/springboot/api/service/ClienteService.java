package com.fmontalvoo.springboot.api.service;

import java.util.List;
import java.util.Optional;

import com.fmontalvoo.springboot.api.entities.Cliente;

public interface ClienteService {

	public List<Cliente> findAll();

	public Cliente save(Cliente cliente);

	public Optional<Cliente> findById(Long id);

	public void delete(Long id);

}
