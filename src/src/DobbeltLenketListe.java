import java.awt.*;
import java.util.*;

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
        //Dersom tabellen bare har et element vil jeg bare legge til dette.
        if(antallElementer == 1) {
            leggTilFørste(a[0]);
        }else{
            //Dersom elementene er partall legger jeg de inn i listen fra hver sin side av tabellen helt til alle er lagt til
            //Usikker på om dette faktisk gjør den raskere men var gøy å prøve :P.
       if(antallElementer % 2 == 0){
       for(int i = 0; i < antallElementer/2; i++){
        leggTilFørste(a[i]);
        leggTilSiste(a[antallElementer-i-1]);
       } }else{
           //Dersom det er et oddetall av elementer fungerer ikke metoden over og jeg bruker derfor en alternativ metode for dette hvor jeg
           // bare legger til elementer 1 og 1 fra begynnelsen av listen.
           for(int i = 0; i < antallElementer; i++){
               leggTilFørste(a[i]);
           }
       }
       }
    }

    //legger til elementene i begynnelsen av listen
    public void leggTilFørste(T a) {
        if(a != null){
        Node CurrentNode = new Node(a);
        antall++;
        if(hode != null ) {
            hode.neste = CurrentNode;
        }
        hode = CurrentNode;
        if(hale == null) {
            hale = CurrentNode;
        }

        }
    }

    //legger til elementene i slutten av listen
    public void leggTilSiste(T a) {
        //Sjekker at verdien ikke = null
        if(a != null){
        //Instansierer node med verdi a og en nestepeker til halen
        Node CurrentNode = new Node(a);
      //Sjekker om hale sin verdi ikke er null, og setter hale.neste
        if(hale != null) {
            hale.forrige = CurrentNode;
        }
        hale = CurrentNode;
        if(hode == null) {
            hode = CurrentNode;
        }
        antall++;}
    }




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

        Node current = hode;

        if (hode == null){
            return "[]";
        }
        if (current.neste == null){
            ut.append(hode.verdi);
            return "["+ut.toString()+"]";
        }
        ut.append("[");
        while(current.neste!=null){
            ut.append(current.verdi + ", ");
            current= current.neste;

        }

        if (hode!=hale){

            ut.append(current.verdi) ;
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
       /* for(int i = 0; i < liste.antall(); i++){
            int start = liste[0];
            int slutt = liste.antall()-1;
        } */
    }

    @Override
    public Iterator<T> iterator()
    {
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks)
    {
        indeksKontroll(indeks, true);
        return iterator();
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
            denne=finnNode(indeks); // setter denne til noden indeks
            fjernOK = false;
            iteratorendringer = endringer;
        }

        @Override
        public boolean hasNext()
        {
            return denne != null;  // denne koden skal ikke endres!
        }

        @Override
        public T next()
        {

            Node<T> p = hode;
            if(iteratorendringer != endringer){
                throw new ConcurrentModificationException("ER IKKE LIK");
            }
            if(hasNext() != true) {
                throw new NoSuchElementException("ER IKKE FLERE IGJEN I LISTEN");
            }
            fjernOK = true;
            p = p.neste;
            T verdien = p.verdi;
            return verdien;
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

    } // DobbeltLenketListeIterator

} // DobbeltLenketListe

