package br.com.sincronizacaoreceita.service;

import br.com.sincronizacaoreceita.model.FileModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

public class EnvioServiceTest {

    private final String PATH_ARQUIVO_LEITURA = "src/test/java/br/com/sincronizacaoreceita/service/fileTest/leituraTeste.csv";
    EnvioService envioService;

    @BeforeEach
    public void instanciaEnvioService (){
        envioService = new EnvioService();
    }

    @Test
    public void deveRetornarListaDeArquivos(){

        List<FileModel> fileModels = envioService.lerArquivo(PATH_ARQUIVO_LEITURA);

        Assertions.assertTrue(fileModels.size() > 0);
    }

    @Test
    public void deveRetornarListaDeArquivosComNumContaSemHifen(){

        List<FileModel> fileModels = envioService.lerArquivo(PATH_ARQUIVO_LEITURA);
        fileModels.forEach(conta ->{
            Assertions.assertFalse(conta.getConta().contains("-"));
        });
    }

    @Test
    public void deveRetornarArrayVazioQuandoNaoEncontraArquivo(){

        List<FileModel> fileModels = envioService.lerArquivo("arquivoInexistente.csv");

        Assertions.assertTrue(fileModels.isEmpty());
    }

    @ParameterizedTest
    @CsvSource(value = {"0101;122256;100.00;A",
                "0101;122268;3200.50;A",
                "3202;400111;-35.12;I",
                "3202;540012;0.00;P",
                "3202;003212;34500.00;B"}, delimiter = ';')
    public void deveEscreverDadosDaContaNoArquivo(String agencia, String conta, double saldo, String status){
        List<FileModel> fileModels = new ArrayList<>();
        final String CAMINHO_ARQUIVO_TESTE = "src/test/java/br/com/sincronizacaoreceita/service/fileTest/escritaTeste.csv";

        fileModels.add(new FileModel(agencia,conta,saldo,status));


        envioService.escreverArquivo(fileModels,"OK", CAMINHO_ARQUIVO_TESTE);

        List<FileModel> fileModelsExpected = envioService.lerArquivo(CAMINHO_ARQUIVO_TESTE);

        Assertions.assertEquals(1, fileModelsExpected.size());
        Assertions.assertEquals(new FileModel(agencia, conta, saldo, status), fileModelsExpected.get(0));
    }
}
