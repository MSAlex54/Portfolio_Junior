package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class ClientHandler {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private ArrayList <String> blackList;
    private Server server;
    private String nick;
    private String login;

    public ClientHandler(Socket socket, Server server) {
        try {
            this.blackList = new ArrayList<>();
            this.socket = socket;
            this.server = server;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true){
                            String str = in.readUTF();
                            if (str.equals("/end")){
                                out.writeUTF("/serverClosed");
                                break;
                            }
                            if (str.startsWith("/auth")) {
                                String[] tokens = str.split(" ");
                                String newNick = AuthServer.getNickByLoginAndPass(tokens[1], tokens[2]);
                                if (newNick != null) {
                                    if (server.isOnline(newNick)) {
                                        sendMsg("/auth User is already online");
                                    } else {
                                        sendMsg("/authok");
                                        nick = newNick;
                                        login = tokens[1];
                                        getBlackList();
                                        server.subScribe(ClientHandler.this);
                                        break;
                                    }
                                } else {
                                    if (AuthServer.loginIsBusy(tokens[1])){
                                        sendMsg("/auth Wrong password");
                                    } else {
                                        sendMsg("/auth Wrong login");
                                    }
                                }

                            } else if (str.startsWith("/signup")) {
                                String[]tokens = str.split(" ");
                                if (AuthServer.loginIsBusy(tokens[1])){
                                    sendMsg("/signup Login is already in use");
                                } else if(AuthServer.nickIsBusy(tokens[2])){
                                    sendMsg("/signup Nick is already in use");
                                } else {
                                    AuthServer.putNickByLoginAndPass(tokens[1],tokens[2],tokens[3]);
                                    sendMsg("/signupok");
                                    nick = tokens[1];
                                    login = tokens[2];
                                    server.subScribe(ClientHandler.this);
                                    break;
                                }
                            }
                        }
                        while (true) {
                            String str = in.readUTF();
                            if (str.startsWith("/")) {
                                if (str.equals("/end")) {
                                    out.writeUTF("/serverClosed");
                                    break;
                                }
                                if (str.startsWith("/w")) {
                                    String[] tokens = str.split(" ", 3);
                                    if (!blackList.contains(AuthServer.getLoginByNick(tokens[1]))) {
                                        server.privateMsg(ClientHandler.this, tokens[1], tokens[2]);
                                    } else {
                                        sendMsg("This user in your blackList");
                                    }
                                }
                                if (str.startsWith("/black")) {
                                    String[] tokens = str.split(" ");
                                    if (str.startsWith("/blackadd")) {
                                        if (nick.equals(tokens[1])) {
                                            sendMsg("You can`t add yourself to blacklist!");
                                        } else {
                                            if (!blackList.contains(AuthServer.getLoginByNick(tokens[1]))) {
                                                blackList.add(AuthServer.getLoginByNick(tokens[1]));
                                                AuthServer.updateBlackeList(getLogin(), blackList);
                                                sendMsg("You added user " + tokens[1] + " in blackList.");
                                            } else {
                                                sendMsg("User is already in blackList");
                                            }
                                        }
                                    }
                                    if (str.startsWith("/blackdel")) {
                                        if (blackList.contains(AuthServer.getLoginByNick(tokens[1]))) {
                                            blackList.remove(AuthServer.getLoginByNick(tokens[1]));
                                            AuthServer.updateBlackeList(getLogin(), blackList);
                                            sendMsg("You remove user " + tokens[1] + " from blackList.");
                                        } else {
                                            sendMsg("User not in your blackList");
                                        }
                                    }
                                }
                            } if (str.startsWith("/update ")){
                                String[] tokens = str.split(" ");
                                if (AuthServer.nickIsBusy(tokens[1])){
                                    sendMsg("/updatefail");
                                } else {
                                    sendMsg("/updateok");
                                    server.broadCastMsg(nick + " now nown as " + tokens[1], ClientHandler.this);
                                    nick = tokens[1];
                                    AuthServer.updateNickname(login, tokens[1]);
                                    server.broadCastUserList();
                                }
                            } else {
                                server.broadCastMsg(str, ClientHandler.this);
                            }
                        }

                    }catch (IOException e){
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    server.unSubScribe(ClientHandler.this);
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg){
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNick() {
        return nick;
    }
    public String getLogin(){
        return login;
    }

    public boolean checkBlackList(String login){
        return blackList.contains(login);
    }

    public void getBlackList (){
        if(AuthServer.getBlackList(login)!=null) {
            blackList.addAll(Arrays.asList(AuthServer.getBlackList(login).toString().split(";")));
        }
    }
}
