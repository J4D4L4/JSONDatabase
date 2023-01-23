package client;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import server.BusinessObject;
import server.Request;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main {
    static Gson gson = new Gson();
    public static void main(String[] args) throws Exception {

        SimpleClient client = new SimpleClient(args);
        client.start();
        client.join(1000);
        //client.sendMsg(createMsg(args), client.dout);
        //client.readMsg(client.din);
        //System.exit(0);

    }

    public static String createMsg(String[] s){
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
                    request = new Request(type,gson.toJsonTree(index),gson.toJsonTree(value));
                }
                else request = new Request(type,gson.toJsonTree(index));
            }
            else request = new Request(type);
        }

        outMsg = new Gson().toJson(request);

        return outMsg;
    }
    public List<Request> readFile(String path) throws FileNotFoundException {
        Gson gson = new Gson();
        List<Request> requests= new ArrayList<>();
        File myObj = new File(path);
        JsonReader reader = new JsonReader(new FileReader(myObj));
        Type typeOfHashMap = new TypeToken<HashMap<String, BusinessObject>>() { }.getType();
        requests = gson.fromJson(reader, Request.class);
        return requests;
    }

}
