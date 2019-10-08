import java.awt.*;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class DobbeltLenketListe<T> implements Liste<T>
{
    private static final class Node<T>   // en indre nodeklasse
    {
        // instansvariabler
        private T verdi;
        private Node<T> forrige, neste;

        private Node(T verdi, Node<T> forrige, Node<T> neste)  // konstruktør
        {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        protected Node(T verdi)  // konstruktør
        {
            this(verdi, null, null);
        }

    } // Node

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;   // antall endringer i listen

    // hjelpemetode finnNode finner en node til en gitt indeks.
    private Node<T> finnNode(int indeks) {
        if(indeks < antall && indeks >= 0){
        if (indeks < antall/2){
            int teller = 0;
            Node current = hode;
            while (current != null){
                if(teller != indeks){
                    current = current.neste;
                    teller++;
                }
                return current;
            }
        }else {
            int teller = antall-1;
            Node curr = hale;
            while (curr != null) {
                if (teller != indeks) {
                    curr = curr.forrige;
                    teller++;
                }
                return curr;
            }
        }
        throw new NoSuchElementException("");
        }
        else {
            throw new IndexOutOfBoundsException("");

        }
    }


    // konstruktør
    public DobbeltLenketListe()
    {
        hode = hale = null;
        antall = 0;
        endringer = 0;
    }

    // konstruktør
    public DobbeltLenketListe(T[] a)
    {
        //Sjekker at tabellen ikke er null.
        Objects.requireNonNull(a, "Tabellen a er null!");
        int antallElementer = a.length;
        int antallIkkeNulleElementer = 0;
        int j = 0;

        for (int i = 0; i < antallElementer; i++){
            if(a[i] != null){
                antallIkkeNulleElementer++;
            }
        }

        //Finner antall elementer som ikke er null
        //Dersom tabellen bare har et element vil jeg bare legge til dette.


           while (antallIkkeNulleElementer != antall()){
               leggTilFørste(a[j]);
                j++;
           }
       }


    //legger til elementene i begynnelsen av listen
    public void leggTilFørste(T a) {
        if(a != null){
        Node currentNode = new Node(a, hode, null);
        if(hode != null ) {
            hode.forrige = currentNode;
        }
        hode = currentNode;
        if(hale == null) {
            hale = currentNode;
        }
            antall++;
            System.out.println("La til element " + currentNode.verdi + " til " +antall + " plassering");
        }
        System.out.println(a);
    }

    //legger til elementene i slutten av listen. Brukte denne til å legge til elementer både først og sist i liste, men fant ut at det ikke gikk noe raskere
//    public void leggTilSiste(T a) {
//        //Sjekker at verdien ikke = null
//        if(a != null){
//        //Instansierer node med verdi a og en nestepeker til halen
//        Node CurrentNode = new Node(a);
//      //Sjekker om hale sin verdi ikke er null, og setter hale.neste
//        if(hale != null) {
//            hale.forrige = CurrentNode;
//        }
//        hale = CurrentNode;
//        if(hode == null) {
//            hode = CurrentNode;
//        }
//        antall++;
//        }
//    }
////



    // subliste
    public Liste<T> subliste(int fra, int til)
    {
        fratilKontroll(antall, fra, til);

        DobbeltLenketListe<T> liste = new DobbeltLenketListe<>();
        for(int i = fra; i<til; i++){
            Node nyNode = finnNode(i);
            liste.leggInn((T) nyNode.verdi);
        }
        return liste;

    }

    public static void fratilKontroll(int antall, int fra, int til)
    {
        if (fra < 0)                                  // fra er negativ
            throw new IndexOutOfBoundsException
                    ("fra(" + fra + ") er negativ!");

        if (til > antall)                          // til er utenfor tabellen
            throw new IndexOutOfBoundsException
                    ("til(" + til + ") > tablengde(" + antall + ")");

        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }

    @Override
    public int antall()
    {
        return antall;
    }

    @Override
    public boolean tom()
    {
        if(antall == 0){
            return true;
        }
        return false;
    }


    @Override
    public boolean leggInn(T verdi)
    {

        Objects.requireNonNull(verdi,"Nullverdier er ikke tillatt!");

        //Tilfelle 1. tom liste

        if (hode == null && hale == null){
            Node nyNode = new Node(verdi);
            nyNode.forrige = null;
            hale = nyNode;
            hode = nyNode;
            antall++;

            return true;
        }

        //Tilfelle 2.

        Node nyNode = new Node(verdi);
        nyNode.forrige = hale;
        nyNode.neste = null;
        hale.neste = nyNode;
        hale  = nyNode;
        antall++;

        return true;

    }

    @Override
    public void leggInn(int indeks, T verdi)
    {
        if (indeks<0){
            throw new IndexOutOfBoundsException("indeks(" + indeks + ") er negativ!");
        }
        if (indeks > antall){
            throw new IndexOutOfBoundsException("indeks(" + indeks + ") > antall (" + antall + ")" );
        }

        Objects.requireNonNull(verdi);
        indeksKontroll(indeks, false);

        if (indeks == (0)) {
            Node nyNode = new Node(verdi);
            Node forrigeHode = this.hode;
            nyNode.forrige = null;
            nyNode.neste = forrigeHode.neste;
            hode = nyNode;

        }else if (indeks == antall-1){
            Node nyNode = new Node(verdi);
            Node forrigeHale = this.hale;
            forrigeHale.forrige.neste = nyNode;
            nyNode.neste = null;
            hale = nyNode;
        }
    }

    @Override
    public boolean inneholder(T verdi)
    {
       if (indeksTil(verdi) == -1){
           return false;
       } else {
           return true;
       }
    }


    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi)
    {
            Node current = hode;

            for(int i = 0; i < antall; i++){
                if(verdi.equals(current)){
                    return i;
                }
                current = current.neste;
            }
        return -1;
    }

    @Override
    public T oppdater(int indeks, T nyverdi)
    {
        //Kontrolerer indeks
        indeksKontroll(indeks, false);
        //Kontrolerer ny verdi
        Objects.requireNonNull(nyverdi);
        //Setter objektet til indeksen i gitt posisjon til en oldNodeValue.
        T oldNodeValue = finnNode(indeks).verdi;
        //Fjerner denne verdien med metoden fjern
        fjern(indeks);
        //Legger til den nye verdien til listen.
        leggInn(indeks, nyverdi);
        //plusser på antall endringer
        endringer++;
        //Returnerer den gamle verdien.
        return  oldNodeValue;

    }

    @Override
    public boolean fjern(T verdi)
    {
        return true;
    }

    @Override
    public T fjern(int indeks)
    {
        Node curr = new Node(hent(indeks));

        curr.forrige = curr.neste;
        curr.neste = curr.forrige;
        return  hent(indeks);
    }

    @Override
    public void nullstill()
    {
        //Metode 1:
        Node current = new Node(hent(0));            //Starter i hode.
        hode = current;

        for (int i = 0; i < antall; i++){
                current.forrige = null;
                endringer++;                // Oppdaterer antall endringer i listen.
                if (i == antall){
                    antall = 0;             //Oppdaterer antall i listen.
                }
        }

        //metode 2:
        for (int i = 0; i < antall; i++){   //Går gjennom nodene.
            if (current.neste != null) {
                fjern(i);                   //bruker metoden fjern for å slette en og en node.
                endringer++;                // Oppdaterer antall endringer i listen.
            }
            antall = 0;                     //Oppdaterer antall i listen.
        }
    }

    @Override
    public String toString()
    {
        StringBuilder ut = new StringBuilder();
        if (hode == null){
            ut.append("[]");
        }else {
            Node current = hode;
            ut.append("[");
            ut.append(current.verdi);
            current = current.neste;

            while (current != null) {
                ut.append(", " + current.verdi);
                current = current.neste;
            }
            ut.append("]");
        }
       return ut.toString();
    }

    public String omvendtString()
    {
        StringBuilder ut = new StringBuilder();

        Node current = hale;

        if (hale == null){
            return "[]";
        }
        if (current.forrige == null){
            ut.append(hale.verdi);
            return "["+ut.toString()+"]";
        }
        ut.append("[");
        while(current.forrige!=null){

            ut.append(current.verdi+", ");
            current= current.forrige;

        }

        if (hode!=hale){
            ut.append(""+current.verdi) ;
        }
        ut.append("]");
        return ut.toString();
    }

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c)
    { // tester dette imrg vet ikke om det er i nærheten
     /*   for(int i = 0; i < c / 2; i++){
            int temp = liste[i];
            liste[i] = liste[c-i-1];
            liste[c-i-1] = temp;
        } */
    }

    @Override
    public Iterator<T> iterator()
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    public Iterator<T> iterator(int indeks)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator()
        {
            denne = hode;     // denne starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks)
        {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

        @Override
        public boolean hasNext()
        {
            return denne != null;  // denne koden skal ikke endres!
        }

        @Override
        public T next()
        {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

    } // DobbeltLenketListeIterator

} // DobbeltLenketListe

