package br.com.sincronizacaoreceita.service;

import br.com.sincronizacaoreceita.model.FileConfirmationModel;
import br.com.sincronizacaoreceita.model.FileModel;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EnvioService {


    public List<FileModel> lerArquivo(String caminhoArquivo) {
        List<FileModel> filesModels = new ArrayList<>();
        try (
                Reader reader = Files.newBufferedReader(Paths.get(caminhoArquivo));
        ) {
            CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                    .withType(FileModel.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withSeparator(';')
                    .build();

            Iterator<FileModel> csvUserIterator = csvToBean.iterator();

            while (csvUserIterator.hasNext()) {
                filesModels.add(csvUserIterator.next());
            }
        } catch (IOException e) {

        }
        filesModels.forEach(dados -> dados.setConta(dados.getConta().replace("-","")));

        return filesModels;
    }

    public void escreverArquivo(List<FileModel> filesModels, String respostaReceita, String caminhoArquivo) {
        List<FileConfirmationModel> filesConfirmationModels = new ArrayList<>();

        filesModels.forEach(file ->filesConfirmationModels.add(new FileConfirmationModel().insereConfirmacaoDeEnvio(file, respostaReceita)));

        try (
                Writer writer = Files.newBufferedWriter(Paths.get(caminhoArquivo));
        ) {
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withSeparator(';')
                    .build();

            beanToCsv.write(filesConfirmationModels);

        }catch (RuntimeException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IOException e){
            System.out.println("Erro na escrita do arquivo: "+e.getMessage());
        }
    }

    public void enviaDadosParaReceita(List<FileModel> fileModels, String caminhoArquivo){
        ReceitaService receitaService = new ReceitaService();
        fileModels.forEach(info -> {
            try {
                if(receitaService.atualizarConta(info.getAgencia(), info.getConta(), info.getSaldo(), info.getStatus())){
                    escreverArquivo(fileModels,"OK", caminhoArquivo);
                }else{
                    escreverArquivo(fileModels,"NOK", caminhoArquivo);
                };
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
