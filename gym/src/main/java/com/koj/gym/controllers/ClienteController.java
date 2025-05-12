package com.koj.gym.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/traer-cliente/{id}")
   public ResponseEntity<Cliente> traerCliente(@PathVariable Long id) {
       return clienteRepository.findById(id)
               .map(cliente -> ResponseEntity.ok(cliente))
               .orElse(ResponseEntity.notFound().build());
   }

   @PostMapping("/insertar-clientes")
   public Cliente insertarCliente(@RequestBody Cliente cliente) {
       return clienteRepository.save(cliente);
   }

   @PutMapping("/editar-clientes/{id}")
    public ResponseEntity<Cliente> editarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        return clienteRepository.findById(id)
                .map(clienteExistente -> {
                    clienteExistente.setNombre(cliente.getNombre());
                    clienteExistente.setApellido(cliente.getApellido());
                    clienteExistente.setEmail(cliente.getEmail());
                    clienteExistente.setTelefono(cliente.getTelefono());
                    clienteExistente.setNumeroMembresia(cliente.getNumeroMembresia());
                    clienteExistente.setTipoMembresia(cliente.getTipoMembresia());
                    clienteExistente.setImagenURL(cliente.getImagenURL());
                    Cliente editarAlumno = clienteRepository.save(clienteExistente);
                    return ResponseEntity.ok(editarAlumno);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/eliminar-clientes/{id}")
    public void eliminarCliente(@PathVariable Long id) {
        clienteRepository.deleteById(id);
    }
}