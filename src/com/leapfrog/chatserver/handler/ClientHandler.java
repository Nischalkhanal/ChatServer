/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leapfrog.chatserver.handler;

import com.leapfrog.chatserver.entity.Client;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nick
 */
public class ClientHandler {

    private List<Client> clients = new ArrayList<>();

    public void addclient(Client client) {
        this.clients.add(client);

    }

    public Client getByUserName(String userName) {
        for (Client c : clients) {
            if (c.getUserName().equals(userName)) {
                return c;
            }
        }
        return null;
    }

    public Client getBySocket(Socket socket) {
        for (Client c : clients) {
            if (c.getSocket().equals(socket)) {
                return c;
            }
        }
        return null;
    }

    public List<Client> getAll() {
        return clients;
    }

    public void broadcastMessage(Client client, String message) throws IOException {
        for (Client c : clients) {
            if (!c.equals(client)) {
                PrintStream ps = new PrintStream(c.getSocket().getOutputStream());
                ps.println(message);
            }
        }

    }

    public void broadcastPrivateMessage(Client client, Client receiver, String message) throws IOException {
        for (Client c : clients) {
            if (c.equals(receiver)) {
                PrintStream ps = new PrintStream(c.getSocket().getOutputStream());
                ps.println(message);

            } else {
                PrintStream ps = new PrintStream(client.getSocket().getOutputStream());
                ps.println("This user is not available or has muted you");
            }

        }

    }
}
