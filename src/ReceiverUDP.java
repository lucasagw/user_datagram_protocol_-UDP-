import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ReceiverUDP {
    public static void main(String[] args) {

        DatagramSocket tomada = null;
        try {
            InetAddress ip = InetAddress.getByName("127.0.0.1");
            int porta = 5000;
            tomada = new DatagramSocket();

            byte[] cartaAEnviar = new byte[64];
            DatagramPacket envelopeAEnviar
                    = new DatagramPacket(cartaAEnviar,
                    cartaAEnviar.length,
                    ip,
                    porta);
            tomada.send(envelopeAEnviar);

            byte[] documento = new byte[100];
            DatagramPacket envelopeAReceber
                    = new DatagramPacket(documento,
                    documento.length);
            tomada.receive(envelopeAReceber);
            byte[] data = envelopeAReceber.getData();
            try (FileOutputStream fos = new FileOutputStream("documentos")) {
                fos.write(data);
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        tomada.close();
    }
}