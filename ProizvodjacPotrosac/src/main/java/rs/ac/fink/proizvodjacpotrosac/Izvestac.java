// u regularnim vremenskim intervalima prikazuje sadržaj zadatog skladišta

package rs.ac.fink.proizvodjacpotrosac;

public class Izvestac extends Thread {
    private static int statId = 0; 
    private int id=++statId; 
    
    private Skladiste skladiste;
    private int interval; 

    public Izvestac(Skladiste skladiste, int interval) {
        this.skladiste = skladiste;
        this.interval = interval;
    }
    
    @Override
    public void run() {
        System.out.println("Izvestaj br.  "+id+" je zapocet.");
        
        try {
            while (!interrupted()) {
                synchronized (skladiste) {
                    int[] stanje = skladiste.getNiz();
                    System.out.print("Izvestac " + id + " - Sadrzaj skladista: ");
                    for (int i = 0; i < stanje.length; i++) {
                        if (stanje[i] != 0) {
                            System.out.print(stanje[i] + " ");
                        }
                    }
                    System.out.println();
                }
                sleep(10000); // Provera stanja na svakih 10 sekundi
            }
            
        } catch (InterruptedException ex) {
            System.out.println("Izvestac "+id+" je zavrsio sa izvestajem.");
        }
    }
    
    private synchronized void prikazSadrzaja() {
        System.out.println("Izvestaj za skladiste ID: " + skladiste.getSkladisteID());
        System.out.println("Trenutno stanje skladista: " + skladiste.getStanje() + "/" + skladiste.getKapacitet());

        int[] trenutniNiz = skladiste.getNiz();  // Dobijanje kopije niza
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
