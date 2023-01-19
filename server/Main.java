package server;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        TextSingleDB dao = new TextSingleDB();
        while (true){
            String in[] = readInput();
            if(in != null) {
                if (in[0].equals("exit")) break;
                else {

                    interpretCommands(in, dao);

                }
            }
        }
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
            } else if (!commands.contains(in[0]) || ((Integer.parseInt(in[1]) >100) || (Integer.parseInt(in[1])<0))) {
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
        try {
            switch (in[0]) {
                case "set":
                    BusinessObject bo = new SingleDB(Integer.parseInt(in[1]), in[2]);
                    dao.add(bo);
                    break;
                case "get":
                    dao.get(Integer.parseInt(in[1]));
                    break;
                case "delete":
                    dao.delete(Integer.parseInt(in[1]));
                    break;
                default:
                    break;
            }
        }
        catch (NumberFormatException e){
            System.out.println("ERROR");
        }
    }


}
