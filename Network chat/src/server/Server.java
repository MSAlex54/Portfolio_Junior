package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {
    private Vector<ClientHandler> clients;
    Server() {
        clients = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;
        try {
            AuthServer.connection();
            server = new ServerSocket(8189);
            System.out.println("Server is running");
            while (true){
                socket = server.accept();
                new ClientHandler(socket,this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e){
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            AuthServer.disconnect();
        }
    }

    public void broadCastMsg(String msg , ClientHandler from){
        for (ClientHandler o:clients) {
            if (msg.startsWith("/ ")){
                o.sendMsg(msg.split(" ",2)[1]);
            } else {
                if (o.getNick().equals(from.getNick())){
                    o.sendMsg("I:" + "\n" + msg);
                } else if(!o.checkBlackList(from.getLogin())) {
                    o.sendMsg(from.getNick() +  ": " + "\n"  + msg);
                }
            }
        }
    }

    public void privateMsg(ClientHandler from, String to, String msg){
        Boolean checkBL = false;
        for (ClientHandler o :clients){
            if (o.getNick().equals(to)){
                if (o.checkBlackList(from.getLogin())){
                    checkBL = true;
                    break;
                }
            }
        }
        if (checkBL){
            from.sendMsg( to + " added you in blackList. You can`t send private mesages to this user.");
        } else {
            from.sendMsg("(private) to " + to + " "  + msg);
            for (ClientHandler o:clients) {
                if (o.getNick().equals(to)) {
                    o.sendMsg("(private) from " + from.getNick() + " " + msg);
                }
            }
        }
    }

    public boolean isOnline(String nick){
        for (ClientHandler o:clients) {
            if (o.getNick().equals(nick)) {
                return true;
            }
        }
        return false;
    }

    public void subScribe(ClientHandler client){
        clients.add(client);
        broadCastUserList();
        System.out.println(client.getNick() + " is connected");
        broadCastMsg("/ " + client.getNick() + " is connected" , client);
    }
    public void unSubScribe(ClientHandler client){
        if (clients.contains(client)) {
            clients.remove(client);
            broadCastUserList();
            System.out.println(client.getNick() + " is disconnected");
            broadCastMsg("/ " + client.getNick() + " is disconnected", client);
        }
    }
    public void broadCastUserList(){
        StringBuilder sb = new StringBuilder();
        sb.append("/clientlist ");
        for (ClientHandler c: clients) {
            sb.append(c.getNick() + " ");
        }
        String out = sb.toString();
        for (ClientHandler o:clients) {
            o.sendMsg(out);
        }
    }
}
