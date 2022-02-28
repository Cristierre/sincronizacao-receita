package br.com.sincronizacaoreceita.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
