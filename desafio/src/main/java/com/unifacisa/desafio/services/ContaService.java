package com.unifacisa.desafio.services;

import com.unifacisa.desafio.dto.ContaDTO;
import com.unifacisa.desafio.dto.DepositoSaqueDTO;
import com.unifacisa.desafio.dto.SaldoDTO;
import com.unifacisa.desafio.entities.Conta;
import com.unifacisa.desafio.entities.Pessoa;
import com.unifacisa.desafio.entities.Transacao;
import com.unifacisa.desafio.repositories.ContaRepository;
import com.unifacisa.desafio.repositories.PessoaRepository;
import com.unifacisa.desafio.repositories.TransacaoRepository;
import com.unifacisa.desafio.services.exceptions.ServiceExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private TransacaoRepository transacaoRepository;

    public List<ContaDTO> findAll() {
        List<Conta> result = contaRepository.findAll();
        return result.stream().map(x -> new ContaDTO(x)).collect(Collectors.toList());
    }

    public ContaDTO insert(ContaDTO dto) throws ParseException {

        Pessoa pessoa = pessoaRepository.findByIdPessoa(dto.getIdPessoa());
        Conta obj = new Conta(dto, pessoa);

        obj = contaRepository.save(obj);

        return new ContaDTO(obj);
    }

    public ContaDTO deposito(DepositoSaqueDTO dto) {
        Conta conta = contaRepository.findByIdConta(dto.getIdConta());

        if (conta == null) {
            throw new ServiceExeption("Conta não encontrada!");
        }

        if (conta.isFlagAtivo()) {
            conta.depositar(dto.getValor());
            conta = contaRepository.save(conta);
            salvarTransacao(conta, dto.getValor());
        } else {
            throw new RuntimeException("A conta está bloqueada!");
        }

        return new ContaDTO(conta);
    }

    public SaldoDTO consultarSaldo(Integer id) {
        Conta conta = contaRepository.findByIdConta(id);

        if (conta == null) {
            throw new ServiceExeption("Conta não encontrada!");
        }

        SaldoDTO saldo = new SaldoDTO();
        saldo.setSaldo(conta.getSaldo());

        return saldo;
    }

    public ContaDTO sacar(DepositoSaqueDTO dto) {
        Conta conta = contaRepository.findByIdConta(dto.getIdConta());

        if (conta == null) {
            throw new ServiceExeption("Conta não encontrada!");
        }

        List<Transacao> transacaos = transacaoRepository.findAllByConta(conta);

        if (checaLimiteSaque(conta, transacaos, dto.getValor())) {
            throw new RuntimeException("Excedeu o limite diário de saques!");
        }

        if (conta.isFlagAtivo()) {
            conta.sacar(dto.getValor());
            conta = contaRepository.save(conta);
            salvarTransacao(conta, -dto.getValor());
        } else {
            throw new RuntimeException("A conta está bloqueada!");
        }

        return new ContaDTO(conta);
    }

    public ContaDTO bloqueio(int id) {
        Conta conta = contaRepository.findByIdConta(id);

        if (conta == null) {
            throw new ServiceExeption("Conta não encontrada!");
        }

        conta.desativar();
        contaRepository.save(conta);

        return new ContaDTO(conta);
    }

    private void salvarTransacao(Conta conta, double valor) {
        Transacao transacao = new Transacao();
        transacao.setDataTransacao(LocalDate.now());
        transacao.setConta(conta);
        transacao.setValor(valor);

        transacaoRepository.save(transacao);
    }

    private boolean checaLimiteSaque(Conta conta, List<Transacao> transacaos, double valorSaque) {
        double soma = -valorSaque;
        for (Transacao transacao : transacaos) {
            double valor = transacao.getValor();
            if (valor < 0 && transacao.getDataTransacao().equals(LocalDate.now())) {
                soma += valor;
            }
        }

        return -soma >= conta.getLimiteSaqueDiario();
    }
}
