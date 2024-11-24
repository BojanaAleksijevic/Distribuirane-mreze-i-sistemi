/*
- Aktivna naplatna rampa sadrži naziv, zadat broj stanica i zadato srednje vreme (tsr)
između dva dolaska vozila
- Pri stvaranju se zadaje stanica čijim kopiranjem se stvaraju stanice rampe. 
- Rampa može da se otvori, zatvori i da se uništi. 
- Može da se odredi ukupan naplaćen iznos od poslednjeg otvaranja rampe i da se sastavi
tekstualni opis rampe u obliku naziv(naplaćeno):stanica,…,stanica. 
- Pri otvaranju se zadaje novi cenovnik koji se prosleđuje svim stanicama. 
- Kad je rampa otvorena, vozila slučajnih kategorija, 
koje su u dozvoljenom intervalu prema važećem cenovniku, 
pristižu u slučajnim vermenskim intervalima od (1±0,3)tsr, na slučajnu stanicu rampe
*/

package rs.ac.fink;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Rampa {
    private static int rampaID = 0;
    private int id = ++rampaID;
    
    private String nazivRampe;
    private  List<Stanica> stanice;
    //private int brStanica;
    private double tsr; // srednje vreme
    private boolean otvorena;
    
    //private Stanica stanica;
    private int naplaceniIznos;
    private Random random = new Random();

    // konstruktor
    public Rampa(String nazivRampe, int brStanica, double tsr) {
        this.nazivRampe = nazivRampe;
        this.stanice = new ArrayList<>();
        this.tsr = tsr;
        this.otvorena = false;
        this.naplaceniIznos = 0;
    }

    // Metoda inicijalizujStanice koristi clone() metodu klase Stanica da stvori kopije osnovne stanice
    public void inicijalizujStanice(Stanica osnovnaStanica, int brojStanica) {
        if (osnovnaStanica == null || brojStanica <= 0) {
            throw new IllegalArgumentException("Nevalidna osnova stanica ili broj stanica.");
        }

        stanice.clear(); // Brisanje prethodnih stanica, ako ih ima
         // Dodaj osnovnu stanicu kao prvu
        stanice.add(osnovnaStanica);

        // Dodaj klonove osnovne stanice za preostale
        for (int i = 1; i < brojStanica; i++) {
            stanice.add(osnovnaStanica.clone());
        }        
    }

    
    public void open(Cenovnik cenovnik) {
        if (otvorena) {
            throw new IllegalAccessError("Rampa je vec otvorena.");
        }
        if (cenovnik == null) {
            throw new IllegalAccessError("Cenovnik ne moze biti null.");
        }
        
        // postavljanje cenovnika za sve stanice
        for (Stanica stanica : stanice) {
            stanica.postaviCenovnik(cenovnik);
        }
        
        /*
        for (int i = 0; i < stanice.size(); i++) {
            Stanica stanica = stanice.get(i);
            stanica.postaviCenovnik(cenovnik);
        }
        */
        
        naplaceniIznos = 0;
        otvorena = true;
    }
    
    public void close() {
        if (!otvorena) {
            throw new IllegalAccessError("Rampa je vec zatvorena.");
        }
        otvorena = false;
    }
    
    public void destroy() {
        if (otvorena) {
            close();
        }
        stanice.clear();
        nazivRampe = "UNISTENA";
    }
    
    

    public int getNaplaceniIznos() {
        return naplaceniIznos;
    }
    
    public void simulirajDolazakVozila() {
        if (!otvorena) {
            throw new IllegalStateException("Rampa nije otvorena.");
        }
        if (stanice.isEmpty()) {
            throw new IllegalStateException("Rampa nema stanica za simulaciju.");
        }

        int kategorija = random.nextInt(stanice.get(0).getCenovnik().brojKategorija());
        Kategorizovano vozilo = new Vozilo(kategorija);

        int indeksStanice = random.nextInt(stanice.size());
        Stanica stanica = stanice.get(indeksStanice);

        // Iz cenovnika stanice se uzima putarina za kategoriju vozila
        int iznos = stanica.getCenovnik().getPutarine(vozilo.getKategorija());
        naplaceniIznos += iznos;
        stanica.naplatiPutarinu(vozilo);

        try {
            // slučajni vremenski interval u rasponu (1±0.3)*tsr
            double interval = tsr * (1 + (random.nextDouble() - 0.5) * 0.6);
            // Oduzimamo 0.5 da bismo dobili vrednosti u opsegu [-0.5, 0.5]
            // Množenjem sa 0.6 dobijamo interval u opsegu [-0.3, 0.3]
            Thread.sleep((long) (interval * 1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    
    // naziv(naplaćeno):stanica,…,stanica. 
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(nazivRampe + "(" + naplaceniIznos + "):");
        for (int i = 0; i < stanice.size(); i++) {
            sb.append(stanice.get(i).toString());
            if (i < stanice.size() - 1) {
                sb.append(","); // Dodajemo zarez samo između stanica
            }
        }
        return sb.toString();
    }

}
