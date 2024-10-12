package aczg.crawlers

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class TISSPage {
    def scraper

    Document getPrestadoresPage(){
        // Passo 1: Acessar "Espaço do Prestador de Serviços de Saúde"
        scraper.updatePath('/ans/pt-br/assuntos/prestadores')

        Document prestadorPage = scraper.getHtml()

        if (!prestadorPage) {
            println "Erro ao acessar a página de Espaço do Prestador de Serviços de Saúde"
            return null
        }

        return prestadorPage
    }

    Document getTissPage(Document prestadorPage){
        Element tissLink = prestadorPage.select("a:contains(TISS - Padrão para Troca de Informação de Saúde Suplementar)").first()

        if (!tissLink) {
            println "Link para TISS não encontrado"
            return null
        }

        def tissPageLink = tissLink.attr("href").replace(scraper.getCompletePath(), "")
        scraper.updatePath(tissPageLink)

        Document tissPage = scraper.getHtml()

        if (!tissPage) {
            println "Erro ao acessar a página de TISS"
            return null
        }

        return tissPage
    }
}
