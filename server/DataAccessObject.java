package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

interface DataAccessObject {
    HashMap<Integer, BusinessObject> getAll();
    void update(BusinessObject bo);
    void delete(int i);
    void add(BusinessObject bo);
    BusinessObject get(int i);
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
    public void delete(int i) {
        if (singleDBList.get(i) !=null) {
            singleDBList.remove(i);
            System.out.println("OK");
        }
        else {
            System.out.println("OK");
        }


    }

    @Override
    public void add(BusinessObject bo) {
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
    public BusinessObject get(int i) {
        if (singleDBList.get(i) !=null) {
            System.out.println(singleDBList.get(i).name);
            return singleDBList.get(singleDBList.get(i));

        }
        else {
            System.out.println("Error");
            return null;
        }
    }

    @Override
    public void setData() {
        ds.setData(singleDBList);
    }
}