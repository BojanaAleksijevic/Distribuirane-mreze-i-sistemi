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
        
        
        // prva rampa
        Stanica osnovnaStanica = new Stanica();
        osnovnaStanica.postaviCenovnik(osnovniCenovnik);

        Rampa rampa = new Rampa("Rampa A1", 2.0);
        rampa.inicijalizujStanice(osnovnaStanica, 5);
        
        
        // druga rampa
        Stanica osnovnaStanica2 = new Stanica();
        osnovnaStanica2.postaviCenovnik(osnovniCenovnik);
        
        Rampa rampa2 = new Rampa("Rampa A2", 8.0);
        rampa2.inicijalizujStanice(osnovnaStanica, 5);

        // Otvaramo rampu sa novim cenovnikom (sa putarinama za 4 kategorije vozila)
        Cenovnik noviCenovnik = new Cenovnik(new int[]{150, 250, 350, 450});
        rampa.open(noviCenovnik);
        rampa2.open(noviCenovnik);
        
        System.out.println("Rampa A1 otvorena:");
        System.out.println(rampa);
        
        System.out.println("Rampa  A2 otvorena:");
        System.out.println(rampa2);

        /*
        System.out.println("Simulacija rada rampe...");
        rampa.simulirajRadRampe(10); // Simulacija 10 sekundi
*/
        
        new Thread(() -> rampa.simulirajRadRampe(10)).start();
        new Thread(() -> rampa2.simulirajRadRampe(10)).start();

        // Pauza za simulaciju
        try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        

        // Ispis trenutnog stanja rampe
        System.out.println("Stanje rampe A1 nakon simulacije:");
        System.out.println(rampa);
        
        System.out.println("Stanje rampe A2 nakon simulacije:");
        System.out.println(rampa2);
        
        try {
            Thread.sleep(2000); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Zatvaranje rampe
        rampa.close();
        System.out.println("Rampa A1 zatvorena:");
        System.out.println(rampa);
        
        
        rampa2.close();
        System.out.println("Rampa A2 zatvorena:");
        System.out.println(rampa2);
        
       

        // Uništavanje rampe
        rampa.destroy();
        System.out.println("Rampa unistena:");
        System.out.println(rampa);
       
        rampa2.destroy();
        System.out.println("Rampa A2 unistena:");
        System.out.println(rampa2); 
    }
}
