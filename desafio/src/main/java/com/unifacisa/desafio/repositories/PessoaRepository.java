package com.unifacisa.desafio.repositories;

import com.unifacisa.desafio.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    Pessoa findByCpf(String cpf);

    Pessoa findByIdPessoa(int id);
}
