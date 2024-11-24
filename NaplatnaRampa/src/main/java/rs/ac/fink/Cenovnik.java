/*
Cenovnik sadrži niz celobrojnih putarina po kategorijama vozila. 
Stvara se popunjenim nizom putarina. 
Može da se dohvati broj kategorija i da se dohvati putarina za datu kategoriju vozila. 
Greška je ako takva kategorija ne postoji.
*/

package rs.ac.fink;

public class Cenovnik {
    private final int[] putarine;
    //final znači da se referenca na niz ne može promeniti nakon inicijalizacije

    public Cenovnik(int[] putarine) {
        if (putarine == null || putarine.length == 0) {
            throw new IllegalArgumentException("Putarine moraju biti definisane i sadržati barem jednu kategoriju.");
        }
        this.putarine = putarine;
    }
    
    
    
    public int brojKategorija() {
        return putarine.length;
    }

    public int getPutarine(int kategorija) {
        if (kategorija < 0 || kategorija >= putarine.length) {
            throw new IllegalArgumentException("Kategorija " + kategorija + " ne postoji.");
        }
        return putarine[kategorija];
    }  
}
