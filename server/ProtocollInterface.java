package server;


import java.net.Socket;

public interface ProtocollInterface<U, V> {
    void writeMessage(Socket targetObject, U message);
    String readMessage(Socket targetObject);
}