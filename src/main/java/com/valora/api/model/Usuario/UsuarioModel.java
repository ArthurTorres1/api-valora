package com.valora.api.model.Usuario;

import com.valora.api.dto.CadastroUsuarioDTO;
import com.valora.api.model.Endereco.Endereco;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name="T_VLR_USUARIO")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long id;

    @Column(name = "NM_USUARIO", nullable = false, length = 100)
    private String nome;

    @Column(name = "NR_TELEFONE", nullable = false, length = 20)
    private String telefone;

    @Column(name = "DS_EMAIL", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "DS_SENHA_HASH", nullable = false)
    private String senha;

    @Column(name = "NR_CPF", nullable = false, length = 11, unique = true)
    private String cpf;

    @Column(name = "DT_NASCIMENTO", nullable = false)
    private LocalDate dataNascimento;

    // O JPA pega a classe Endereco e "abre" as colunas dela aqui dentro da tabela do Usuário
    @Embedded
    private Endereco endereco;

    // Como o Oracle já gera a data automaticamente (DEFAULT SYSDATE),
    // dizemos para o Hibernate não tentar inserir ou atualizar este campo.
    @Column(name = "DT_CRIACAO", insertable = false, updatable = false)
    private LocalDateTime dataCriacao;

    public UsuarioModel(CadastroUsuarioDTO cadastroUsuarioDTO) {
        this.nome = cadastroUsuarioDTO.nome();
        this.telefone = cadastroUsuarioDTO.telefone();
        this.email = cadastroUsuarioDTO.email();
        this.senha = cadastroUsuarioDTO.senha();
        this.cpf = cadastroUsuarioDTO.cpf();
        this.dataNascimento = LocalDate.now();
        this.endereco = new Endereco(cadastroUsuarioDTO.endereco());
    }
}
