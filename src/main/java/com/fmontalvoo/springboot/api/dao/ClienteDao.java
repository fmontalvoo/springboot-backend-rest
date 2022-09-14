package com.fmontalvoo.springboot.api.dao;

import org.springframework.data.repository.CrudRepository;

import com.fmontalvoo.springboot.api.entities.Cliente;

public interface ClienteDao extends CrudRepository<Cliente, Long> {

}
