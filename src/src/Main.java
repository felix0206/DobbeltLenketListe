public class Main {
    public static void main(String[] args){

        Liste<String> liste = new DobbeltLenketListe<>();

        String[] a = {"a",null};
        System.out.println(liste.antall() + " " + liste.tom());

        DobbeltLenketListe<String> test = new DobbeltLenketListe<>(a);
        System.out.println(test);
    }
}