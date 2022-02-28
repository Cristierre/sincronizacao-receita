package br.com.sincronizacaoreceita.service;

import br.com.sincronizacaoreceita.model.FileModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class EnvioServiceTest {

    EnvioService envioService;

    @Test
    public void deveRetornarListaDeArquivos(){
        envioService = new EnvioService();

        List<FileModel> fileModels = envioService.lerArquivo("informacaoContas.csv");

        Assertions.assertTrue(fileModels.size() > 0);
    }

    @Test
    public void deveRetornarListaDeArquivosComNumContaSemHifen(){
        envioService = new EnvioService();

        List<FileModel> fileModels = envioService.lerArquivo("informacaoContas.csv");
        fileModels.forEach(conta ->{
            Assertions.assertFalse(conta.getConta().contains("-"));
        });
    }

    @Test
    public void deveRetornarArrayVazioQuandoNaoEncontraArquivo(){
        envioService = new EnvioService();

        List<FileModel> fileModels = envioService.lerArquivo("arquivoTeste.csv");

        Assertions.assertTrue(fileModels.isEmpty());
    }
}
