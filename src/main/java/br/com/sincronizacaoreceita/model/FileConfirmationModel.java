package br.com.sincronizacaoreceita.model;

import com.opencsv.bean.CsvBindByName;

public class FileConfirmationModel {
    @CsvBindByName(column = "agencia")
    private String agencia;

    @CsvBindByName(column = "conta")
    private String conta;

    @CsvBindByName(column = "saldo")
    private double saldo;

    @CsvBindByName(column = "status")
    private String status;

    @CsvBindByName(column = "status envio")
    private String statusEnvio;

    public FileConfirmationModel insereConfirmacaoDeEnvio(FileModel fileModel, String confirmacao){
        this.agencia = fileModel.getAgencia();
        this.conta = fileModel.getConta();
        this.saldo = fileModel.getSaldo();
        this.status = fileModel.getStatus();
        this.statusEnvio = confirmacao;

        return this;
    }

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

    public String getStatusEnvio() {
        return statusEnvio;
    }

    public void setStatusEnvio(String statusEnvio) {
        this.statusEnvio = statusEnvio;
    }
}
