package com.fmontalvoo.springboot.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fmontalvoo.springboot.api.dao.ClienteDao;
import com.fmontalvoo.springboot.api.entities.Cliente;

@Service
public class ClienteServiceImp implements ClienteService {

	@Autowired
	private ClienteDao dao;

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>) dao.findAll();
	}

	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
		return dao.save(cliente);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Cliente> findById(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		dao.deleteById(id);
	}

}
