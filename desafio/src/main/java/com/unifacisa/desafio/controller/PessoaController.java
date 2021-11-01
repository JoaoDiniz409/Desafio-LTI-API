package com.unifacisa.desafio.controller;

import com.unifacisa.desafio.dto.PessoaDTO;
import com.unifacisa.desafio.services.PessoaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/pessoas", produces = "application/json")
@Api(value="API REST Pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @ApiOperation(value="Retorna uma lista de Pessoas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 404, message = "Pessoas não encontradas"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping
    public ResponseEntity<List<PessoaDTO>> findAll() {
        List<PessoaDTO> result = pessoaService.findAll();
        return ResponseEntity.ok(result);
    }


    @ApiOperation(value="Cadastrar uma Pessoa")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 404, message = "Pessoa não cadastrada"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping
    public ResponseEntity<PessoaDTO> insert(@RequestBody PessoaDTO dto) {
        try {
            PessoaDTO obj = pessoaService.insert(dto);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(obj.getIdPessoa()).toUri();
            return ResponseEntity.created(uri).body(obj);
        } catch (SecurityException | ParseException e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }
}
