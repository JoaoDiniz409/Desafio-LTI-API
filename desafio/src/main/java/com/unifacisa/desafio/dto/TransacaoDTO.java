package com.unifacisa.desafio.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoDTO implements Serializable {

    private int idTransacao;
    private double valor;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataTransacao;

    private ContaDTO conta;

}
