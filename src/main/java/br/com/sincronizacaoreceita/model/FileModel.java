package br.com.sincronizacaoreceita.model;

import com.opencsv.bean.CsvBindByName;

public class FileModel {

    @CsvBindByName(column = "agencia")
    private String agencia;

    @CsvBindByName(column = "conta")
    private String conta;

    @CsvBindByName(column = "saldo")
    private double saldo;

    @CsvBindByName(column = "status")
    private String status;


    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
