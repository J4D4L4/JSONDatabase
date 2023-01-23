package server;

import java.util.List;


public abstract class BusinessObject {
    String name;
    String key;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return key;
    }

    public void setId(String id) {
        this.key = id;
    }

    BusinessObject(String id,String name){
        this.name = name;
        this.key = id;
    }


}

