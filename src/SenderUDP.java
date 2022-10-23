import java.io.File;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Files;

public class SenderUDP {

    public static void main(String[] args) throws Exception {
        int porta = 5000;
        DatagramSocket tomadaServidora = new DatagramSocket(porta);
        System.out.println("Servidor em execucao!");

        byte[] cartaAReceber = new byte[13];
        DatagramPacket envelopeAReceber
                = new DatagramPacket(cartaAReceber,
                cartaAReceber.length);

        tomadaServidora.receive(envelopeAReceber);
        cartaAReceber = envelopeAReceber.getData();
        String nomeDocumento = new String(cartaAReceber).trim();
        File file = new File("src/" + nomeDocumento);
        byte[] fileContent = Files.readAllBytes(file.toPath());

        System.out.println(fileContent.length);
        InetAddress ipCliente = envelopeAReceber.getAddress();
        int portaCliente = envelopeAReceber.getPort();

        DatagramPacket envelopeAEnviar
                = new DatagramPacket(fileContent,
                fileContent.length,
                ipCliente,
                portaCliente);
        tomadaServidora.send(envelopeAEnviar);

        tomadaServidora.close();
    }
}