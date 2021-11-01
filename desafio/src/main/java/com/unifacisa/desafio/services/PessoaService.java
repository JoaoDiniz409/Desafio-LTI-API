package com.unifacisa.desafio.services;

import com.unifacisa.desafio.dto.PessoaDTO;
import com.unifacisa.desafio.entities.Pessoa;
import com.unifacisa.desafio.repositories.PessoaRepository;
import com.unifacisa.desafio.services.exceptions.ServiceExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<PessoaDTO> findAll() {
        List<Pessoa> result = pessoaRepository.findAll();
        return result.stream().map(x -> new PessoaDTO(x)).collect(Collectors.toList());
    }

    public PessoaDTO insert(PessoaDTO dto) throws ParseException {
        Pessoa pessoa = pessoaRepository.findByCpf(dto.getCpf());
        if (pessoa != null) {
            throw new ServiceExeption("Cpf já existe");
        }

        Pessoa obj = new Pessoa(dto);
        obj = pessoaRepository.save(obj);

        return new PessoaDTO(obj);
    }
}
