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

    public void broadcastMsg(ClientHandler from, String msg) {
        for (ClientHandler client: clients) {
            if (!client.checkBlackList(from.getNick())) {
                client.sendMsg(msg);
            }
        }
    }

    public void privateMsg(ClientHandler from, String _addressee, String _msg){
        for (ClientHandler o : clients) {
            if (o.getNick().equals(_addressee)) {
                o.sendMsg("from " + from.getNick() + ": " + _msg);
                from.sendMsg("to " + _addressee + ": " + _msg);
                return;
            }
        }
        from.sendMsg("Пользователя " + _addressee + " нет в чате");
    }

    public void subscribe(ClientHandler client){
        clients.add(client);
    }

    public void unsubscribe(ClientHandler client) {
        clients.remove(client);
    }

}

