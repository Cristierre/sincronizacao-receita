package br.com.sincronizacaoreceita;

import br.com.sincronizacaoreceita.model.FileModel;
import br.com.sincronizacaoreceita.service.EnvioService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class SincronizacaoReceitaApplication {

	public static void main(String[] args) {

		SpringApplication.run(SincronizacaoReceitaApplication.class, args);

		EnvioService envioService = new EnvioService();

		List<FileModel> fileModels = envioService.lerArquivo("src/main/java/br/com/sincronizacaoreceita/files/"+ Arrays.stream(args).collect(Collectors.toList()).get(0));

		envioService.enviaDadosParaReceita(fileModels,"src/main/java/br/com/sincronizacaoreceita/files/informacoesProcessadas.csv");

		System.out.println("Operação feita com sucesso");
	}
}
