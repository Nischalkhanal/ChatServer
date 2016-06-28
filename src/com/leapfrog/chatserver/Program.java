/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leapfrog.chatserver;

import com.leapfrog.chatserver.handler.ClientHandler;
import com.leapfrog.chatserver.handler.ClientListener;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author nick
 */
public class Program {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int port = 7777;
        
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server running at " + port);
            ClientHandler handler=new ClientHandler();
            while (true) {
                Socket client = server.accept();
                System.out.println("Connection request from:" + client.getInetAddress().getHostAddress());
                ClientListener listener = new ClientListener(client,handler);
                listener.start();
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());

        }

    }

}
