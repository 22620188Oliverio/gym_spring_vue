package com.koj.gym.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.koj.gym.model.Cliente;
import com.koj.gym.repository.ClienteRepository;


@RestController
@RequestMapping("/gym/cliente")
public class ClienteController {
    @Autowired
   private ClienteRepository clienteRepository;

   @GetMapping("/traer-clientes")
   public List<Cliente> traerClientes() {
       return clienteRepository.findAll();
   }

}
