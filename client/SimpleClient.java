package client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class SimpleClient {
    String address = "127.0.0.1";
    Socket s;
    DataInputStream din;
    DataOutputStream dout;

    SimpleClient() throws IOException {
        System.out.println("Client started!");
        s=new Socket(InetAddress.getByName(address),23456);
        din=new DataInputStream(s.getInputStream());
        dout=new DataOutputStream(s.getOutputStream());

    }
    public void test()throws Exception{

        //BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

        String str="",str2="";
        sendMsg("Give me a record # 12", dout);
        str2=readMsg(din);

    }

    public static void sendMsg(String msg, DataOutputStream out){
        try {
            out.writeUTF(msg);
            out.flush();
            System.out.println("Send: "+msg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readMsg(DataInputStream in){
        String inStr = null;
        try {
            inStr = in.readUTF();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Received: "+inStr);
        return inStr;
    }
}
