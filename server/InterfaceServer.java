package server;

import java.net.ServerSocket;

public interface InterfaceServer {
    ServerSocket getServerSocket();
    int getServerPortNumber();
    void setServerSocket(ServerSocket serverSocket);
    void setServerPortNumber(int serverPortNumber);
    void setServerSet(boolean isServerSet);
    boolean isServerSet();
}