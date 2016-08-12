/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.binita.chatserver.Chatserver;

import com.binita.chatserver.listener.ClientHandler;
import com.binita.listener.Client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author Bini
 */
public class ClientListener extends Thread{
    private Socket socket;
    private Client client;
    private ClientHandler handler;
    public ClientListener(Socket socket,ClientHandler handler){
    this.socket=socket;
    this.handler=handler;
    }

    @Override
    public void run() {
        try{
        PrintStream ps=new PrintStream(socket.getOutputStream());
              
                ps.println("welcome");
                ps.println("enter name");
                BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()) );
                String name=reader.readLine();
                
                System.out.println("hello"+name);
                client=new Client(name, socket);
                handler.addClient(client);
                handler.broadcastMessage(client.getUsername()+ "has joined");
                while(!isInterrupted()){
                String msg=reader.readLine();
                String[] tokens =msg.split(";;");
                    System.out.println(tokens.length);
                if(tokens[0].equalsIgnoreCase("pm")){
                if(tokens.length>2){
                 handler.PrivateMessage(tokens[1],"(PM) from"  + client.getUsername() + ">" + tokens[2]);
                }
                }else{
            handler.broadcastMessage(client.getUsername()+ " tells u >"+ msg);
                }      
                }
        }catch(IOException ioe){
       

    
}
    }
}
