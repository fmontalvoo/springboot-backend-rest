package com.fmontalvoo.springboot.api.service;

import java.util.List;

import com.fmontalvoo.springboot.api.entities.Cliente;

public interface ClienteService {

	public List<Cliente> findAll();

}
