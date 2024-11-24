package rs.ac.fink.proizvodjacpotrosac;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
        Izvestac izvestac1 = new Izvestac(skladiste, 10000); // Interval 10 sekundi
        Izvestac izvestac2 = new Izvestac(skladiste, 10000);
        Izvestac izvestac3 = new Izvestac(skladiste, 10000);

        niti.add(izvestac1);
        niti.add(izvestac2);
        niti.add(izvestac3);

        // Učitavanje rasporeda iz fajla
        File rasporedFile = new File("src/main/java/rs/ac/fink/proizvodjacpotrosac/raspored.txt");

        if (rasporedFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(rasporedFile))) {
                System.out.println("Raspored:");
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line); // Ispis rasporeda
                }
            } catch (IOException e) {
                System.err.println("Greška prilikom čitanja fajla: " + e.getMessage());
            }
        } else {
            System.err.println("Fajl 'raspored.txt' nije pronađen u trenutnom direktorijumu.");
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
