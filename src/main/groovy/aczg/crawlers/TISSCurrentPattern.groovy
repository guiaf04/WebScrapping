package aczg.crawlers

import aczg.Scraper
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class TISSCurrentPattern {
    Scraper scraper

    TISSCurrentPattern(Scraper scraper) {
        this.scraper = scraper
    }

    Document getCurrentTISS(Document tissPage){
        Element padraoTiss = tissPage.select("a:contains(Clique aqui para acessar a versão )").first()
        if (!padraoTiss) {
            println "Link para Padrão TISS não encontrado"
            return null
        }

        String padraoTissLink = padraoTiss.attr("href").replace(scraper.getCompletePath(), "")
        scraper.updatePath(padraoTissLink)

        Document padraoTissPage = scraper.getHtml()
        if (!padraoTissPage) {
            println "Erro ao acessar a página de Padrão TISS"
            return null
        }

        return padraoTissPage
    }

    static Elements getDocumentTables(Document padraoTissPage){
        // Passo 4: Realizar o parser da tabela de documentos
        Elements rows = padraoTissPage.select("table tbody tr")

        if (rows.isEmpty()) {
            println "Tabela de documentos não encontrada"
            return null
        }

        return rows
    }
}
