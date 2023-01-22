package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface DataAccessObject {
    HashMap<Integer, BusinessObject> getAll();
    void update(BusinessObject bo);
    void delete(BusinessObject i);
    void set(BusinessObject bo);
    BusinessObject get(BusinessObject i);
    void setData();

}

class TextSingleDB implements DataAccessObject{


    TextSingleDB(){
        ds = new DataSource();
        singleDBList = ds.getData();
    }

    HashMap<Integer,BusinessObject> singleDBList;
    DataSource ds;
    @Override
    public HashMap<Integer,BusinessObject> getAll() {
        return singleDBList;
    }

    @Override
    public void update(BusinessObject bo) {
        if (singleDBList.get(bo.getId()) !=null) {
            singleDBList.get(bo.getId()).setName(bo.getName());
            System.out.println("OK");
        }
        else {
            System.out.println("Error");
        }
    }

    @Override
    public void delete(BusinessObject i) {
        if (singleDBList.get(i.getId()) !=null) {
            singleDBList.remove(i.getId());
            System.out.println("OK");
        }
        else {
            System.out.println("OK");
        }


    }

    @Override
    public void set(BusinessObject bo) {
        if (singleDBList.get(bo.getId()) ==null) {
            singleDBList.put(bo.getId(), bo);
            System.out.println("OK");
        }
        else {
            singleDBList.put(bo.getId(), bo);
            System.out.println("OK");
        }

    }

    @Override
    public BusinessObject get(BusinessObject i) {
        if (singleDBList.get(i.getId()) !=null) {
            //System.out.println(singleDBList.get(i.getId()).name);
            BusinessObject returnVal = singleDBList.get(i.getId());
            return returnVal;

        }
        else {
            System.out.println("Error");
            //SimpleServer.sendMessage();
            return null;
        }
    }

    @Override
    public void setData() {
        ds.setData(singleDBList);
    }
}