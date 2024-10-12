package aczg

import groovyx.net.http.HttpException
import groovyx.net.http.optional.Download
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

import java.nio.file.Files
import java.nio.file.Paths

import static groovyx.net.http.HttpBuilder.configure

class Task3 {
    Scraper scraper
    TISSPage tissPage
    TISSRelationalTables tissRelationalTable

    Task3(Scraper scraper, tissPage, tissRelationalTable) {
        this.scraper = scraper
        this.tissPage = tissPage
        this.tissRelationalTable = tissRelationalTable
    }

    private static void downloadDocumentTables(Document relationalPage){
        def downloadDirectory = Paths.get('./Downloads/Tabela_de_erros')

        if (!Files.exists(downloadDirectory)) {
            Files.createDirectories(downloadDirectory)
        }

        Element errorTable = relationalPage.select("a:contains(Clique aqui para baixar a tabela de erros)").first()
        String fileUrl = errorTable.attr("href")
        def fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1)

        def client = configure {
            request.uri = fileUrl
        }

        File file = (downloadDirectory.resolve(fileName).toFile())

        try {
            client.get {
                println("Download: ${fileName}")
                Download.toFile(delegate, file)
            }
        } catch (HttpException e) {
            println "Erro ao baixar o arquivo: ${e.message} (URL: ${fileUrl})"
        }
    }

    void execute(){
        scraper.resetPath()
        def documentPage = tissPage.getPrestadoresPage()
        def tissPage = tissPage.getTissPage(documentPage)
        def relationalPage = tissRelationalTable.getRelationalTables(tissPage)

       downloadDocumentTables(relationalPage)
    }
}
