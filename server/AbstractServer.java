package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class AbstractServer implements InterfaceServer, Serializable, ProtocollInterface<String,BusinessObject>{

    private ServerSocket serverSocket;
    private int serverPortNumber;
    @Override
    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    @Override
    public int getServerPortNumber() {
        return serverPortNumber;
    }

    @Override
    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket=serverSocket;
    }

    @Override
    public void setServerPortNumber(int serverPortNumber) {
        this.serverPortNumber = serverPortNumber;
    }

    @Override
    public void setServerSet(boolean isServerSet) {

    }

    @Override
    public boolean isServerSet() {
        return false;
    }

    @Override
    public void writeMessage(Socket targetObject, String message) {
        try {
            //System.out.println("Writing to client...");
            ObjectOutputStream outputStream = new ObjectOutputStream(new PrintStream(targetObject.getOutputStream()));
            outputStream.writeUTF(message);
            System.out.printf("Sent: A record # %s was sent!%n",message.split(" ")[message.split(" ").length-1]);
            outputStream.flush();
        } catch (IOException e) {
            System.err.println("Error at connecting to client");
        }
        System.out.println("Connection terminated.");
    }


    @Override
    public String readMessage(Socket targetObject) {
        DataInputStream input = null;
        DataOutputStream output = null;
        try {
            input = new DataInputStream(targetObject.getInputStream());
            output = new DataOutputStream(targetObject.getOutputStream());
            String msg = input.readUTF();
            System.out.println("Received: "+msg);
            String sent = " A record # "+msg.split(" ")[msg.split(" ").length-1]+" was sent!";
            writeMessage(targetObject,msg);
            output.writeUTF(sent);
            //System.out.println("Sent: "+sent);
            return msg;
        }catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void connectingToClient() {
        while(this.getServerSocket() != null) {
            //System.out.println("Waiting for the client...");
            Socket client;
            try {
                while (true) {
                    client = this.getServerSocket().accept(); //wait for the request from client
                    System.out.println("Found a client");
                    String inMessage = this.readMessage(client);
                    //int discountedPrice = calculateDiscountedPrice(vehicle);
                    this.writeMessage(client, inMessage);
                    client.close();
                }
            } catch (IOException e) {
                System.err.println("Error at connecting to client");
            }
        }
        //System.out.println("Connection terminated.");
    }
}
