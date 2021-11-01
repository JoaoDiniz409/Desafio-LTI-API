package com.unifacisa.desafio.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.unifacisa.desafio.entities.Pessoa;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PessoaDTO implements Serializable {

    private int idPessoa;
    private String nome;
    private String cpf;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private String dataNascimento;

    public PessoaDTO() {
    }

    public PessoaDTO(int idPessoa, String nome, String cpf, String dataNascimento) {
        this.idPessoa = idPessoa;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    public PessoaDTO(Pessoa entity) {
        idPessoa = entity.getIdPessoa();
        nome = entity.getNome();
        cpf = entity.getCpf();
        dataNascimento = entity.getDataNascimento();
    }
}
