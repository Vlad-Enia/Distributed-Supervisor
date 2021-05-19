import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Server {

    public static final int PORT = 8101;

    static private boolean running = true;
    static private boolean shouldShutDown = false;
    static public volatile int threadCount = 0;

    private static ServerSocket serverSocket = null;

    static public void stopServer() {
        System.out.println("Server was stopped by a client");
        System.exit(0);
    }

    static public void stopServerGracefully() {
        System.out.println("Server initiated by a client gracefully");
        shouldShutDown = true;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Server() throws IOException {
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                System.out.println("Waiting for a client ...");
                Socket socket = serverSocket.accept();
                new ClientThread(socket).start();
                threadCount++;
            }
        } catch (IOException | SQLException e) {
            System.err.println("The server doesn't accept new connections. It will shut down soon... ");

        } finally {
            serverSocket.close();
        }
        System.out.println("Number of threads: " + threadCount);
        while (threadCount > 0) {
            Thread.onSpinWait();
        }
        System.out.println("Number of threads: " + threadCount);
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
    }
}
