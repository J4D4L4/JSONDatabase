package server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class SimpleServer {
    String address = "127.0.0.1";;
    ServerSocket ss;
    Socket s;
    DataInputStream din;
    DataOutputStream dout;

    SimpleServer() throws IOException {
        ss = new ServerSocket(23456, 50, InetAddress.getByName(address));
        System.out.println("Server started!");
        s = ss.accept();
        din=new DataInputStream(s.getInputStream());
        dout=new DataOutputStream(s.getOutputStream());

    }
    public void test()throws Exception{
        //BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

        String str="",str2="";
        //while(!str.equals("stop")){
        str=readMsg(this.din);

        sendMessage(dout,"A record # "+str.split(" ")[str.split(" ").length-1] +" was sent!");
        dout.flush();
        //}
        din.close();
        s.close();
        ss.close();
    }

    public static String  readMsg(DataInputStream in){
        try {
            String inStr = in.readUTF();
            System.out.println("Received: "+inStr);
            return inStr;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void sendMessage(DataOutputStream out, String message){
        try {
            out.writeUTF(message);
            out.flush();
            System.out.println("Sent: "+message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
