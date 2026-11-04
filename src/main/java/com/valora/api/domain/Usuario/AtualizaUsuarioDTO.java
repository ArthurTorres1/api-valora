package com.valora.api.domain.Usuario;

import com.valora.api.domain.Endereco.dto.DadosEndereco;
import jakarta.validation.constraints.NotNull;

public record AtualizaUsuarioDTO(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco
) {
}
