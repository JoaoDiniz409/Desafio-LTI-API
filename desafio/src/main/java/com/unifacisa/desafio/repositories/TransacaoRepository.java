package com.unifacisa.desafio.repositories;

import com.unifacisa.desafio.entities.Conta;
import com.unifacisa.desafio.entities.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {

    List<Transacao> findAllByConta(Conta conta);
}
