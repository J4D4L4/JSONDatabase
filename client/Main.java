package client;



public class Main {

    public static void main(String[] args) throws Exception {

        SimpleClient client = new SimpleClient();
        client.sendMsg(createMsg(args), client.dout);
        client.readMsg(client.din);
    }

    public static String createMsg(String[] s){
        String outMsg = "";
        for (String i : s){
            outMsg += " "+i;
        }
        return outMsg.substring(1);
    }


}
