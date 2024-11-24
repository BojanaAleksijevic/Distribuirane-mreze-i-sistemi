package rs.ac.fink.proizvodjacpotrosac;
/*
import java.util.logging.Level;
import java.util.logging.Logger;
*/
public class Proizvodjac extends Thread {
    private static int statId = 0; // da se zna id za svakog
    private int id=++statId; // id se inkrementira preinkrementom
    
    private Skladiste skladiste;
    private int brojac = 0;
    private int minTime; // minimalno vreme proizvodnje
    private int maxTime;
    
    private int trajanje = minTime + (int)(Math.random()*(maxTime-minTime));

    public Proizvodjac(Skladiste skladiste, int minTime, int maxTime) {
        this.skladiste = skladiste;
        this.minTime = minTime;
        this.maxTime = maxTime;
    }
    
    // u run metodi proizvodjac proizvodi
    public void run(){
        System.out.println("Proizvodjac "+id+" je krenuo sa proizvodnjom");
        try{
            // metoda interrupted vrati da li je nit prekinuta
            while(!interrupted()){
                // sada se simulira broj koliko traje proizvodnja
                // Math.random vraca broj izmedju 0 i 1
                // dobija se broj koji je u intervalu maxTime-minTimd
           
                int trajanje = minTime + (int)Math.random()*(maxTime-minTime);
                sleep(trajanje); // moramo da uspavamo nit za to vreme
                int proizvod = id*1000 + brojac++;
                // id prvog proizvoda proizvodjaca 1 ce biti 1000
                // znaci, za proizvodjaca 1: 1000, 1001, 1002
                // za proizvodjaca 2: 2000, 2001, 2002
                skladiste.Stavi(proizvod);
            
                System.out.println("Proizveden je proizvod" + proizvod + " i dodat u skladiste");
            }
        } catch (InterruptedException ex) {
            System.out.println("Prozivodjac "+id+ " je zavrsio sa radom");
        }           
    }
}