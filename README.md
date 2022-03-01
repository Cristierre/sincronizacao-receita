# Sincronizador receita

## Requisitos para build
- ter **maven** instalado e configurado;
- Java JDK 11

## Rodando o projeto
O Arquivo que indicar para o processamento deve seguir o seguinte modelo:
```
agencia;conta;saldo;status
0101;12225-6;100,00;A
0101;12226-8;3200,50;A
3202;40011-1;-35,12;I
3202;54001-2;0,00;P
```
Na raiz do projeto abra o terminal e rode os seguintes comandos:

Para compilar rode:
```
mvn compile
```

Após rode o seguinte comando para que o **jar** da aplicação seja gerado:
```
mvn package
```

Feito isso, agora rode o seguinte comando para a execução do projeto, passando por parâmetro ao final da linha 
de comando o nome do arquivo que deseja processar:
```
java -jar target/sincronizacao-receita-0.0.1-SNAPSHOT.jar <input-file>
```
## Diretório de arquivos
O arquivo a ser processado deve ser inserido dentro do pacote:
**src/main/java/br/com/sincronizacaoreceita/files**
O arquivo resultante do processamento será salvo no mesmo diretório com o o nome **informacoesProcessadas**, nele havera uma coluna extra com o resultado do processamento:
- **OK** - indicando retorno positivo;
- **NOK** - indicando retorno negativo;
