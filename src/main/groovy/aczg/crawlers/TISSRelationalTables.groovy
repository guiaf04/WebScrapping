package aczg.crawlers

import aczg.Scraper
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class TISSRelationalTables {
    Scraper scraper

    TISSRelationalTables(Scraper scraper) {
        this.scraper = scraper
    }

    Document getRelationalTables(Document tissPage){
        Element tissTable = tissPage.select("a:contains(Clique aqui para acessar as planilhas)").first()

        if (!tissTable) {
            println "Link para Tabelas Relacionadas não encontrado"
            return null
        }

        String tissTableLink = tissTable.attr("href").replace(scraper.getCompletePath(), "")
        scraper.updatePath(tissTableLink)

        Document tissTablePage = scraper.getHtml()
        if (!tissTablePage) {
            println "Erro ao acessar a página de Tabelas Relacionadas TISS"
            return null
        }

        return tissTablePage
    }
}
