/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leapfrog.chatserver.handler;

import com.leapfrog.chatserver.dao.UserDAO;
import com.leapfrog.chatserver.dao.impl.UserDAOImpl;
import com.leapfrog.chatserver.entity.Client;
import com.leapfrog.chatserver.entity.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nick
 */
public class ClientListener extends Thread {

    private Socket socket;
    private PrintStream pstream;
    private BufferedReader reader;
    private UserDAO userDAO = new UserDAOImpl();
    private ClientHandler handler;
    private Client client;

    public ClientListener(Socket socket, ClientHandler handler) throws IOException {
        this.socket = socket;
        this.handler = handler;
        pstream = new PrintStream(socket.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    }

    @Override
    public void run() {
        try {

            pstream.println("Hello from server\r\nServer time is " + new Date().toString());
            while (!doLogin()) {
                pstream.println("Invalid userName & Password or not activated yet");
            }
            
            while(true){
             pstream.println(">");
             String line=reader.readLine();
             String[]messageArray = line.split(";;");

                if (messageArray[0].equalsIgnoreCase("pm")) {
                    try {
                        Client destination = handler.getByUserName(messageArray[1]);
                            handler.broadcastPrivateMessage(client, destination, client.getUserName() + " personally says " + messageArray[2]);
                        
                    } catch (NullPointerException ex) {
                        pstream.println("No such Clients exists");
                    }
                }else{
             handler.broadcastMessage(client, client.getUserName() + " says >" + line);
             }
            }

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

    }   

    private boolean doLogin() throws IOException {
        pstream.println("Enter your UserName:");
        String UserName = reader.readLine();
        pstream.println("Enter your Password:");
        String Password = reader.readLine();

        User user = userDAO.login(UserName, Password);
        if (user == null) {
            return false;

        } else if (!user.isStatus()) {
            return false;

        }
        client=new Client(UserName, socket);
        handler.addclient(client);
        

        return true;

    }

    //private boolean checkIfAvailable(Client source, Client destination) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


