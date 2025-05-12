package com.koj.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.koj.gym.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario

}
