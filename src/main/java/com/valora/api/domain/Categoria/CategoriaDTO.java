package com.valora.api.domain.Categoria;

public record CategoriaDTO(
        Long idCategoria,
        Long idUsuario,
        String nmCategoria
) {}