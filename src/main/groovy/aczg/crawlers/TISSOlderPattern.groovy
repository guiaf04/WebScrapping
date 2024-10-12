package aczg.crawlers

import aczg.Scraper
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class TISSOlderPattern {
    Scraper scraper

    Document getOlderTISS(Document tissPage){
        Element padraoTiss = tissPage.select("a:contains(Clique aqui para acessar todas as versões )").first()
        if (!padraoTiss) {
            println "Link para Histórico de Padrão TISS não encontrado"
            return null
        }

        String olderTissLink = padraoTiss.attr("href").replace(scraper.getCompletePath(), "")
        scraper.updatePath(olderTissLink)

        Document olderTissPage = scraper.getHtml()
        if (!olderTissPage) {
            println "Erro ao acessar a página de Histórico de Padrão TISS"
            return null
        }

        return olderTissPage
    }

    static Elements getVersionTable(Document olderTissPage){
        return olderTissPage.select('tbody tr')
                .findAll { row ->
                    row.firstElementChild().text().find(/[0-9]{4}/).toInteger() > 2015
                }
                .collect { row ->
                    row.select('td').take(3).collect { it.text() }
                }
    }
}
