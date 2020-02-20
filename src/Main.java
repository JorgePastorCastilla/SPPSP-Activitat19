public class Main {

    public static void main(String[] args) {

        Joselito primero = new Joselito(300,301,"tokencito");
        Joselito s = new Joselito(301,302);
        Joselito t = new Joselito(302,300);


        s.start();
        t.start();
        primero.start();

    }

}
