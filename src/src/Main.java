import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        //Liste<Integer> liste = new DobbeltLenketListe<>();

       /* String[] a = {"a", null};
        System.out.println(liste.antall() + " " + liste.tom());

        DobbeltLenketListe<Integer> test = new DobbeltLenketListe<>();
        System.out.println(test);*/

       /* System.out.println(liste.toString() + " " + ((DobbeltLenketListe<Integer>) liste).omvendtString());
        for (int i = 1; i <= 3; i++) {
            liste.leggInn(i);
            System.out.println(liste.toString()+ " " + ((DobbeltLenketListe<Integer>) liste).omvendtString());

        }
*/
        Character[] c = {'A','B','C','D','E','F','G','H','I','J'};
        DobbeltLenketListe<Character> liste2 = new DobbeltLenketListe<>(c);
//        System.out.println(liste2.subliste(3,8));
        //System.out.println(liste2.subliste(5,5));
       // System.out.println(liste2.subliste(8,liste2.antall()));
        //System.out.println(liste2.subliste(0,11));

        int liste[]  = {1,2,3,4,5};
        System.out.println(Arrays.toString(liste));
        liste2.nullstill();
        System.out.println(Arrays.toString(liste));


    }

}