package cryptomessenger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {

    static private int port = 8080;
    private ArrayList<ConnectionToClient> clientList;
    private LinkedBlockingQueue<Message> messages;
    private ServerSocket serverSocket;


    public Server(int port) {
        clientList = new ArrayList<ConnectionToClient>();
        messages = new LinkedBlockingQueue<Message>();
        try {
            serverSocket = new ServerSocket(port);

        } catch (IOException e) {
            System.out.println("Server can't created there is an error on port : " + port);
        }


        Thread accept = new Thread() {
            public void run() {

                while (true) {
                    try {
                        Socket s = serverSocket.accept();
                        clientList.add(new ConnectionToClient(s));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        accept.setDaemon(true);
        accept.start();

    }

    private class ConnectionToClient {
        ObjectInputStream in;
        ObjectOutputStream out;
        Socket socket;

        ConnectionToClient(Socket socket) throws IOException {
            this.socket = socket;
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            Thread read = new Thread() {
                public void run() {
                    boolean connectionFlag = true;
                    while (connectionFlag) {
                        try {
                            Message message = (Message) in.readObject();
                            if (message.isAlive()) {
                                message.setUserCount(clientList.size());
                                messages.put(message);
                                sendToAll(message);
                            } else {
                                clientRemove(message);
                                connectionFlag = false;
                            }

                        } catch (IOException | ClassNotFoundException | InterruptedException e) {
                            System.out.println("There is an interruption error please restart the server and connections");
                            e.printStackTrace();
                        }
                    }
                }
            };

            read.setDaemon(true); // terminate when main ends
            read.start();
        }

        public void write(Message message) {
            try {
                out.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void clientRemove(Message message) {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            clientList.remove(message.getUserId());
        }
    }


    public void sendToAll(Message message) {
        for (ConnectionToClient client : clientList)
            client.write(message);
    }


    public static void main(String[] args) throws IOException {

        Server server = new Server(port);
        while (true) {
            int a = 0;
        }


    }

}