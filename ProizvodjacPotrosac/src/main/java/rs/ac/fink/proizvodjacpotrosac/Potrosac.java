/*
klasa Potrosac koji uzima proizvode iz skladišta troši ih vremenski period izmedju minTime i maxTime
 */
package rs.ac.fink.proizvodjacpotrosac;


public class Potrosac extends Thread {
    private static int statId = 0; // da se zna id za svakog
    private int id=++statId; // id se inkrementira preinkrementom
    
    private Skladiste skladiste;
    private int minTime; // minimalno vreme proizvodnje
    private int maxTime;
    
    private int trajanje = minTime + (int)(Math.random()*(maxTime-minTime));

    public Potrosac(Skladiste skladiste, int minTime, int maxTime) {
        this.skladiste = skladiste;
        this.minTime = minTime;
        this.maxTime = maxTime;
    }

    public void run() {
       // System.out.println("Potrosac "+id+" je krenuo da uzima proizvode.");
        
        try {
            while (!interrupted()) {
                int proizvod = skladiste.Uzmi(); // Uzimanje proizvoda
                //System.out.println("Potrosac " + id + " je preuzeo proizvod " + proizvod + ".");
                int trajanje = minTime + (int) (Math.random() * (maxTime - minTime));
                sleep(trajanje); // Simulacija trošenja proizvoda
            }
            
        } catch (InterruptedException ex) {
           // System.out.println("Potrosac "+id+" je zavrsio sa uzimanjem proizvode");
        }
    }
}
