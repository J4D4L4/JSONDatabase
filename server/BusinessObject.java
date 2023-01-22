package server;

import java.util.List;


public abstract class BusinessObject {
    String name;
    int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    BusinessObject(int id,String name){
        this.name = name;
        this.id = id;
    }


}

