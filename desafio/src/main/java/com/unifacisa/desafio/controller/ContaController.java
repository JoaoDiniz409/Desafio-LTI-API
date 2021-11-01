package com.unifacisa.desafio.controller;

import com.unifacisa.desafio.dto.ContaDTO;
import com.unifacisa.desafio.dto.DepositoSaqueDTO;
import com.unifacisa.desafio.dto.SaldoDTO;
import com.unifacisa.desafio.services.ContaService;
import com.unifacisa.desafio.services.exceptions.ServiceExeption;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.util.List;


@RestController()
@RequestMapping(value = "/api/contas", produces = "application/json")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @ApiOperation(value="Retorna uma lista de Contas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 404, message = "Contas não encontradas"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping
    public ResponseEntity<List<ContaDTO>> findAll() {
        List<ContaDTO> list = contaService.findAll();
        return ResponseEntity.ok(list);
    }

    @ApiOperation(value="Criar uma Conta")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 404, message = "Conta não criada"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping
    public ResponseEntity<ContaDTO> criarConta(@RequestBody ContaDTO dto) {
        try {
            ContaDTO obj = contaService.insert(dto);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(obj.getIdConta()).toUri();
            return ResponseEntity.created(uri).body(obj);
        } catch (ServiceExeption | ParseException e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @ApiOperation(value="Faz um deposito")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 404, message = "Deposito não realizado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping("/depositar")
    public ResponseEntity<ContaDTO> depositar(@RequestBody DepositoSaqueDTO dto) {
        try {
            ContaDTO obj = contaService.deposito(dto);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(obj.getIdConta()).toUri();
            return ResponseEntity.created(uri).body(obj);
        } catch (ServiceExeption e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @ApiOperation(value="Faz um Saque")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 404, message = "Saque não realizado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping("/sacar")
    public ResponseEntity<ContaDTO> sacar(@RequestBody DepositoSaqueDTO dto) {
        try {
            ContaDTO obj = contaService.sacar(dto);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(obj.getIdConta()).toUri();
            return ResponseEntity.created(uri).body(obj);
        } catch (ServiceExeption e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @ApiOperation(value="Bloqueia uma conta")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 404, message = "Conta não bloqueada"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping("/bloquear/{idConta}")
    public ResponseEntity<ContaDTO> bloquear(@PathVariable int idConta) {
        try {
            ContaDTO obj = contaService.bloqueio(idConta);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(obj.getIdConta()).toUri();
            return ResponseEntity.created(uri).body(obj);
        } catch (ServiceExeption e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @ApiOperation(value="Verificar saldo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 404, message = "Conta não encontrada"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping("/saldo/{idConta}")
    public ResponseEntity<SaldoDTO> saldo(@PathVariable int idConta) {
        try {
            SaldoDTO obj = contaService.consultarSaldo(idConta);
            return ResponseEntity.status(HttpStatus.OK.value()).body(obj);
        } catch (ServiceExeption e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }


}
