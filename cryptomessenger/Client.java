package cryptomessenger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import static cryptomessenger.GUI.messagePrinter;

public class Client {
    private ConnectionToServer server;
    private LinkedBlockingQueue<Message> messages;
    private Socket socket;
    private int userId;

    public Client(int port) throws IOException {
        InetAddress host = InetAddress.getLocalHost();

        socket = new Socket(host.getHostAddress(), port);
        messages = new LinkedBlockingQueue<Message>();
        server = new ConnectionToServer(socket);

        Thread messageHandling = new Thread() {
            public void run() {
                while (true) {
                    try {
                        Message message = messages.take();
                        if (message.isAlive()) {
                            userId = message.getUserCount()-1;
                            messagePrinter(message);
                        }

                    } catch (InterruptedException e) {
                    }
                }
            }
        };

        messageHandling.setDaemon(true);
        messageHandling.start();
    }

    private class ConnectionToServer {
        ObjectInputStream in;
        ObjectOutputStream out;
        Socket socket;

        ConnectionToServer(Socket socket) throws IOException {
            this.socket = socket;
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());


            Thread read = new Thread() {
                public void run() {
                    boolean readFlag = true;
                    while (readFlag) {
                        try {
                            Message message = (Message) in.readObject();
                            if (!message.isAlive()) {
                                in.close();
                                out.close();
                                socket.close();
                                closeConnection();
                                readFlag = false;
                            }else{
                                messages.put(message);
                            }

                        } catch (IOException | ClassNotFoundException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

            read.setDaemon(true);
            read.start();
        }

        private void write(Message message) {
            try {
                out.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public void send(Message message) {
        message.setUserId(userId);
        server.write(message);
    }

    private void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}