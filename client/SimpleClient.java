package client;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import server.BusinessObject;
import server.Person;
import server.Request;
import server.SingleDB;

import java.io.*;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleClient extends Thread{
    String address = "127.0.0.1";
    static Socket s;
    DataInputStream din;
    DataOutputStream dout;
    String message;
    static List<Request> requests = new ArrayList<>();
    static Gson gson = new Gson();
    String filePath ="./src/client/data/";

    public SimpleClient(String msg) throws IOException  {
        System.out.println("Client started!");
        s=new Socket(InetAddress.getByName(address),23456);
        din=new DataInputStream(s.getInputStream());
        dout=new DataOutputStream(s.getOutputStream());
        message = msg;



    }

    public SimpleClient(String[] args) throws IOException  {
        System.out.println("Client started!");
        s=new Socket(InetAddress.getByName(address),23456);
        din=new DataInputStream(s.getInputStream());
        dout=new DataOutputStream(s.getOutputStream());

        List<String> in = Arrays.asList(args);
        if(in.indexOf("-in") != -1){
            List<Request> dataRequest= readFile(in.get(in.indexOf("-in")+1));
            for (Request r: dataRequest) {
                requests.add(r);
            }
        }
        else requests.add(createMsg(args));


    }

    @Override
    public void run(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        for (Request r : requests){

                sendMsg(r, dout);
                try {
                    readMsg(din);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

        }


    }
    public void test()throws Exception{

        //BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

        String str="",str2="";
        //sendMsg("Give me a record # 12", dout);
        str2=readMsg(din);

    }

    public static void sendMsg(Request msg, DataOutputStream out){
        try {
            out.writeUTF(gson.toJson(msg));
            out.flush();
            System.out.println("Sent:"+gson.toJson(msg));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readMsg(DataInputStream in) throws IOException {
        String inStr = null;
        try {
            inStr = in.readUTF();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Client Received: "+inStr);
        s.close();
        return inStr;

    }

    public List<Request> readFile(String path) throws IOException {
        List<Request> requests= new ArrayList<>();
        File myObj = new File(filePath+path);
        JsonReader reader = new JsonReader(new FileReader(myObj));
        Type typeOfHashMap = new TypeToken<HashMap<String, BusinessObject>>() { }.getType();
        Map<String, String> map = gson.fromJson(reader,Map.class);
        reader.close();
        Request request = new Request("", gson.toJsonTree(""));
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();

            if(key.equals("type")) {
                request.type = entry.getValue();
            }
            if(key.equals("key")) {
                String jsonValueKey = gson.toJson(entry.getValue());
                if(!(gson.fromJson(jsonValueKey,Object.class).getClass() == ArrayList.class)) {
                    request.key = gson.toJsonTree(entry.getValue());
                }
                else request.key = gson.toJsonTree(entry.getValue());
            }
            if(key.equals("value")) {
                String jsonValue = gson.toJson(entry.getValue());
                if(jsonValue.indexOf("{") == -1)
                    request.value = gson.toJsonTree(entry.getValue());
                else if(!(gson.fromJson(jsonValue,Object.class).getClass() == LinkedTreeMap.class)) {
                    Person person = gson.fromJson(jsonValue, Person.class);
                    request.value =  gson.toJsonTree(person);
                }
                else request.value = gson.toJsonTree(entry.getValue());

            }

            //data.put(entry.getKey(),bo);
            //data.put(entry.getKey(), value);
        }
        requests.add(request);
        return requests;
    }


    public static Request createMsg(String[] s){
        List<String> in = Arrays.asList(s);
        String index;
        String value;
        String type;
        Request request = new Request(null,null);;
        String outMsg;


        if(in.indexOf("-t") != -1) {
            type = in.get(in.indexOf("-t")+1);

            if (in.indexOf("-k") != -1) {
                index = in.get(in.indexOf("-k")+1);

                if (in.indexOf("-v") != -1) {
                    value = in.get(in.indexOf("-v")+1);
                    request = new Request(type, gson.toJsonTree(index),gson.toJsonTree(value));
                }
                else request = new Request(type,gson.toJsonTree(index));
            }
            else request = new Request(type);
        }



        return request;
    }
}
