package com.valora.api.dto;

import java.time.LocalDate;

public record CadastroUsuarioDTO(
        String nome,
        String email,
        String senha,
        String cpf,
        LocalDate dataNascimento,
        DadosEndereco endereco
) {}