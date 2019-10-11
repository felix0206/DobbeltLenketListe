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


//   Oppgave 1
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
        if (antallIkkeNulleElementer == 0){return;}

        while (antall == 0){
        leggTilFørste(a[j]);
        j++;
        }
        if (antall == 1 && antallIkkeNulleElementer > 1){
           while (antallIkkeNulleElementer != antall){
               leggTilNeste(a[j]);
                j++;
           }
        }
    }

    public void leggTilNeste(T a){
        if (antall == 0){
            System.out.println("Hva faen skjedde her?");
        }
        if (a != null){
        Node current = new Node(a, hale, null);
        hale.neste = current;
        hale = current;
        antall++;
    }
    }



    //legger til elementene i begynnelsen av listen
    public void leggTilFørste(T a) {
        if(a != null){
//            Instansierer nye node å legge seg som nåværende hode
        Node currentNode = new Node(a, null, hode);
        if(hode != null ) {
            hode.forrige = currentNode;
        }
        hode = currentNode;
        if(hale == null) {
            hale = currentNode;
        }
            antall++;
        }
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



    //  Oppgave 2

    @Override
    public String toString() {
//        Kommentarer se omvendtString metoden
        StringBuilder ut = new StringBuilder();
        Node current = hode;

        if (current == null){
            ut.append( "[]");
        }else {
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
//      Instansierer en Stringbuilder
        StringBuilder ut = new StringBuilder();
//        Instansierer en Node fra halen
        Node current = hale;

//        Sjekker om node er null som betyr at listen er tom. returnerer derfor et tomt array
        if (current == null){
            ut.append( "[]");
        }else {
            ut.append("[");
//            Skriver ut første verdi så det blir riktig i forhold til det oppgaven spør om.
            ut.append(current.verdi);
//            Setter Current til neste verdi i listen
            current = current.forrige;
//            Iterer gjennom hele listen og skriver ut current verdi.
            while (current != null) {
                ut.append(", " + current.verdi);
                //Setter Current til neste verdi i listen
                current = current.forrige;
            }
            ut.append("]");
        }
        return ut.toString();
    }
    // oppgave 2 b

    @Override
    public boolean leggInn(T verdi)
    {
        Objects.requireNonNull(verdi,"Nullverdier er ikke tillatt!");



//            Setter inn noden dersom verd
            if(tom()) {
//                Instansierer node til å bli hale
                Node currentNode = new Node(verdi, hode, null);
                hale = currentNode;
                if (hode == null) {
                    hode = currentNode;
                }
            }else if(antall == 1){
                Node currentNode = new Node(verdi, hode, null);
                hale = currentNode;
                hode.neste = hale;
            }else  {
//               Instansierer den nye noden til å bli halen med neste peker== null
                Node current = new Node(verdi, hale, null);
                hale.neste = current;
                hale = current;
            }
        antall++;
        endringer++;
        return true;
    }




    //    oppgave 3
    private Node<T> finnNode(int indeks) {
        indeksKontroll(indeks, false);
        if (indeks < antall/2){
            int teller = 0;
            Node current = hode;
            while (teller < indeks){
                current = current.neste;
                teller++;
            }return current;
        }else {
            int teller = antall - 1;
            Node curr = hale;
            while (teller > indeks) {
                curr = curr.forrige;
                teller--;
            }
            return curr;
        }
    }


    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks, false);
        Node nodeVerdiMedIndex = finnNode(indeks);
        return (T) nodeVerdiMedIndex.verdi;
    }


    @Override
    public T oppdater(int indeks, T nyverdi)
    {
        //Kontrolerer indeks
        indeksKontroll(indeks, false);
        //Kontrolerer ny verdi
        Objects.requireNonNull(nyverdi, "Ups, du kan ikke oppdatere null inn i en liste");
        //Setter objektet til indeksen i gitt posisjon til en oldNodeValue.
        Node<T> oldNodeValue = finnNode(indeks);
        //Må sette denne ettersom den andre blir slettet etter jeg fjerner oldvalue
        T returneringsVerdi = oldNodeValue.verdi;
        //Fjerner den gamle verdien.
        leggInn(indeks, nyverdi);
        fjern(oldNodeValue.verdi);
        //Legger til den nye verdien til listen.
        //Fjerner denne verdien med metoden fjern
        //plusser ikke på antall endringer da dette blir gjor i både leggInn og fjern

        //Returnerer den gamle verdien.
        return  returneringsVerdi;

    }

// Oppgave 3b

    // subliste
    public Liste<T> subliste(int fra, int til)
    {
        fratilKontroll(antall, fra, til);

        DobbeltLenketListe<T> liste = new DobbeltLenketListe<>();
        for(int i = fra; i < til; i++){
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
                    ("til(" + til + ") > tabelengde(" + antall + ")");

        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }


//  opggave 4

    @Override
    public int indeksTil(T verdi)
    {
        if (verdi == null){
            return -1;
        }
        Node<T> current = hode;

        for(int i = 0; i < antall; i++){
            if(current.verdi.equals(verdi)){
                return i;
            }
            current = current.neste;
        }
        return -1;

    }

    @Override
    public boolean inneholder(T verdi)
    {
       return indeksTil(verdi) != -1;
    }


//    Oppgave 5

    @Override
    public void leggInn(int indeks, T verdi) {
        Objects.requireNonNull(verdi);
        indeksKontroll(indeks,true);

        if (tom()) {
            leggTilFørste(verdi);
            endringer++;
        }else if(indeks == antall){
            leggInn(verdi);
            endringer++;
     }else if(indeks == 0){
            leggTilFørste(verdi);
            endringer++;
        } else{
             Node gamleNode = finnNode(indeks);
//          Finner verdien som er på plasseringen til den gamle verdien som ligger på plasseringen der vi ønsker å legge til vår nye node.
//         Instansierer Nodene p,q og r. Der jeg vil legge q etter p = curr og r = curr skal ligge etter noden. q skal legges inn i mitten av de 2 nodene.
//            Noden etter den nye som skal legges inn
            //            Må instansierer en ny node q.
             Node newNode = new Node(verdi);
             newNode.forrige = gamleNode.forrige;
             newNode.neste = gamleNode;
             newNode.neste.forrige = newNode;
             newNode.forrige.neste = newNode;
             endringer++;
             antall++;
        }}


// Oppgave 6

    @Override
public boolean fjern(T verdi) {
    //Tester om liste faktisk eksisterer ellers er det ingen elementer og slette.
    if (tom()) {
        return false;
    }
//    Heller ingen vits å prøve og slette en node som ikke kan eksistere
    if(verdi == null){
        return false;
    }

    if (antall == 1){
        hode = null;
        hale = null;
        antall--;
        endringer++;
        return true;
    }

//    Om verdien == hode sin verdi sletter jeg hode og setter neste element til hode.
    if (hode.verdi.equals(verdi)) {
        hode = hode.neste;
        hode.forrige = null;
        antall--;
        endringer++;
        return true;
   }
//  Gjør det samme om verdien er lik hale sin verdi.
    if (hale.verdi.equals(verdi)) {
        hale = hale.forrige;
        hale.neste = null;
        antall--;
        endringer++;
        return true;
    }
//        Om noden ligger mellem hode og hale
        //       Finner node med verdi = ønsket slettes sin verdi.
        Node current= finnNodeMedVerdi(verdi);
//        Sjekker om noden faktisk finnes i listen. for dersom den ikke gjør det vil finnNodemedveradi gi at current.neste = null
        if (current.neste == null){
//           Er dessverre ikke mye jeg kan gjøre når du vil fjerne noe som ikke eksisterer
            return false;
        }
              current.forrige.neste = current.neste;
              current.neste.forrige = current.forrige;
        antall--;
        endringer++;
        return true;
    }

    private Node finnNodeMedVerdi(T verdi){
        Node current = hode;
//       Finner node med verdi = ønsket slettet sin verdi.
        while (!current.verdi.equals(verdi) && current.neste != null) {
            current = current.neste;
        }
        return current;
    }



    @Override
    public T fjern(int indeks) {
        if (indeks < 0 || indeks > antall - 1) {
            throw new IndexOutOfBoundsException("The index " + indeks + " is out of bounds.");
        }
        if (tom()) {
            throw new NoSuchElementException("Det finnes ingen liste å fjerne fra");
        }

        if(antall == 1){
            Node current = hode;
            hode = null;
            hale = null;
            antall--;
            endringer++;
            return (T) current.verdi;
        }
        //Looper gjennom listen til gitt indeks.
        Node current = finnNode(indeks);
        //Om curr.forrige == null => curr == hode.
        if (current.equals(hode)) {
            //Setter sin neste node sin forrigepeker til null
            current.neste.forrige = null;
            //Setter hode til å være den neste noden i listen.
            hode = current.neste;
//            ellers, om current.neste er null dvs at man skal slette halen
        }else if (current.equals(hale)) {
            hale = hale.forrige;
            hale.neste = null;
        } else {
            current.neste.forrige = current.forrige;
            current.forrige.neste = current.neste;
        }
       antall--;
        endringer++;
        return (T) current.verdi;
    }


//         Oppgave 7
    @Override
    public void nullstill()
    {
        //Metode 1:
        Node current = hode;

        while(current.neste != null){
            hode = hode.neste;
            current.forrige = null;

            current = hode;
            endringer++;
        }
        hale.neste = null;
        hode.forrige = null;
        hode = hale = null;
        endringer++;
        antall = 0;

    }



//    oppgave 8
@Override
public Iterator<T> iterator()
{
    return new DobbeltLenketListeIterator();
}

    public Iterator<T> iterator(int indeks)
    {
        indeksKontroll(indeks, false);
        return new DobbeltLenketListeIterator(indeks);
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
            if(iteratorendringer != endringer){
                throw new ConcurrentModificationException("ER IKKE LIK");
            }
            if(!hasNext()) {
                throw new NoSuchElementException("ER IKKE FLERE IGJEN I LISTEN");
            }
            T denneVerdi = denne.verdi;
            fjernOK = true;
            denne = denne.neste;  // itereres igjennom lista
            return denneVerdi;
        }


//        oppgave 9
        @Override
        public void remove()
        {

            if (!fjernOK){
                throw new IllegalStateException("Ulovlig tilstand!");
            }

            if (endringer != iteratorendringer){
                throw new ConcurrentModificationException("Endringer og iteratorendringer er ikke like!");
            }

            fjernOK = false;
            if(antall == 1){
                hode = null;
                hale = hode;
            }
            else if(denne == null){
                hale = hale.forrige;
                hale.neste = null;
            }
            else if(denne.forrige == hode){
                hode = hode.neste;
                hode.forrige = null;
            }
            else{
                Node<T> p = denne;
                Node<T> q = denne.forrige.forrige;
                p.forrige = q;
                q.neste = p;
            }
            antall--;
            endringer++;
            iteratorendringer++;
        }


    }// DobbeltLenketListeIterator
    //Oppgave 10

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c)
    {
        if (liste.antall() != 1){
            for (int j = liste.antall(); j > 0; j--){
                Iterator<T> iterator = liste.iterator();    //Oppretter ny iterator hver iterasjon
                int midlertidigMinste = 0;                  //Setter midlertidigMinste til 0.
                T minsteVerdi = iterator.next();               //Setter midlertidig minsteverdi til første verdi i lenken.
                for (int i = 1; i < j; i++){                //Itterer gjennom lenken frem til n
                    T verdi = iterator.next();              //Setter verdi lik neste verdi i lenken
                    if (c.compare(verdi,minsteVerdi) < 0){     //Sammenligner minsteverdi med verdi for å se om verdi er mindre
                        midlertidigMinste = i;
                        minsteVerdi = verdi;                   //Dersom verdi er mindre blir minsteverdi oppdatert
                    }
                }
                liste.leggInn(liste.fjern(midlertidigMinste));  //Fjerner minste verdien fra lista og legger den til bakerst.
            }
            }
        }
} // DobbeltLenketListe

