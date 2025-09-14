package com.github.devopMarkz.cliente_service.repositories;

import com.github.devopMarkz.cliente_service.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findClienteByEmail(String email);

}
