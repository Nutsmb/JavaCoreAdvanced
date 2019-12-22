package Client;

import Server.ClientHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Controller{
    @FXML
    TextArea textArea;

    @FXML
    TextField textField;

    @FXML
    Button sendBtn;

    Socket socket;
    DataInputStream inputStream;
    DataOutputStream outputStream;

    final String IP_ADRESS = "localhost";
    final int PORT = 8124;

    boolean isAuthorized = false;
    String nick;
    String privateMsg;

    @FXML
    HBox upperPanel;

    @FXML
    HBox bottomPanel;

    @FXML
    TextField loginField;

    @FXML
    TextField passwordField;

    @FXML
    TextField nicknameField;

    @FXML
    Button authBtn;

    @FXML
    Button regBtn;

    public void setAuthorised(boolean isAuthorized){
        this.isAuthorized = isAuthorized;
        if (!isAuthorized){
            upperPanel.setVisible(true);
            upperPanel.setManaged(true);
            bottomPanel.setVisible(false);
            bottomPanel.setManaged(false);
        }
        else {
            upperPanel.setVisible(false);
            upperPanel.setManaged(false);
            bottomPanel.setVisible(true);
            bottomPanel.setManaged(true);
        }
    }

    public void connect() {
        try {
            socket = new Socket(IP_ADRESS, PORT);
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true){
                            String str = inputStream.readUTF();
                            if(str.startsWith("/authOK")){
                                String[] token = str.split(" ");
                                nick = token[1];
                                setAuthorised(true);
                                break;
                            }
                            else {
                                textArea.appendText(str + "\n");
                            }
                        }

                        while (true){
                            String str = inputStream.readUTF();
                            if(str.equals("/serverClosed")){
                                break;
                            } else {
                                textArea.appendText(str + "\n");
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("Не удалось закрыть сокет на стороне клиенте!");
                        }
                        setAuthorised(false);
                    } // Closing socket
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Не удалось поднять сокет на стороне клиенте!");
        }
    }

    public void sendMsg() {
        try {
            outputStream.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tryToAuth() {
        if(socket == null || socket.isClosed()){
            connect();
        }
        try {
            outputStream.writeUTF("/auth " + loginField.getText() + " " + passwordField.getText());
            loginField.clear();
            passwordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tryToSignup(){
        if(socket == null || socket.isClosed()){
            connect();
        }
        try {
            outputStream.writeUTF("/regis " + loginField.getText() + " " + passwordField.getText() + " " + nicknameField.getText());
            loginField.clear();
            passwordField.clear();
            nicknameField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
