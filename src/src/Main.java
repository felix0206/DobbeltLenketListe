

public class Main {
    public static void main(String[] args) {

        Liste<Integer> liste = new DobbeltLenketListe<>();

       /* String[] a = {"a", null};
        System.out.println(liste.antall() + " " + liste.tom());

        DobbeltLenketListe<Integer> test = new DobbeltLenketListe<>();
        System.out.println(test);*/

        System.out.println(liste.toString() + " " + ((DobbeltLenketListe<Integer>) liste).omvendtString());
        for (int i = 1; i <= 3; i++) {
            liste.leggInn(i);
            System.out.println(liste.toString()+ " " + ((DobbeltLenketListe<Integer>) liste).omvendtString());

        }
    }

}