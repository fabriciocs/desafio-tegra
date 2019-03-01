package br.com.tegra.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

public class AirportJson implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;
    private String aeroporto;
    private String cidade;


    public String getNome() {
        return nome;
    }

    public AirportJson setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getAeroporto() {
        return aeroporto;
    }

    public AirportJson setAeroporto(String aeroporto) {
        this.aeroporto = aeroporto;
        return this;
    }

    public String getCidade() {
        return cidade;
    }

    public AirportJson setCidade(String cidade) {
        this.cidade = cidade;
        return this;
    }
}
