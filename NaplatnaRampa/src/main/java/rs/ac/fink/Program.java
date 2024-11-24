/*
Program napravi jednu naplatnu rampu koju otvori, posle
izvesnog vremena ispiše na glavnom izlazu, posle još nešto vremena zatvori, 
ispiše na glavnom izlazu i uništi. 
Koristiti konstantne parametre (ne treba ništa učitavati s glavnog ulaza)
*/

package rs.ac.fink;

import java.util.Arrays;

public class Program {
    public static void main(String[] args) throws InterruptedException {
        // Definišemo osnovnu stanicu sa početnim cenovnikom
        int[] putarine = {100, 200, 300, 400}; // Primer putarina za 4 kategorije
        Cenovnik osnovniCenovnik = new Cenovnik(putarine);
        Stanica osnovnaStanica = new Stanica();
        osnovnaStanica.postaviCenovnik(osnovniCenovnik);

        // Kreiramo naplatnu rampu
        Rampa rampa = new Rampa("Rampa A1", 5, 2.0); // naziv, broj stanica, tsr (sekunde)
        rampa.inicijalizujStanice(osnovnaStanica, 5);

        // Otvaramo rampu sa novim cenovnikom (sa putarinama za 4 kategorije vozila)
        Cenovnik noviCenovnik = new Cenovnik(new int[]{150, 250, 350, 450});
        rampa.open(noviCenovnik);
        System.out.println("Rampa otvorena:");
        System.out.println(rampa);

        // Simulacija dolaska vozila tokom 5 dolazaka
        Thread simulacija = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                rampa.simulirajDolazakVozila(); // Simuliramo dolazak vozila
                System.out.println("Stanje rampe nakon dolaska vozila:");
                System.out.println(rampa);

                try {
                    Thread.sleep(1000); // Pauza između ispisivanja stanja (2 sekunde)
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        simulacija.start();
        simulacija.join(); // osigurava da se glavni tok programa zaustavi dok se simulacija ne završi

        try {
            Thread.sleep(2000);
          //  simulacija.join(); // Sačekaj završetak simulacije
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        
        try {
            Thread.sleep(2000); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        
        // Ispis trenutnog stanja rampe
        System.out.println("Stanje rampe nakon simulacije:");
        System.out.println(rampa);

        // Zatvaranje rampe
        rampa.close();
        System.out.println("Rampa zatvorena:");
        System.out.println(rampa);

        // Uništavanje rampe
        rampa.destroy();
        System.out.println("Rampa unistena:");
        System.out.println(rampa);
    }
}
