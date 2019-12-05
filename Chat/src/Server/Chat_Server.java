package Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Chat_Server {
    private Vector<ClientHandler> clients;

    public Chat_Server() {
        clients = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;

        try {
            server = new ServerSocket(8123);
            System.out.println("Сервер запустился!");
            while (true){
                socket = server.accept();
                System.out.println("Клиент подключился!");
                clients.add(new ClientHandler(this, socket));
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

    public void broadcastMsg(String msg) {
        for (ClientHandler client: clients) {
            client.sendMsg(msg);
        }
            
    }

}

