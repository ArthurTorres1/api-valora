package com.valora.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(
        @NotBlank(message = "LOGRADOURO é obrigatório")
        String logradouro,

        @NotBlank(message = "BAIRRO é obrigatório")
        String bairro,

        @NotBlank(message = "O CEP é obrigatório.")
        @Pattern(regexp = "^\\d{5}-\\d{3}$|^\\d{8}$", message = "CEP inválido. Use 00000-000 ou 8 números.")
        String cep,

        @NotBlank(message = "CIDADE é obrigatório")
        String cidade,

        @NotBlank(message = "UF é obrigatório")
        String uf,

        String numero,
        String complemento
) {}