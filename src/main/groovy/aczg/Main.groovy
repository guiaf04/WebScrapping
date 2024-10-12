package aczg

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


