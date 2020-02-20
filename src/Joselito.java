import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Joselito extends Thread{

    private boolean esPrimero;
    private String token;
    private int puerto;
    private int puertoDestino;
    private DatagramSocket socket;
    private DatagramPacket paqueteRecibido;
    private DatagramPacket paqueteEnviado;
    byte[] buf;

    public Joselito(int puerto, int puertoDestino){
        this.esPrimero = false;
        this.puerto = puerto;
        this.puertoDestino = puertoDestino;
    }
    public Joselito(int puerto, int puertoDestino, String token){
        this.esPrimero = true;
        this.puerto = puerto;
        this.puertoDestino = puertoDestino;
        this.token = token;
    }

    @Override
    public void run() {
//        super.run();
        if(esPrimero){
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            enviar();
        }else{
            recibir();
        }

    }

    public void enviar(){
        try {
            InetAddress ip = InetAddress.getByName("127.0.0.1");
            //creamos el datagrama socket
            socket = new DatagramSocket();
            // Inicializamos el paquete que vamos a mandar a B
            paqueteEnviado = new DatagramPacket(token.getBytes(),token.length(),ip,puertoDestino);
            // Enviamos el paquete con el mensaje
            socket.send(paqueteEnviado);
            // Cerramos el socket
            socket.close();
            if(esPrimero){
                recibir();
            }

        } catch (IOException e) {
            System.out.println("Error de A: " + e.getMessage());
        }
    }

    public void recibir(){
        try {
            // Inicializamos el socket de recibir
            socket = new DatagramSocket(puerto);
            buf = new byte[1024];
            // Inicializamos el paquete con un tamaño
            paqueteRecibido = new DatagramPacket(buf,buf.length);
            // Esperamos a recibir el paquete
            socket.receive(paqueteRecibido);
            // Creamos un string con los datos que vienen del paquete
            token = new String(paqueteRecibido.getData(),0,paqueteRecibido.getLength());
            System.out.println("soy el joselito del puerto: "+puerto+" y este es mi puto token: "+token);
            this.token = token;
            // Cerramos el socket
            if(esPrimero){
                System.out.println("SOY EL PRIMERO Y ME HE PASADO EL JUEGO");
                System.exit(0);
            }
            socket.close();
            enviar();

        } catch (IOException e) {
            System.out.println("Error de A: " + e.getMessage());
        }
    }

}
