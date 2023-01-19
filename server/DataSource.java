package server;

import java.util.HashMap;
import java.util.List;

public class DataSource {

    DataSource(){
        data =new HashMap<>();
    }

    public HashMap<Integer,BusinessObject> getData() {
        return data;
    }

    public void setData(HashMap<Integer, BusinessObject> data) {
        this.data = data;
    }

    HashMap<Integer, BusinessObject> data;
}
