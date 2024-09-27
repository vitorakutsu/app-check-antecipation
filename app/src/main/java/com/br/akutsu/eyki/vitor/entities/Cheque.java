package com.br.akutsu.eyki.vitor.entities;

public class Cheque {

    private double valor;
    private int dias;
    private double juros;

    public Cheque() {
        this.valor = 0;
        this.dias = 0;
        this.juros = 0;
    }

    public Cheque(double valor, int dias, double juros) {
        this();
        this.valor = valor;
        this.dias = dias;
        this.juros = juros;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public double getJuros() {
        return juros;
    }

    public void setJuros(double juros) {
        this.juros = juros;
    }
}
