package ServerAndSocket;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import lombok.Getter;

import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter

class Port{
    private int port;
}

public class Server {
    private ServerSocket serverSocket = null;
    private Connection connection = null;

    public Server() {
        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/game_shop?useUnicode=true&serverTimezone=UTC", "root", "9132");
        }
        catch (SQLException  e ) {
            e.printStackTrace();
        }
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader("package.json"));
            Port data = gson.fromJson(reader, Port.class);

            serverSocket = new ServerSocket(data.getPort());
            System.out.println("Сервер запущен!" + serverSocket.getLocalSocketAddress());
        }
        catch (IOException e ) {
            e.printStackTrace();
        }
    }

    public void listen() {
        try {
            while (true) {
                System.out.println("В ожидании клиента!");
                Socket socket = serverSocket.accept();
                System.out.println("Клиент подключился: /" +socket.getInetAddress().getHostAddress());
                ClientHandler clientHandler = new ClientHandler(socket, connection);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.listen();
    }
}

