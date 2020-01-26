package Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.*;

public class ChatServer {
    private Vector<ClientHandler> clients;
    private static final Logger logger = Logger.getLogger("");

    public ChatServer() {
        Handler handler = null;
        try {
            handler = new FileHandler("Server.log",true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        handler.setLevel(Level.ALL);
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);

        clients = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;

        try {
            AuthService.connect();
            server = new ServerSocket(8124);
            System.out.println("Сервер запустился!");
            logger.log(Level.INFO, "Сервер запустился!");
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
                logger.log(Level.SEVERE, "Не удалось закрыть сокет на стороне сервера.");
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Не удалось закрыть сервер.");
                logger.log(Level.SEVERE, "Не удалось закрыть сервер.");
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
        broadcastClientList();
    }

    public void unsubscribe(ClientHandler client) {
        clients.remove(client);
        broadcastClientList();
    }

    public void broadcastClientList(){
        StringBuilder sb = new StringBuilder();
        sb.append("/clientlist ");
        for(ClientHandler o : clients){
            sb.append(o.getNick() + " ");
        }
        String out = sb.toString();
        for(ClientHandler o : clients){
            o.sendMsg(out);
        }
    }

}

