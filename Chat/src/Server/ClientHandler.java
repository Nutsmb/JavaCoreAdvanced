package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class ClientHandler {
    private Socket socket;
    DataInputStream inputStream;
    DataOutputStream outputStream;
    ChatServer server;
    String nick;
    private ArrayList<String> authorizedUsers  = new ArrayList();

    private static final Logger logger = Logger.getLogger("");

    List<String> blackList;

    public ClientHandler(ChatServer server, Socket socket){

        Handler handler = null;
        try {
            handler = new FileHandler("Commands.log",true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        handler.setLevel(Level.ALL);
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);

        try {
            this.socket = socket;
            this.server = server;
            this.inputStream = new DataInputStream(socket.getInputStream());
            this.outputStream = new DataOutputStream(socket.getOutputStream());
            this.blackList = new ArrayList<>();

            new Thread(() -> {
                    try {
                        while (true){
                            String str = inputStream.readUTF();
                            if(str.startsWith("/auth")){
                                String[] token = str.split(" ");
                                String newNick = AuthService.getNickByLoginAndPass(token[1], token[2]);
                                if(newNick != null){
                                    if(authorizedUsers.contains(newNick)){
                                        sendMsg("Этот пользователь уже авторизовался");
                                        logger.log(Level.WARNING, "Попытка повторной авторизации. "+ newNick);
                                    }
                                    else {
                                        nick = newNick;
                                        authorizedUsers.add(newNick);
                                        sendMsg("/authOK " + nick);
                                        server.subscribe(ClientHandler.this);
                                        blackList = AuthService.getBlacklist(nick);
                                        logger.log(Level.INFO, "Подключился пользователь " + nick +"!");
                                        break;
                                    }
                                }
                                else {
                                    sendMsg("Неверный логин/пароль");
                                    logger.log(Level.WARNING, "Ошибка авторизации по логину/паролю" + token[1] + " " + token[2]);
                                }
                            }
                            if(str.startsWith("/regis")){
                                String[] token = str.split(" ");
                                if(AuthService.getNickByLoginAndPass(token[1], token[2]) == null){
                                    AuthService.signup(token[1], token[2], token[3]); // добавить в базу пользователя
                                    // авторизовать пользователя
                                    nick = token[3];
                                    authorizedUsers.add(nick);
                                    sendMsg("/authOK " + nick);
                                    server.subscribe(ClientHandler.this);
                                    blackList = AuthService.getBlacklist(nick);
                                    logger.log(Level.INFO, "Подключился новый пользователь " + nick +"!");
                                    break;
                                }
                                else {
                                    sendMsg("Такой логин уже существует");
                                    logger.log(Level.WARNING, "Попытка повторной регистрации с логин "+ token[1]);
                                }
                            }
                        }

                        while (true){
                            String str = inputStream.readUTF();
                            if(str.equals("/quit")){
                                outputStream.writeUTF("/serverclosed");
                                break;
                            }
                            if(str.startsWith("/pm")){
                                String[] token = str.split(" ");
                                String addressee = token[1];
                                str = str.replace("/pm ", "");
                                str = str.replace(addressee, "");
                                server.privateMsg(this, addressee, str);
                            }
                            else if (str.startsWith("/blacklist ")) { // /blacklist nick3
                                String[] tokens = str.split(" ");
                                blackList.add(tokens[1]);
                                AuthService.addToBlacklist(nick, tokens[1]);
                                sendMsg("Вы добавили пользователя " + tokens[1] + " в черный список");
                            } else {
                                server.broadcastMsg(this, nick + ": " + str);
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
                            logger.log(Level.SEVERE, "Входящий поток не закрылся!");
                        }
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("Исходящий поток не закрылся!");
                            logger.log(Level.SEVERE, "Исходящий поток не закрылся!");
                        }
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("Сокет сервера не закрылся!");
                            logger.log(Level.SEVERE, "Сокет сервера не закрылся!");

                        }
                        server.unsubscribe(ClientHandler.this);
                    } // Closing input&output streams and socket
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
            logger.log(Level.INFO, "Сообщение отправить не удалось!");
        }
    }

    public String getNick() {
        return nick;
    }

    public boolean checkBlackList(String nick) {
        return blackList.contains(nick);
    }
}