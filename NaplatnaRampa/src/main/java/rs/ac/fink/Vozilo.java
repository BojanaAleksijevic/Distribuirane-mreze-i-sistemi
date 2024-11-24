package rs.ac.fink;

public class Vozilo implements Kategorizovano {
    private int kategorija;

    public Vozilo(int kategorija) {
        if (kategorija < 0) {
            throw new IllegalArgumentException("Kategorija mora biti nenegativan broj.");
        }
        this.kategorija = kategorija;
    }
    
    
    @Override
    public int getKategorija() {
        return kategorija;
    }  
}
