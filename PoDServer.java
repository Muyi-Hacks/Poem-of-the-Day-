

import java.io.*;
import java.net.*;
import java.util.*;

public class PoDServer {
    private static final int PORT = 12345; // define where the PoD server is running aka the port
    private static List<String> poems = new ArrayList<>(); //a list of all the poems available 

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java PoDServer <poemfile.txt>"); //opening the poems from the poemfile.txt (keep in mind if you want to test with a different file it most be named thsi)
            System.exit(1); //exits server after printing poem
        }

        loadPoems(args[0]);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("PoD Server is running on port " + PORT); //message telling user in terminal where the actual port is running
            while (true) {
                try (Socket clientSocket = serverSocket.accept(); // waits for telnet to connect to server
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); //how it will deliver poems
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) { //reading client input

                    out.println("Welcome to Poem of the Day!"); //welcome message
                    out.println("Available poems:");
                    for (int i = 0; i < poems.size(); i++) { // poem options being printed
                        out.printf("%d. %s%n", i + 1, poems.get(i).split("\\n")[0]);
                    }
                    out.println("Enter the number of the poem you want to read:");

                    String input = in.readLine();// getting user input
                    try {
                        int selection = Integer.parseInt(input);
                        if (selection >= 1 && selection <= poems.size()) {
                            out.println("Here is your poem:\n" + poems.get(selection - 1));
                        } else {
                            out.println("Invalid selection. Goodbye."); 
                        }
                    } catch (NumberFormatException e) {
                        out.println("Invalid input. Goodbye.");
                    }
                } catch (IOException e) {
                    System.err.println("Error handling client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Could not start server: " + e.getMessage());
        }
    }

    private static void loadPoems(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            StringBuilder poem = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) { //reading and printing each line of the poem for user to read
                if (line.trim().isEmpty()) {
                    if (poem.length() > 0) {
                        poems.add(poem.toString());
                        poem.setLength(0);
                    }
                } else {
                    poem.append(line).append("\n");
                }
            }
            if (poem.length() > 0) poems.add(poem.toString());
        } catch (IOException e) {
            System.err.println("Failed to load poems: " + e.getMessage());
            System.exit(1);
        }
    }
}
