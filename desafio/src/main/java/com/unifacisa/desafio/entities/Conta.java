package com.unifacisa.desafio.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.unifacisa.desafio.dto.ContaDTO;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Conta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Identificador único para a conta")
    @Column(name = "id_conta")
    private int idConta;

    @ApiModelProperty(value = "O saldo da conta")
    @Column(name = "saldo")
    private double saldo;

    @ApiModelProperty(value = "O limite de saque diario da conta")
    @Column(name = "limite_saque_diario")
    private double limiteSaqueDiario;

    @ApiModelProperty(value = "A flag ativa que vai intetificar conta ativa ")
    @Column(name = "flag_ativo")
    private boolean flagAtivo;

    @ApiModelProperty(value = "Tipo de conta")
    @Column(name = "tipo_conta")
    private int tipoConta;

    @ApiModelProperty(value = "A data de criação da conta")
    @Column(name = "data_criacao")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private String dataCriacao;

    @OneToMany(mappedBy = "conta")
    private List<Transacao> transacaos = new ArrayList<>();

    @ApiModelProperty(value = "Identificador do dono da conta")
    @ManyToOne
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa;

    public Conta(ContaDTO contaDTO, Pessoa pessoa) {
        this.saldo = contaDTO.getSaldo();
        this.limiteSaqueDiario = contaDTO.getLimiteSaqueDiario();
        this.flagAtivo = true;
        this.tipoConta = contaDTO.getTipoConta();
        this.dataCriacao = contaDTO.getDataCriacao();
        this.transacaos = new ArrayList<>();
        this.pessoa = pessoa;
    }

    public void depositar(double valor) {
        this.saldo += valor;
    }

    public void sacar(double valor) {
        if (this.saldo >= valor) {
            this.saldo -= valor;
        } else {
            throw new RuntimeException("O valor do saldo é insuficiente!");
        }
    }

    public void ativar() {
        this.flagAtivo = true;
    }

    public void desativar() {
        this.flagAtivo = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conta conta = (Conta) o;
        return idConta == conta.idConta;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idConta);
    }
}
