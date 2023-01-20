package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class SingleDBServer  extends  AbstractServer{
    private boolean isServerSet;
    public SingleDBServer(){
        if(!this.isServerSet()){
            try {
                int serverPortNumber = 23456;
                String address = "127.0.0.1";
                this.setServerSocket(new ServerSocket(serverPortNumber, 50, InetAddress.getByName(address)));
                this.setServerPortNumber(serverPortNumber); //save the port number
                this.setServerSet(true);
                System.out.println("Server started!");
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean isServerSet() {
        return isServerSet;
    }

    @Override
    public void setServerSet(boolean serverSet) {
        isServerSet = serverSet;
    }
}
