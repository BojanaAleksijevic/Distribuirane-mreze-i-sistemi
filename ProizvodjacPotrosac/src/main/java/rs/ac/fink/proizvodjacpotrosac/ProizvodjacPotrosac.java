package rs.ac.fink.proizvodjacpotrosac;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProizvodjacPotrosac {

    public static void main(String[] args) {
        // Skladište kapaciteta 10
        Skladiste skladiste = new Skladiste(10);

        // Lista za čuvanje svih niti
        List<Thread> niti = new ArrayList<>();

        // Kreiranje 20 proizvođača
        for (int i = 0; i < 20; i++) {
            Proizvodjac proizvodjac = new Proizvodjac(skladiste, 500, 1000); // min i max u ms
            niti.add(proizvodjac);
        }

        // Kreiranje 30 potrošača
        for (int i = 0; i < 30; i++) {
            Potrosac potrosac = new Potrosac(skladiste, 800, 1500);
            niti.add(potrosac);
        }

        // Kreiranje 3 izveštača
        for (int i = 1; i <= 3; i++) {
            Izvestac izvestac = new Izvestac(skladiste, "./raspored.txt");
            niti.add(izvestac);
        }

        // Pokretanje svih niti
        for (Thread nit : niti) {
            nit.start();
        }

        // Omogućavamo prekid simulacije pritiskom na Enter
        System.out.println("Pritisnite Enter za prekid simulacije...");
        try {
            System.in.read(); // Čeka unos korisnika
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Prekidanje svih niti
        for (Thread nit : niti) {
            nit.interrupt();
        }

        // Čekanje da se sve niti završe
        for (Thread nit : niti) {
            try {
                nit.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Simulacija je prekinuta.");
    }
}
