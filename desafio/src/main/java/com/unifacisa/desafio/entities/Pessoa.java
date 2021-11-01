package com.unifacisa.desafio.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.unifacisa.desafio.dto.PessoaDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Identificador único para uma pessoa")
    @Column(name = "id_pessoa")
    private int idPessoa;

    @ApiModelProperty(value = "O nome da pessoa")
    @Column(name = "nome")
    private String nome;

    @ApiModelProperty(value = "O cpf da pessoa")
    @Column(name = "cpf")
    private String cpf;

    @ApiModelProperty(value = "A data de aniversario da pessoa")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private String dataNascimento;

    @OneToMany(mappedBy = "pessoa")
    private List<Conta> contas = new ArrayList<>();

    public Pessoa(PessoaDTO pessoaDTO) {
        this.nome = pessoaDTO.getNome();
        this.cpf = pessoaDTO.getCpf();
        this.dataNascimento = pessoaDTO.getDataNascimento();
        this.contas = new ArrayList<>();
    }
}
