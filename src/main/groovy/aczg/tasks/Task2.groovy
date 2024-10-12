package aczg.tasks

import aczg.Scraper
import aczg.crawlers.TISSOlderPattern
import aczg.crawlers.TISSPage
import org.jsoup.select.Elements

import java.nio.file.Files
import java.nio.file.Paths

class Task2 {
    Scraper scraper
    TISSPage tissPage
    TISSOlderPattern tissOlder

    Task2(Scraper scraper, tissPage, tissOlder) {
        this.scraper = scraper
        this.tissPage = tissPage
        this.tissOlder = tissOlder
    }

    private static void downloadOlderItens(Elements itens){
        String item = itens.join("\n").replaceAll(/[\[|\]]/, "")

        def downloadDirectory = Paths.get('./Downloads/Historico_padrao_TISS')

        if (!Files.exists(downloadDirectory)) {
            Files.createDirectories(downloadDirectory)
        }

        File file = (downloadDirectory.resolve("historico_componentes_TISS.csv").toFile())

        if (file != null){
            println("Download historic components of TISS")
            file.text = "Competência, Publicação, Início de Vigência\n"
            file.append(item)
        }
    }

    void execute(){
        scraper.resetPath()
        def documentPage = tissPage.getPrestadoresPage()
        def tissPage = tissPage.getTissPage(documentPage)
        def olderPage = tissOlder.getOlderTISS(tissPage)
        def itens = tissOlder.getVersionTable(olderPage)

        downloadOlderItens(itens)
    }
}
