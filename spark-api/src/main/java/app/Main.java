package app;

import static spark.Spark.*;
import app.AuthController;
import app.ResourceController;

public class Main {
    public static void main(String[] args) {
        port(4567);
        staticFiles.location("/public");
        AuthController.initRoutes();
        ResourceController.initRoutes();
    }
}
