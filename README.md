# TISS Contracts Scraper

Este projeto, feito por Guilherme Araújo Floriano, realiza o scraping de dados de contratos TISS do portal gov.br, utilizando as bibliotecas **HttpBuilder** para realizar requisições HTTP e **JSoup** para processar e extrair dados HTML. O projeto é gerenciado e construído com **Gradle**, e depende de versões específicas de bibliotecas para garantir o correto funcionamento.

## Tecnologias Utilizadas
- **Groovy**: Linguagem utilizada para o desenvolvimento.
- **HttpBuilder**: Para realizar as requisições HTTP.
- **JSoup**: Para parsing do HTML e extração dos dados.
- **Gradle**: Para gerenciamento de dependências e construção do projeto.

## Funcionalidades
- Extrai dados de contratos de saúde suplementar do portal gov.br.
- Filtra contratos a partir de dezembro de 2015.
- Coleta e exibe os três primeiros campos de cada linha da tabela presente no site.

## Requisitos

Certifique-se de ter os seguintes itens instalados e configurados em sua máquina:

1. **Java Development Kit (JDK)**: Versão 8 ou superior.
2. **Gradle**: Para construir e executar o projeto. Você pode instalar o Gradle [aqui](https://gradle.org/install/).

## Configuração do Projeto

As dependências do projeto são gerenciadas pelo Gradle. No arquivo `build.gradle`, as versões das bibliotecas foram especificadas para garantir compatibilidade:

```gradle
plugins {
    id 'groovy'
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.codehaus.groovy:groovy-all:3.0.22'
    implementation 'io.github.http-builder-ng:http-builder-ng-core:1.0.4'
    implementation 'org.slf4j:slf4j-api:1.7.32'
    implementation 'org.jsoup:jsoup:1.17.2'
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.7.2'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.7.2'
}
```

### Dependências
- **Groovy**: `3.0.22`
- **JSoup**: `1.17.2`
- **HttpBuilder-NG**: `1.0.4`

Certifique-se de que essas versões estão sendo utilizadas no seu projeto para evitar problemas de compatibilidade.

## Instruções de Execução

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/seu-usuario/tiss-contracts-scraper.git
   cd tiss-contracts-scraper
   ```

2. **Execute o projeto com Gradle**:

   Certifique-se de estar na raiz do projeto e execute o seguinte comando para rodar o scraper:
   ```bash
   gradle run
   ```

3. **Saída esperada**:

   O projeto coletará os dados do site específico e filtrará os contratos a partir de dezembro de 2015, retornando apenas os três primeiros campos de cada linha de contrato.

## Observações

- O site alvo do scraping está sujeito a mudanças, e o código pode precisar de ajustes para continuar funcionando corretamente se houver alterações no layout ou na estrutura da página HTML.
- Certifique-se de seguir as políticas de uso de dados e respeitar os limites de scraping estabelecidos no site alvo.
