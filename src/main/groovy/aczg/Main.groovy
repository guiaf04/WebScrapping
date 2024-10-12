package aczg

import aczg.crawlers.TISSCurrentPattern
import aczg.crawlers.TISSOlderPattern
import aczg.crawlers.TISSPage
import aczg.crawlers.TISSRelationalTables
import aczg.tasks.Task1
import aczg.tasks.Task2
import aczg.tasks.Task3

class Main {
    static void main(String[] args) {
        def scraper = new Scraper('https://www.gov.br')
        def tissPage = new TISSPage(scraper: scraper)
        def tissCurrent = new TISSCurrentPattern(scraper)
        def tissOlder = new TISSOlderPattern(scraper: scraper)
        def tissTable = new TISSRelationalTables(scraper)

        Task1 task1 = new Task1(scraper, tissPage, tissCurrent)
        Task2 task2 = new Task2(scraper, tissPage, tissOlder)
        Task3 task3 = new Task3(scraper, tissPage, tissTable)

        task1.execute()
        task2.execute()
        task3.execute()

    }

}


