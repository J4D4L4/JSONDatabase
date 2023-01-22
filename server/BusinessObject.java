package server;

import java.util.List;


public abstract class BusinessObject {
    String name;
    String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    BusinessObject(String id,String name){
        this.name = name;
        this.id = id;
    }


}

