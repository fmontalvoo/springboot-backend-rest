package com.fmontalvoo.springboot.api.service;

import java.util.List;

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

}
