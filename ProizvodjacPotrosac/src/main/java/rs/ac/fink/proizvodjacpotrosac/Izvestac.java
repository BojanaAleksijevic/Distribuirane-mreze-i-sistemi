package rs.ac.fink.proizvodjacpotrosac;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Izvestac extends Thread {
    private static int statId = 0;
    private final int id = ++statId; // Svakom izveštaču se dodeljuje jedinstveni ID
    private final Skladiste skladiste;
    private final String fileRaspored;

    public Izvestac(Skladiste skladiste, String fileRaspored) {
        this.skladiste = skladiste;
        this.fileRaspored = fileRaspored;
    }

    @Override
    public void run() {
        System.out.println("Izvestac " + id + " je zapoceo rad.");
        try (BufferedReader reader = new BufferedReader(new FileReader(fileRaspored))) {
            String red;
            while ((red = reader.readLine()) != null) {
                String[] delovi = red.split("\\s+");

                // Proveravamo da li je trenutni red za ovog izveštača
                if (delovi.length == 2 && delovi[1].equals("Izvestac" + id)) {
                    synchronized (skladiste) {
                        // Sinhronizacija pristupa skladištu
                        System.out.println("Izvestac " + id + " - Izvestaj za skladiste:");
                        System.out.println("Trenutno stanje skladista: " + skladiste.getStanje() + "/" + skladiste.getKapacitet());

                        int[] trenutniNiz = skladiste.getNiz(); // Dobijanje kopije niza
                        System.out.print("Sadrzaj: [");
                        for (int i = 0; i < trenutniNiz.length; i++) {
                            System.out.print(trenutniNiz[i]);
                            if (i < trenutniNiz.length - 1) {
                                System.out.print(", ");
                            }
                        }
                        System.out.println("]");
                    }
                }

                // Pauza između provera kako bi se izveštaji smestili u odgovarajuće intervale
                Thread.sleep(1000); // Pauza između provera
            }
        } catch (IOException e) {
            System.err.println("Greška prilikom čitanja fajla '" + fileRaspored + "': " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Izvestac " + id + " je prekinut.");
        }
        System.out.println("Izvestac " + id + " je završio rad.");
    }
}
