package server;

public class Person extends SingleDB{

    Car car = new Car();
    Rocket rocket = new Rocket();
    Person(String key, String name) {
        super(key, name);
    }
}

class Car{
    String model;
    String year;
}

class Rocket{
    String name;
    String launches;
}