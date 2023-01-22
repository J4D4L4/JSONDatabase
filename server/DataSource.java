package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class DataSource {

    DataSource() throws IOException {
        readFiles();
    }

    public HashMap<String, BusinessObject> getData() {
        return data;
    }

    public void setData(HashMap<String, BusinessObject> data) {
        this.data = data;
    }

    volatile HashMap<String, BusinessObject> data;
    final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    Lock readLock = rwl.readLock();
    Lock writeLock = rwl.writeLock();

    Gson gson = new GsonBuilder().create();
    String pathToJson = "./src/server/data/db.json";
    void readFiles() throws IOException {

        File myObj;
        try {
            readLock.lock();
            Type typeOfHashMap = new TypeToken<HashMap<String, BusinessObject>>() { }.getType();
            myObj = new File(pathToJson);
            JsonReader reader = new JsonReader(new FileReader(myObj));
            Scanner myReader = new Scanner(myObj);

            data = gson.fromJson(reader, typeOfHashMap);
                //System.out.println(data);

            readLock.unlock();
        } catch (FileNotFoundException e) {
            writeLock.lock();
            //myObj = new File("D:\\Dev\\JSON Database\\JSON Database\\task\\src\\server\\data");
            Path newFilePath = Paths.get(pathToJson);

            myObj = new File(Files.createFile(newFilePath).toString());
            writeLock.unlock();
            //if (myObj.createNewFile()) {
                //System.out.println("File created: " + myObj.getName());
            //}
        }
        readLock.lock();
        JsonReader reader = new JsonReader(new FileReader(myObj));
        Type typeOfHashMap = new TypeToken<HashMap<String, BusinessObject>>() { }.getType();
        data = gson.fromJson(reader, typeOfHashMap);
        readLock.unlock();
    }

    void updateFile(){
        writeLock.lock();
        File myObj = new File(pathToJson);
        Type typeOfHashMap = new TypeToken<HashMap<String, BusinessObject>>() { }.getType();
        try {
            Writer writer = new FileWriter(myObj);
            Gson builder = new GsonBuilder().create();
            builder.toJson(data,writer);
            //String json = gson.toJson(data);
            //gson.toJson(json,new FileWriter(myObj));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writeLock.unlock();
    }

    String toJson(){

        String json = gson.toJson(data);
        return json;
    }

    HashMap<String, BusinessObject> fromJson(String json){
        Type typeOfHashMap = new TypeToken<HashMap<String, BusinessObject>>() { }.getType();
        HashMap<String, BusinessObject> returnMap = gson.fromJson(json, typeOfHashMap);
        return returnMap;
    }
}
