package rs.ac.fink.proizvodjacpotrosac;

public class Skladiste {
    private static int id=0;
    private int SkladisteID=++id;
    private int[] niz;
    // ulaz i izlaz sluze da se zna dokle se stiglo sa proizvodnjom, dokle sa potrosnjom
    private int ulaz; // brojac za proizvodnju
    private int izlaz; // brojac za potrosnju
    private int stanje; // koliko je trenutno elemenata u skladistu
    private final int kapacitet; // velicina niza

    public Skladiste(int kapacitet) {
        this.kapacitet = kapacitet;
        niz = new int[kapacitet];
    }

    public int getSkladisteID() {
        return SkladisteID;
    }

    public int getStanje() {
        return stanje;
    }

    // sl. dve gettter metode dodate zbog klase Izvestac
    public int[] getNiz() {
        return niz.clone();
    }

    public int getKapacitet() {
        return kapacitet;
    }
    
    // synchronized - radi sa vise niti
    // synchronized obezbeđuje da samo jedna nit može istovremeno da pristupi funkciji
    public synchronized void Stavi(int element) throws InterruptedException {
        while(stanje == kapacitet) // proverava da li je bafer pun
            wait(); // throws InterruptedException je zbog ovog wait
            //  ako nit koja poziva metodu bude prekinuta dok čeka
        niz[ulaz++] = element; // dodavanje el. u bafer
        // Dodaje element na poziciju ulaz u nizu, a zatim povećava ulaz za 1.
        stanje++; // povecava broj el. u baferu
        
        if(ulaz == kapacitet)
            ulaz = 0;
        /* Ako je pokazivač ulaz dostigao kapacitet bafera, resetuje ga na 0,
        jer bafer funkcioniše kao kružni niz 
        (kad se dođe do kraja, nastavlja se od početka) */
        
        notifyAll(); // sluzi da probudi niti koje cekaju, koje su bile blokirane zbog toga sto je buffer bio prazan
    }
    
    public synchronized int Uzmi() throws InterruptedException {
        while(stanje == 0) 
            wait();
        
        int element = niz[izlaz];
        niz[izlaz++]=0;
        stanje--;
        
        if(izlaz == kapacitet)
            izlaz = 0;
        notifyAll(); // ima efekat na sve one koji su se blokarali
        return element;
    }
}

/*
Ukratko
Stavi dodaje element u bafer ako nije pun, 
dok Uzmi uzima element iz bafera ako nije prazan
*/