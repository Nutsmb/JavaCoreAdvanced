package Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ChatServer {
    private Vector<ClientHandler> clients;

    public ChatServer() {
        clients = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;

        try {
            AuthService.connect();
            String res = AuthService.getNickByLoginAndPass("login1", "pass1");
            System.out.println(res);
            server = new ServerSocket(8123);
            System.out.println("Сервер запустился!");
            while (true){
                socket = server.accept();
                System.out.println("Клиент подключился!");
                new ClientHandler(this, socket);
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
            AuthService.disconnect();
        } // Closing socket and server
    }

    public void broadcastMsg(String msg) {
        for (ClientHandler client: clients) {
            client.sendMsg(msg);
        }
    }

    public void subscribe(ClientHandler client){
        clients.add(client);
    }

    public void unsubscribe(ClientHandler client) {
        clients.remove(client);
    }

}

