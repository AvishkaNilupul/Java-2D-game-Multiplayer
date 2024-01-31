import java.io.*;
import java.net.*;

public class ChatServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server waiting for clients...");

            // Accept first client
            Socket clientSocket1 = serverSocket.accept();
            System.out.println("First client connected.");

            // Accept second client
            Socket clientSocket2 = serverSocket.accept();
            System.out.println("Second client connected.");

            // Create input/output streams for client 1
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(clientSocket1.getInputStream()));
            PrintWriter writer1 = new PrintWriter(clientSocket1.getOutputStream(), true);

            // Create input/output streams for client 2
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(clientSocket2.getInputStream()));
            PrintWriter writer2 = new PrintWriter(clientSocket2.getOutputStream(), true);

            // Start a thread for client 1 to listen for messages
            new Thread(() -> {
                try {
                    String line;
                    while ((line = reader1.readLine()) != null) {
                        System.out.println("Client 1: " + line);
                        writer2.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Start a thread for client 2 to listen for messages
            new Thread(() -> {
                try {
                    String line;
                    while ((line = reader2.readLine()) != null) {
                        System.out.println("Client 2: " + line);
                        writer1.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
