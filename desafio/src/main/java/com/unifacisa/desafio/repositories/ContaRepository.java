package com.unifacisa.desafio.repositories;

import com.unifacisa.desafio.entities.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Integer> {

    Conta findByIdConta(int id);
}
