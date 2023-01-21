package server;

import server.Commands.*;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleServer {
    String address = "127.0.0.1";;
    static ServerSocket ss;
    static Socket s;
    static DataInputStream din;
    static  DataOutputStream dout;

    public SimpleServer() throws IOException {
        ss = new ServerSocket(23456, 50, InetAddress.getByName(address));
        System.out.println("Server started!");
        //s = ss.accept();
        //din=new DataInputStream(s.getInputStream());
        //dout=new DataOutputStream(s.getOutputStream());

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

    public void run( ) throws Exception{
        TextSingleDB dao = new TextSingleDB();
        while (true) {



            s = ss.accept();
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());



            String str = readMsg(this.din);
            if(checkIfInputLeagel(str)){
                String interpretedMessage[] = interpretServerCommands(str);
                interpretCommands(interpretedMessage,dao);
            }
            else {
                sendMessage(dout, "Error");
                System.out.println("Run: ERROR");
            }
        }


    }

    public static String  readMsg(DataInputStream in){
        try {
            String inStr = in.readUTF();
            //System.out.println("Received: "+inStr);
            return inStr;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void sendMessage(DataOutputStream out, String message){
        try {
            out.writeUTF(message);
            out.flush();
            System.out.println("Server Sent: "+message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String[] interpretServerCommands(String s){

        List<String> in = new ArrayList<>();
        in = Arrays.asList(s.split(" "));

        String command = in.get(in.indexOf("-t")+1);
        String index = in.get(in.indexOf("-i")+1);
        String message = "";
        if(s.indexOf("-m") != -1) {
            for (int i = in.indexOf("-m") + 1; i < in.size(); i++) {
                message += " "+in.get(i);
            }
        }
        //if (message.length()!=0) message = message.substring(1);
        String returnStr[] = {command,index,message};
        return returnStr;

    }

    static void interpretCommands(String in[], DataAccessObject dao){
        Controller controller = new Controller();
        BusinessObject bo = new SingleDB(-1,"");
        Command command = new GetCommand(dao);
        try {
            switch (in[0]) {
                case "set":
                    bo = new SingleDB(Integer.parseInt(in[1]), in[2]);
                    command = new SetCommand(dao);
                    sendMessage(dout, "OK");
                    //dao.set(boS);
                    break;
                case "get":
                    bo = new SingleDB(Integer.parseInt(in[1]), "");
                    command = new GetCommand(dao);

                    controller.setCommand(command);
                    BusinessObject ba = command.execute(bo);
                    if(ba == null){
                        sendMessage(dout, "ERROR");
                    }
                    else sendMessage(dout, ba.getName());
                    //dao.get(boG);
                    break;
                case "delete":
                    bo = new SingleDB(Integer.parseInt(in[1]), "");
                    command = new DeleteCommand(dao);
                    sendMessage(dout, "OK");
                    //dao.delete(boD);
                    break;
                case "exit":
                    bo = new SingleDB(0, "");
                    sendMessage(dout, "OK");
                    command = new ExitCommand(dao);
                    closeServer();
                    //dao.delete(boD);
                    break;
                default:
                    break;
            }
            controller.setCommand(command);
            controller.executeCommand(bo);
        }
        catch (NumberFormatException e){
            sendMessage(dout, "Error");
            System.out.println("Interpret: ERROR");
        }
    }

    public static boolean checkIfInputLeagel(String s){
        boolean isLeagal = true;
        List<String> input = new ArrayList<>();
        input = Arrays.asList(s.split(" "));
        // check if correct min length and if commands are present
        if((s.length()<1 || (input.indexOf("-t") == -1))){
            isLeagal = false;
        }
        // check if commands are followed by input
        if (input.get(input.indexOf("-t")+1).equals("-i") || input.get(input.indexOf("-i")+1).equals("-m")){
            isLeagal = false;
        }

        if(!getCommands().contains(input.get(input.indexOf("-t")+1))){
            isLeagal = false;
        }
        if(input.size()>2){
            if(Integer.parseInt(input.get(3))>1000 || Integer.parseInt(input.get(3))<0)
                isLeagal = false;
        }
        return isLeagal;
    }

    static List<String> getCommands(){
        List<String> commands = new ArrayList<>();
        commands.add("set");
        commands.add("get");
        commands.add("delete");
        commands.add("exit");
        return commands;
    }
    public static void closeServer(){
        try {
            din.close();
            s.close();
            ss.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
