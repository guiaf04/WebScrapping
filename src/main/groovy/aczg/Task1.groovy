package aczg

import groovyx.net.http.HttpException
import groovyx.net.http.optional.Download
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

import java.nio.file.Files
import java.nio.file.Paths

import static groovyx.net.http.HttpBuilder.configure

class Task1 {
    Scraper scraper
    TISSPage tissPage
    TISSCurrentPattern tissCurrent

    Task1(Scraper scraper, tissPage, tissCurrent) {
        this.scraper = scraper
        this.tissPage = tissPage
        this.tissCurrent = tissCurrent
    }

    private static void downloadDocumentTables(Elements rows){
        def downloadDirectory = Paths.get('./Downloads/Arquivos_padrao_TISS')

        if (!Files.exists(downloadDirectory)) {
            Files.createDirectories(downloadDirectory)
        }

        rows.each { row ->
            Elements columns = row.select("td")

            Element downloadLink = columns[2].select("a").first()
            def fileUrl = downloadLink.attr("href")
            def fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1)

            def client = configure {
                request.uri = fileUrl // URL do arquivo diretamente
                request.headers['User-Agent'] = 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36'
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
    }

    void execute(){
        def documentPage = tissPage.getPrestadoresPage()
        def tissPage = tissPage.getTissPage(documentPage)
        def currentTISSPage = tissCurrent.getCurrentTISS(tissPage)
        def documentTables = tissCurrent.getDocumentTables(currentTISSPage)

        downloadDocumentTables(documentTables)
    }
}
