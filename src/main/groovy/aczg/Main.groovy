package aczg

import groovyx.net.http.optional.Download

import static groovyx.net.http.HttpBuilder.configure
import org.jsoup.nodes.Document


class Main{

    static void task1(){
        File teste = new File("../../Downloads/padrao_vigente")

        File file = configure {
            request.uri = "https://www.gov.br/ans/pt-br/assuntos/prestadores/" +
            "padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss/" + '/PadroTISSComunicao202301.zip'
        }.get {
            Download.toFile(delegate, teste)
        } as File
    }

    static void task2(){
        Document page = configure {
            request.uri = "https://www.gov.br/ans/pt-br/assuntos/prestadores/" +
                    "padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss/" +
                    'padrao-tiss-historico-das-versoes-dos-componentes-do-padrao-tiss'
        }.get() as Document

        // Seleciona todas as linhas (tr) dentro de tbody
        def itens = page.select('tbody tr')
                .findAll{row ->
                    row.firstElementChild().text().find(/[0-9]{4}/).toInteger() > 2015}
                .collect { row ->
                    row.select('td').take(3).collect { it.text() }
                }

        String item = itens.join("\n").replaceAll(/[\[|\]]/, "")
        println(item)

        File saved = new File("../../Downloads/historico_componentes_TISS.csv")
        saved.createNewFile()

        if (saved.getText() == ""){
            saved.text = "Competência, Publicação, Início de Vigência\n"
            saved.append(item)
        }
    }

    static void task3(){
        File file = configure {
            request.uri = "https://www.gov.br/ans/pt-br/arquivos/assuntos/prestadores/" +
                    "padrao-para-troca-de-informacao-de-saude-suplementar-tiss/padrao-tiss-tabelas-relacionadas/" +
                    "Tabelaerrosenvioparaanspadraotiss__1_.xlsx"
        }.get {
            Download.toFile(delegate, new File("../../Downloads/tabelas_relacionadas.xlsx"))
        } as File
    }

    static void main(String[] args) {
        task1()
        task2()
        task3()
    }

}


