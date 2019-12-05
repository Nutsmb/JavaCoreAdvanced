package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private Socket socket;
    DataInputStream inputStream;
    DataOutputStream outputStream;
    Chat_Server server;

    public ClientHandler(Chat_Server server, Socket socket){

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
                            if(str.equals("/qiut")){
                                outputStream.writeUTF("/serverclosed");
                                break;
                            }
                            server.broadcastMsg(str);
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
