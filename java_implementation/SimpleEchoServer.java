import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleEchoServer {
    public static void main(String[] args) {
        int port = 12345; // Port number on which the server will listen

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Echo server started on port " + port);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        System.out.println("Received: " + inputLine);
                        out.println(inputLine); // Echo the received message
                    }

                    System.out.println("Client disconnected.");
                } catch (Exception e) {
                    System.err.println("Error in connection handling: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Server exception: " + e.getMessage());
        }
    }
}
