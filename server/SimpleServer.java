package server;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
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
    static Gson gson = new Gson();

    public SimpleServer() throws IOException {
        ss = new ServerSocket(23456, 50, InetAddress.getByName(address));
        System.out.println("Server started!");

    }
    public void test()throws Exception{
        //BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

        String str="",str2="";
        //while(!str.equals("stop")){
        //str=readMsg(this.din);

        //sendMessage(dout,"A record # "+str.split(" ")[str.split(" ").length-1] +" was sent!");
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



            Request str = readMsg(this.din);
            if(checkIfInputLeagel(str)){
                //String interpretedMessage[] = interpretServerCommands(str);
                interpretCommands(str,dao);
            }
            else {
                Response errorResponse = new Response("ERROR");
                sendMessage(dout, errorResponse);
                //System.out.println("Run: ERROR");
            }
            s.close();
        }


    }

    public static Request  readMsg(DataInputStream in){
        try {
            Request request = gson.fromJson(in.readUTF(),Request.class);
            System.out.println("Received: "+gson.toJson(request));
            return request;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void sendMessage(DataOutputStream out,Response response){
        try {
            //Response response = new Response(responseType,message);
            String outMessage = gson.toJson(response);
            out.writeUTF(outMessage);
            out.flush();
            System.out.println("Sent:"+outMessage);
            s.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /*public String[] interpretServerCommands(Request s){

        //List<String> in = new ArrayList<>();
        //in = Arrays.asList(s.split(" "));

        String command = s.type;
        String index = s.key;
        String message = "";
        if(s.value != null) {
            for (int i = in.indexOf("-m") + 1; i < in.size(); i++) {
                message += " "+in.get(i);
            }
        }
        //if (message.length()!=0) message = message.substring(1);
        String returnStr[] = {command,index,message};
        return returnStr;

    }*/

    static void interpretCommands(Request in, DataAccessObject dao){
        Controller controller = new Controller();
        BusinessObject bo = new SingleDB("-1","");
        Command command = new GetCommand(dao);
        Response okResponse = new Response("OK");
        Response errorResponse = new Response("error");
        try {
            switch (in.type) {
                case "set":
                    try {
                        bo = new SingleDB(in.key, in.value);
                        command = new SetCommand(dao);
                        sendMessage(dout, okResponse);
                    }
                    catch (NumberFormatException e){
                        sendMessage(dout, new ErrorResponse("ERROR","No such key"));
                    }

                    //dao.set(boS);
                    break;
                case "get":
                    bo = new SingleDB(in.key, "");
                    command = new GetCommand(dao);
                    BusinessObject ba = command.execute(bo);
                    if(ba == null){
                        sendMessage(dout, new ErrorResponse("ERROR","No such key"));
                    }

                    else{
                        Response itemResponse = new GoodResponse("OK", ba.getName());
                    sendMessage(dout, itemResponse);
                }
                    //dao.get(boG);
                    break;
                case "delete":
                    bo = new SingleDB(in.key, "");
                    command = new DeleteCommand(dao);

                    BusinessObject bD = command.execute(bo);
                    if(bD == null) sendMessage(dout, new ErrorResponse("ERROR","No such key"));
                    else sendMessage(dout, okResponse);
                    //dao.delete(boD);
                    break;
                case "exit":
                    bo = new SingleDB("0", "");
                    sendMessage(dout, okResponse);
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
            sendMessage(dout, new ErrorResponse("ERROR","Wrong Format"));
            System.out.println("Interpret: ERROR");
        }
        catch (JsonParseException e){
            sendMessage(dout, new ErrorResponse("ERROR","Wrong Format"));
            System.out.println("Interpret: ERROR");
        }
    }

    public static boolean checkIfInputLeagel(Request s){
        boolean isLeagal = true;

        //List<String> input = new ArrayList<>();
        //input = Arrays.asList(s.split(" "));
        // check if correct min length and if commands are present
        if(((s.type == null))){
            isLeagal = false;
        }
        // check if commands are followed by input
        if(!getCommands().contains(s.type)){
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
