package server;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface DataAccessObject {
    HashMap<String, BusinessObject> getAll();
    void update(BusinessObject bo);
    boolean delete(BusinessObject i);
    void set(BusinessObject bo);
    BusinessObject get(BusinessObject i);
    void setData();
    public DataSource ds = null;

    Person getPerson(BusinessObject bo);
}

class TextSingleDB implements DataAccessObject{


    TextSingleDB() throws IOException {
        ds = new DataSource();
        singleDBList = ds.getData();
    }

    HashMap<String,BusinessObject> singleDBList;
    public DataSource ds;
    @Override
    public HashMap<String,BusinessObject> getAll() {
        return singleDBList;
    }

    @Override
    public synchronized void update(BusinessObject bo) {
        if (singleDBList.get(bo.getId()) !=null) {
            singleDBList.get(bo.getId()).setName(bo.getName());
            System.out.println("OK");
            ds.updateFile();
        }
        else {
            System.out.println("Error");
        }
    }

    @Override
    public synchronized boolean delete(BusinessObject i) {
        if (singleDBList.get(i.getId()) !=null) {
            singleDBList.remove(i.getId());
            ds.updateFile();
            return true;
        }
        else {
            return false;
        }


    }

    @Override
    public synchronized void set(BusinessObject bo) {
        if (singleDBList.get(bo.getId()) ==null) {
            singleDBList.put(bo.getId(), bo);
            System.out.println("OK");
            ds.updateFile();
        }
        else {
            singleDBList.put(bo.getId(), bo);
            System.out.println("OK");
        }

    }

    @Override
    public synchronized BusinessObject get(BusinessObject i) {
        if (singleDBList.get(i.getId()) !=null) {
            //System.out.println(singleDBList.get(i.getId()).name);
            BusinessObject returnVal = singleDBList.get(i.getId());
            return returnVal;

        }
        else {
            //System.out.println("Error");
            //SimpleServer.sendMessage();
            return null;
        }
    }

    public Person getPerson(BusinessObject bo){
        Gson gson = new Gson();
        Person returnVal = gson.fromJson(gson.toJson(singleDBList.get(bo.getId())), Person.class);
        return returnVal;
    }

    @Override
    public void setData() {
        ds.setData(singleDBList);
    }
}