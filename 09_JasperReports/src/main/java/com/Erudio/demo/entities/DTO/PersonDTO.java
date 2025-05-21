package com.Erudio.demo.entities.DTO;

public class PersonDTO {
    private Long id;
    private String nome;
    private String sobrenome;
    private String endereco;
    private String genero;
    private Boolean ativo;

    public PersonDTO(){};

    public PersonDTO(Long id, String nome, String sobrenome, String endereco, String genero, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.endereco = endereco;
        this.genero = genero;
        this.ativo = ativo;
    }

    public String getNomeCompleto(){
        return (nome != null ? nome : "") +
                (sobrenome != null ? " " + sobrenome: "");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
