package Models;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    //Atributos para conexión a sockets de servidor
    private int port;
    private String host;
    private Socket clientSocket;
    private DataOutputStream out;
    
    public Client(String host, int port){
        this.port = port;
        this.host = host;
        openSocketOutput();
    }
    
    public void openSocketOutput(){
        try {
            clientSocket = new Socket(host,port);
            out = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Error en la apertura del socket y flujo de salida: " + e.getMessage());
           
        }
    }
    
    public void sendMsg(String msg){
        try{
            out.writeUTF(msg);
        }
        catch (IOException e){
            System.err.println("Error en el envío de información por el socket: " + e.getMessage());
        }
    }
  
}
