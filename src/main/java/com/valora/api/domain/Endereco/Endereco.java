package com.valora.api.domain.Endereco;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @Column(name = "DS_LOGRADOURO", length = 150)
    private String logradouro;

    @Column(name = "DS_BAIRRO", length = 100)
    private String bairro;

    @Column(name = "DS_CEP", length = 10)
    private String cep;

    @Column(name = "DS_CIDADE", length = 100)
    private String cidade;

    @Column(name = "DS_UF")
    private String uf;

    @Column(name = "DS_COMPLEMENTO", length = 100)
    private String complemento;

    @Column(name = "NR_ENDERECO", length = 20)
    private String numero;

    public Endereco(DadosEndereco dadosEndereco) {
        this.logradouro = dadosEndereco.logradouro();
        this.bairro = dadosEndereco.bairro();
        this.cep = dadosEndereco.cep();
        this.cidade = dadosEndereco.cidade();
        this.uf = dadosEndereco.uf();
        this.complemento = dadosEndereco.complemento();
        this.numero = dadosEndereco.numero();
    }

    public void atualizarInformacoes(DadosEndereco novoEndereco) {
        if(novoEndereco.logradouro() != null)
            this.logradouro = novoEndereco.logradouro();
        if(novoEndereco.bairro() != null)
            this.bairro = novoEndereco.bairro();
        if(novoEndereco.cep() != null)
            this.cep = novoEndereco.cep();
        if(novoEndereco.cidade() != null)
            this.cidade = novoEndereco.cidade();
        if(novoEndereco.uf() != null)
            this.uf = novoEndereco.uf();
        if(novoEndereco.complemento() != null)
            this.complemento = novoEndereco.complemento();
        if(novoEndereco.numero() != null)
            this.numero = novoEndereco.numero();

    }
}