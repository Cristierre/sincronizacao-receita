package br.com.sincronizacaoreceita;

import br.com.sincronizacaoreceita.model.FileConfirmationModel;
import br.com.sincronizacaoreceita.model.FileModel;
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

		List<FileModel> fileModels = lerArquivo();

		enviaDadosParaReceita(fileModels);

	}
	public static List<FileModel> lerArquivo() {
		List<FileModel> filesModels = new ArrayList<>();
		try (
				Reader reader = Files.newBufferedReader(Paths.get("C:\\Users\\CristierreGomesKonra\\Documents\\Pessoal\\sincronizacao-receita\\src\\main\\java\\br\\com\\sincronizacaoreceita\\exemplo.csv"));
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

	public static void escreverArquivo(List<FileModel> filesModels, String respostaReceita) {
		List<FileConfirmationModel> filesConfirmationModels = new ArrayList<>();

		filesModels.forEach(file -> {
			filesConfirmationModels.add(new FileConfirmationModel().insereConfirmacaoDeEnvio(file, respostaReceita));
		});
		try (
				Writer writer = Files.newBufferedWriter(Paths.get("C:\\Users\\CristierreGomesKonra\\Documents\\Pessoal\\sincronizacao-receita\\src\\main\\java\\br\\com\\sincronizacaoreceita\\exemploConfirmacao.csv"));
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

	public static void enviaDadosParaReceita(List<FileModel> fileModels){
		ReceitaService receitaService = new ReceitaService();
		fileModels.forEach(info -> {
			try {
				if(receitaService.atualizarConta(info.getAgencia(), info.getConta(), info.getSaldo(), info.getStatus())){
					escreverArquivo(fileModels,"OK");
				}else{
					escreverArquivo(fileModels,"NOK");
				};
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}

}
