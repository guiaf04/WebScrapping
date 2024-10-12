package aczg

import groovyx.net.http.HttpBuilder
import groovyx.net.http.HttpException
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import static groovyx.net.http.HttpBuilder.configure

class Scraper {
    private String baseUrl
    private String currentPath
    private HttpBuilder client
    private String completePath

    String getCompletePath() {
        return completePath
    }

    Scraper(String baseUrl) {
        this.baseUrl = baseUrl
        this.currentPath = ''
        this.client = configure {
            request.uri = baseUrl
            request.contentType = 'text/html'
            request.headers['User-Agent'] = 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36'
        }
    }

    void updatePath(String path) {
        this.currentPath += path
        this.completePath = baseUrl + currentPath
    }

    void resetPath() {
        this.currentPath = ''
    }

    Document getHtml() {
        try {
            Document response = client.get {
                request.uri.path = currentPath
                response.success { fromServer, body ->
                    println "Acesso bem-sucedido: ${baseUrl}${currentPath}"
                    return Jsoup.parse(body.toString()) // Retorna o HTML como um objeto Document
                }
                response.failure { fromServer, body ->
                    println "Falha ao acessar a página: Status ${fromServer.statusCode}"
                    return null
                }
            } as Document
            return response
        } catch (HttpException e) {
            println "Erro ao acessar a página: ${e.message} (caminho: ${currentPath})"
            return null
        }
    }
}
