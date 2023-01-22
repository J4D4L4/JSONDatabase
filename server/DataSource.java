package server;

import java.util.HashMap;
import java.util.List;


public class DataSource {

    DataSource(){
        data =new HashMap<>();
    }

    public HashMap<String, BusinessObject> getData() {
        return data;
    }

    public void setData(HashMap<String, BusinessObject> data) {
        this.data = data;
    }

    HashMap<String, BusinessObject> data;
}
