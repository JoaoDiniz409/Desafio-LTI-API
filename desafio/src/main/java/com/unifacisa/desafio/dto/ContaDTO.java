package com.unifacisa.desafio.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.unifacisa.desafio.entities.Conta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaDTO implements Serializable {

    private int idConta;
    private double saldo;
    private double limiteSaqueDiario;
    private boolean flagAtivo;
    private int tipoConta;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private String dataCriacao;
    private int idPessoa;

    public ContaDTO(Conta entity) {
        idConta = entity.getIdConta();
        saldo = entity.getSaldo();
        limiteSaqueDiario = entity.getLimiteSaqueDiario();
        flagAtivo = entity.isFlagAtivo();
        tipoConta = entity.getTipoConta();
        dataCriacao = entity.getDataCriacao();
        idPessoa = entity.getPessoa().getIdPessoa();
    }

}
