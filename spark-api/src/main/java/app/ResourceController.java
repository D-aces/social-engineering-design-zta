package app;

import static spark.Spark.*;
import com.google.gson.*;
import java.util.Optional;

public class ResourceController {
    public static void initRoutes() {
        get("/resource/:id", (req, res) -> {
            res.type("application/json");

            // Get the token from the Authorization header
            String authHeader = req.headers("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                res.status(401);
                return new Gson().toJson(new ErrorResponse("Missing or invalid Authorization header"));
            }

            String token = authHeader.substring(7); // Strip "Bearer "

            // Validate token
            if (!TokenManager.isValid(token)) {
                res.status(401);
                return new Gson().toJson(new ErrorResponse("Invalid or expired token"));
            }

            // Load the resource (mocked for now)
            String id = req.params(":id");
            Optional<DBEngine.Asset> asset = DBEngine.getAssetById(id);


            if (asset.isPresent()) {
                return new Gson().toJson(asset.get());
            } else {
                res.status(404);
                return new Gson().toJson(new ErrorResponse("Asset not found"));
            }
        });
    }

    static class ErrorResponse {
        String error;
        ErrorResponse(String error) {
            this.error = error;
        }
    }
}
