package br.com.sincronizacaoreceita;

import br.com.sincronizacaoreceita.model.FileConfirmationModel;
import br.com.sincronizacaoreceita.model.FileModel;
import br.com.sincronizacaoreceita.service.EnvioService;
import br.com.sincronizacaoreceita.service.ReceitaService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SpringBootApplication
public class SincronizacaoReceitaApplication {

	public static void main(String[] args) {

		SpringApplication.run(SincronizacaoReceitaApplication.class, args);

		EnvioService envioService = new EnvioService();

		List<FileModel> fileModels = envioService.lerArquivo("src/main/java/br/com/sincronizacaoreceita/files/");

		envioService.enviaDadosParaReceita(fileModels,"src/main/java/br/com/sincronizacaoreceita/files/informacoesProcessadas.csv");

	}
}
