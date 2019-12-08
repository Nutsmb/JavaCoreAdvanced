package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler {
    private Socket socket;
    DataInputStream inputStream;
    DataOutputStream outputStream;
    ChatServer server;
    String nick;
    private ArrayList<String> authorizedUsers  = new ArrayList();

    public ClientHandler(ChatServer server, Socket socket){

        try {
            this.socket = socket;
            this.server = server;
            this.inputStream = new DataInputStream(socket.getInputStream());
            this.outputStream = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true){
                            String str = inputStream.readUTF();
                            if(str.startsWith("/auth")){
                                String[] token = str.split(" ");
                                String newNick = AuthService.getNickByLoginAndPass(token[1], token[2]);
                                if(newNick != null){
                                    if(authorizedUsers.contains(newNick)){
                                        sendMsg("Этот пользователь уже авторизовался");
                                    }
                                    else {
                                        nick = newNick;
                                        authorizedUsers.add(newNick);
                                        sendMsg("/authOK " + nick);
                                        server.subscribe(ClientHandler.this);
                                        break;
                                    }
                                }
                                else {sendMsg("Неверный логин/пароль");}
                            }
                        }

                        while (true){
                            String str = inputStream.readUTF();
                            if(str.equals("/quit")){
                                outputStream.writeUTF("/serverclosed");
                                break;
                            }
                            if(str.startsWith("/pm")){
                                server.broadcastMsg(str);
                            } else {
                                server.broadcastMsg(nick + " :" + str);
                            }
                        }
                    } catch (IOException e){
                        e.printStackTrace();
                    } finally {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("Входящий поток не закрылся!");
                        }
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("Исходящий поток не закрылся!");
                        }
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("Сокет сервера не закрылся!");
                        }
                        server.unsubscribe(ClientHandler.this);
                    } // Closing input&output streams and socket
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg){
        try {
            outputStream.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Сообщение отправить не удалось!");
        }
    }
}