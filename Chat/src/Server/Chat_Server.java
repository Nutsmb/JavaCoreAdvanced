package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Chat_Server {
    public static void main(String[] args) {
        ServerSocket server = null;
        Socket socket = null;

        try {
            server = new ServerSocket(8123);
            System.out.println("Сервер запустился!");

            socket = server.accept();
            System.out.println("Клиент подключился!");

            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());


            while (true){
                String str = inputStream.readUTF();

                if(str.equals("/qiut")){
                    break;
                }

                System.out.println("Client: " + str);
                outputStream.writeUTF(str);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Не удалось закрыть сокет на стороне сервера.");
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Не удалось закрыть сервер.");
            }
        } // Closing socket and server
    }

}
