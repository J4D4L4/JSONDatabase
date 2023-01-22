package server;


import server.Commands.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
    /*
       TextSingleDB dao = new TextSingleDB();
        while (true){
            String in[] = readInput();
            if(in != null) {
                if (in[0].equals("exit")) break;
                else {

                    interpretCommands(in, dao);

                }
            }
        }*/

        SimpleServer server = new SimpleServer();
        server.run();


    }

    public static String[] readInput(){
        Scanner scanner = new Scanner(System.in);
        String[] in = scanner.nextLine().split(" ");
        List<String> commands = getCommands();
        String[] returnStr = null;


        try {
            if(in[0].equals("exit")) return in;
            if (in.length < 1) {
                System.out.println("ERROR");
                returnStr = null;
            } else if (!commands.contains(in[0]) || ((Integer.parseInt(in[1]) >1000) || (Integer.parseInt(in[1])<0))) {
                System.out.println("ERROR");
                returnStr = null;
            }
            else {
                String inCOmmand = in[0];
                String id = in[1];
                String inputStr ="";
                if(in.length>2) {
                    for (int i = 2; i < in.length; i++) inputStr += " " + in[i];
                    returnStr = new String[]{inCOmmand, id, inputStr.substring(1)};
                }
                else returnStr = new String[]{inCOmmand, id, inputStr};
            }

        }
        catch (NumberFormatException e){
            System.out.println("ERROR");
            returnStr = null;
        }
        return returnStr;
    }

    static List<String> getCommands(){
        List<String> commands = new ArrayList<>();
        commands.add("set");
        commands.add("get");
        commands.add("delete");
        commands.add("exit");
        return commands;
    }

    static void interpretCommands(String in[], DataAccessObject dao){
        Controller controller = new Controller();
        BusinessObject bo = new SingleDB("-1","");
        Command command = new GetCommand(dao);
        try {
            switch (in[0]) {
                case "set":
                     bo = new SingleDB(in[1], in[2]);
                    command = new SetCommand(dao);

                    //dao.set(boS);
                    break;
                case "get":
                    bo = new SingleDB(in[1], "");
                    command = new GetCommand(dao);
                     //dao.get(boG);
                    break;
                case "delete":
                    bo = new SingleDB(in[1], "");
                    command = new DeleteCommand(dao);
                    //dao.delete(boD);
                    break;
                default:
                    break;
            }
            controller.setCommand(command);
            //BusinessObject returnObj = controller.executeCommand(bo);
        }
        catch (NumberFormatException e){
            System.out.println("ERROR");
        }
    }

    public String[] interpretServerCommands(String s){

        String[] in = s.split(" ");
        String command = in[s.indexOf("-t")+1];
        String index = in[s.indexOf("-i")+1];
        String message = "";
        for (int i = s.indexOf("-m")+1; i<s.length();i++){
            message += in[i];
        }
        String returnStr[] = {command,index,message};
        return returnStr;

    }


}