package client;


import com.google.gson.Gson;
import server.Request;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        SimpleClient client = new SimpleClient(createMsg(args));
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
                    request = new Request(type,index,value);
                }
                else request = new Request(type,index);
            }
            else request = new Request(type);
        }

        outMsg = new Gson().toJson(request);

        return outMsg;
    }


}
