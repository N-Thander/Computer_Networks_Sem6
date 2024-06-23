import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SimpleEchoClient {
    public static void main(String[] args) {
        String serverAddress = "localhost"; // Server address
        int port = 12345; // Server port number

        try (Socket socket = new Socket(serverAddress, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to echo server on port " + port);
            String userInput;

            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput); // Send user input to server
                String response = in.readLine(); // Read the response from the server
                System.out.println("Echo from server: " + response);
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.getMessage());
        }
    }
}
