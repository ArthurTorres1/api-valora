package com.valora.api.DTOs;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record CadastroUsuarioDTO(
        @NotBlank(message = "O nome é obrigatório.")
        String nome,

        @Email
        @NotBlank(message = "O campo E-MAIL é obrigatório.")
        String email,

        @NotBlank(message = "O campo SENHA é obrigatório.")
        @Size(min = 8, max = 20, message = "A senha deve ter entre 8 e 20 caracteres.")
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
                message = "A senha deve conter pelo menos uma letra maiúscula, uma minúscula, um número e um caractere especial."
        )
        String senha,

        @CPF(message = "O CPF informado é inválido.")
        String cpf,

        @Past(message = "A dat de nascimento deve ser uma data passada.")
        LocalDate DataNascimento,

        @NotNull
        @Valid
        DadosEndereco endereco
) {
}
