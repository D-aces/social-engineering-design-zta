package app;

import static spark.Spark.*;
import com.google.gson.*;

public class Main {
    public static void main(String[] args) {
        port(4567); // default

        get("/hello", (req, res) -> "Hello World");

        get("/api/user", (req, res) -> {
            res.type("application/json");
            return new Gson().toJson(new User("Jhin", 4));
        });
    }

    static class User {
        String name;
        int age;
        User(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}
