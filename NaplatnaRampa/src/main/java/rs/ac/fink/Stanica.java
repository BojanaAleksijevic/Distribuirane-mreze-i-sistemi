/*
Da ima jedinstven, automatski generisan celobrojan identifikator i cenovnik
Može da se:
    - postavi cenovnik, 
    - da se napravi kopija stanice (sa anuliranom naplaćenom putarinom), 
    - da se naplati putarina od zadatog vozila, 
    - da se dohvati ukupan naplaćeni iznos za prolaz od prethodnog postavljanja cenovnika 
    - da se sastavi tekstualni opis stanice u obliku idBr(naplaćenIznos). 
Greška je ako je kategorija van dozvoljenog opsega ili ako u trenutku naplate nije definisan cenovnik
*/

package rs.ac.fink;

public class Stanica implements Cloneable{
    private static int stanicaID = 0;
    private int id = ++stanicaID;
    
    private Cenovnik cenovnik;
    private int naplaceniIznos;

    // konstruktor bez argumenata
    public Stanica() {
        this.naplaceniIznos = 0;
    }

    public Cenovnik getCenovnik() {
        return cenovnik;
    }

    
    // konstruktor sa argumentima
    public Stanica(Cenovnik cenovnik) {
        this.cenovnik = cenovnik;
        this.naplaceniIznos = 0; // 0 je pocetno stanje
    }
    

    public void postaviCenovnik(Cenovnik noviCenovnik) {
        if (noviCenovnik == null) {
            throw new IllegalArgumentException("Cenovnik ne može biti null.");
        }
        this.cenovnik = noviCenovnik;
        this.naplaceniIznos = 0;
    }
    
    // metoda clone za kopiju stanice
    @Override
    public Stanica clone() {
        try {
            Stanica s = (Stanica)super.clone();
            // poziv super.clone() pravi plitku kopiju objekta Stanica
            s.id = ++stanicaID;
            s.naplaceniIznos = 0; // sa anuliranom naplaćenom putarinom
            return s;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Kloniranje nije podržano", e);
        }
    }
    
    public void naplatiPutarinu(Kategorizovano vozilo) {
        if (cenovnik == null) {
            throw new IllegalStateException("Cenovnik nije definisan.");
        }
        int kategorija = vozilo.getKategorija();
        int putarina = cenovnik.getPutarine(kategorija);
        naplaceniIznos += putarina;
    }

    public int getNaplaceniIznos() {
        return naplaceniIznos;
    }
    
    @Override
    public String toString() {
        return id + "(" + naplaceniIznos + ")";
    }
}
    
    

