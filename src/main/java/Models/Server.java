/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Views.ServerView;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;

/**
 *
 * @author martin
 */
public class Server extends Observable implements Runnable{
    
    //Atributos del servidor
    private int port;
    private Socket socket;
    private ServerSocket serverSocket;
    private ServerView serverView;
    
    public Server(int port, ServerView serverView){
        this.port = port;
        this.serverView = serverView;
        //Iniciamos la conexión con el método run() llamado en la vista MainServerView al conectar.
    }
    
    public int getPort(){
        return port;
    }
    
    public void notifyClients(String msg){
        this.setChanged();
        this.notifyObservers(msg + "\n");
        this.clearChanged();
    }
    
    public void notifyServer(String msg){
        serverView.notifyState(msg + "\n");
    }
    
    @Override
    public void run() {
        try{
            serverSocket = new ServerSocket(port);
            while(true){
                serverView.notifyState("Escuchando conexiones entrantes en puerto " + port + "...\n");
                socket = serverSocket.accept();
                serverView.notifyState("Un cliente nuevo se ha conectado: " + socket + "\n");
                InputListener listener = new InputListener(socket,this);
                listener.start();
            }
        }
        catch (IOException e){
            System.err.println("Error en la inicialización del servidor: " + e.getMessage());
        }
    }
}
