/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlThreads;

import Models.Server;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author martin
 */
public class InputListener extends Thread {
    Server server;
    Socket socket;
    DataInputStream in;

    public InputListener(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }
    
    @Override
    public void run(){
        try{
            String notification = "";
            in = new DataInputStream(socket.getInputStream());
            
            while(true){
                notification = in.readUTF();
                if (!notification.equals("exit()")){
                    server.notifyClients(notification);
                }
                else{
                    server.notifyServer("Se ha desconectado un cliente." + "\n");
                    in.close();
                    socket.close();
                    break;
                }
            }

        }
        catch(IOException e){
            System.err.println("Error activando listeners: " + e.getMessage());
        }
    }
}
