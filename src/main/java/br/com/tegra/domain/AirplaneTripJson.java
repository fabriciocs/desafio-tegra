package br.com.tegra.domain;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;

public class AirplaneTripJson implements Serializable {

    private static final long serialVersionUID = 1L;
    


    private String voo;


    private String origem;


    private String destino;

    @JsonProperty("data_saida")
    private String dataSaida;


    private String saida;

    private BigDecimal valor;

    private String chegada;

    public String getVoo() {
        return voo;
    }

    public AirplaneTripJson setVoo(String voo) {
        this.voo = voo;
        return this;
    }

    public String getOrigem() {
        return origem;
    }

    public AirplaneTripJson setOrigem(String origem) {
        this.origem = origem;
        return this;
    }

    public String getDestino() {
        return destino;
    }

    public AirplaneTripJson setDestino(String destino) {
        this.destino = destino;
        return this;
    }

    public String getDataSaida() {
        return dataSaida;
    }

    public AirplaneTripJson setDataSaida(String dataSaida) {
        this.dataSaida = dataSaida;
        return this;
    }

    public String getSaida() {
        return saida;
    }

    public AirplaneTripJson setSaida(String saida) {
        this.saida = saida;
        return this;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public AirplaneTripJson setValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public String getChegada() {
        return chegada;
    }

    public AirplaneTripJson setChegada(String chegada) {
        this.chegada = chegada;
        return this;
    }
}
