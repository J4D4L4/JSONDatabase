package client;

import server.ProtocollInterface;
import server.BusinessObject;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class User implements Serializable, ProtocollInterface<BusinessObject, String> {

    User(){

    }
    private static class UserHolder{
        private static final User Instance = new User();
    }
    @Override
    public void writeMessage(Socket targetObject, BusinessObject message) {
        try {
            System.out.printf("Give me a record # %d,%n",message.getId());
            //DataInputStream input = new DataInputStream(targetObject.getInputStream());
            DataOutputStream output = new DataOutputStream(targetObject.getOutputStream());
            output.writeUTF("Give me a record # "+message.getId());
            output.flush();
        } catch (IOException e) {
            System.err.println("Error at sending to server");
        }
    }

    @Override
    public String readMessage(Socket targetObject) {
        return null;
    }

    //@Override
    public String readMessage(Socket targetObject, DataInputStream input) {
        try {
            //System.out.println("Reading from server...");
            //ObjectInputStream objectInputStream = new ObjectInputStream(targetObject.getInputStream());

            //DataInputStream input = new DataInputStream(targetObject.getInputStream());
            //DataOutputStream output = new DataOutputStream(targetObject.getOutputStream());

            String serverResponse = input.readUTF();
            System.out.println("Received: "+serverResponse);
            return serverResponse;
        } catch (IOException e) {
            System.err.println("Error at connecting to server");
            return ""+0;
        }
    }

    public void connectingToServer(int portNumber, String address, BusinessObject bo) {
        System.out.println("Trying to connect with server...");
        Socket server;
        try {
            server = new Socket(InetAddress.getByName(address), portNumber); //create the connection to server
            System.out.println("Found a server");
            DataInputStream input = new DataInputStream(server.getInputStream());
            DataOutputStream output = new DataOutputStream(server.getOutputStream());
            this.writeMessage(server, bo);

            String discountedPriceString = this.readMessage(server, input);

            //server.close(); //close the connection to server
        } catch (IOException e) {
            System.err.println("Error at connecting to server");
        }
        //System.out.println("Connection terminated");
    }
}
