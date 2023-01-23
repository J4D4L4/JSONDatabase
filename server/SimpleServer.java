package server;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.internal.LinkedTreeMap;
import server.Commands.*;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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


            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.submit(()->{
                Request str = readMsg(this.din);
                System.out.println(str);
                if(checkIfInputLeagel(str)){
                    //String interpretedMessage[] = interpretServerCommands(str);
                    interpretCommands(str,dao);
                }
                else {
                    Response errorResponse = new Response("ERROR");
                    sendMessage(dout, errorResponse);
                    //System.out.println("Run: ERROR");
                }
                try {
                    s.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            executor.shutdown();

            boolean terminated = executor.awaitTermination(60, TimeUnit.MILLISECONDS);
        }


    }

    public static Request  readMsg(DataInputStream in) {
        try {
            //Request request = gson.fromJson(in.readUTF(),Request.class);
            Map<String, String> map = gson.fromJson(in.readUTF(), Map.class);

            Request request = new Request("", gson.toJsonTree(""), gson.toJsonTree(""));
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();

                if (key.equals("type")) {
                    request.type = entry.getValue();
                }
                if (key.equals("key")) {
                    request.key = gson.toJsonTree(entry.getValue());

                }
                if (key.equals("value")) {

                    JsonElement jsonValue = gson.toJsonTree(entry.getValue());
                    if((jsonValue.isJsonObject() )) {
                        Person person = gson.fromJson(jsonValue, Person.class);
                        request.value = gson.toJsonTree(person);
                    }
                    else request.value = gson.toJsonTree(entry.getValue());
                }
            }

            System.out.println("Received: " + gson.toJson(request));
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
                        JsonElement jsonIN = in.value;


                        if(!(gson.fromJson(jsonIN,Object.class).getClass() == LinkedTreeMap.class)) {
                            bo = new SingleDB(gson.fromJson(in.key,String.class), gson.fromJson(in.value, String.class));
                        }//Tested for json element
                        else bo = gson.fromJson(in.value, Person.class);
                        bo.key = (gson.fromJson(in.key,String.class));
                        command = new SetCommand(dao);
                        sendMessage(dout, okResponse);
                    }
                    catch (NumberFormatException e){
                        sendMessage(dout, new ErrorResponse("ERROR","No such key"));
                    }

                    //dao.set(boS);
                    break;
                case "get":
                    List<String> getElements = new ArrayList<>();
                    Response itemResponse;
                    BusinessObject ba;
                    if((gson.fromJson(in.key,Object.class).getClass() == ArrayList.class)) {
                        for(JsonElement json: in.key.getAsJsonArray()){
                            getElements.add(gson.fromJson(json,String.class));
                        }
                        bo = new Person(getElements.get(0),"");
                        ba = dao.getPerson(bo);

                    }
                    else {
                        bo = new SingleDB(gson.fromJson(in.key, String.class), "");
                        ba = command.execute(bo);
                    }
                    command = new GetCommand(dao);
                    //

                    if(ba == null){
                        sendMessage(dout, new ErrorResponse("ERROR","No such key"));
                    } else if (ba instanceof Person) {
                        String personJson = gson.toJson(ba);
                        itemResponse = new GoodResponse("OK", gson.fromJson(personJson, JsonObject.class).get(getElements.get(1)).getAsString());

                    } else{
                        itemResponse = new GoodResponse("OK", ba.getName());
                    sendMessage(dout, itemResponse);
                }
                    //dao.get(boG);
                    break;
                case "delete":
                    bo = new SingleDB(gson.fromJson(in.key,String.class), "");
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
