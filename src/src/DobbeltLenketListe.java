import java.util.Comparator;
import java.util.Iterator;
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

    // hjelpemetode
    private Node<T> finnNode(int indeks)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
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
        if (a.length == 0){
            throw new NullPointerException("Listen er tom!");
        }

        int teller = 0;
        while (a[teller] == null){
            teller++;
        }

        Node hode = new Node(a[teller]);
        Node hale = hode;
        this.hode = hode;
        this.hale=hode;

        Node forrigeNode = hode;
        forrigeNode.verdi = a[teller];
        this.hale.verdi=a[teller];

        for (int i = teller+1; i<a.length; i++){

            while(a[i]==null && i < a.length-1){
                i++;
            }
            if (i == a.length-1 && a[i] == null){
                break;
            }

            Node nyNode = new Node(a[i]);
            nyNode.forrige = forrigeNode;
            forrigeNode.neste = nyNode;

            forrigeNode = nyNode;
            hale = nyNode;
        }

        hale.neste=null;
        this.hale=hale;
        this.hale.verdi=(T)hale.verdi;


    }

    // subliste
    public Liste<T> subliste(int fra, int til)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
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
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public boolean inneholder(T verdi)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public T hent(int indeks)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public int indeksTil(T verdi)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public T oppdater(int indeks, T nyverdi)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public boolean fjern(T verdi)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public T fjern(int indeks)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public void nullstill()
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
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
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
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

