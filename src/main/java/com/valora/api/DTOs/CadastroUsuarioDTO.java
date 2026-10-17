package com.valora.api.DTOs;

import java.time.LocalDate;

public record CadastroUsuarioDTO(
        String nome,
        String email,
        String senha,
        String cpf,
        LocalDate DataNascimento,
        DadosEndereco endereco
) {
}
