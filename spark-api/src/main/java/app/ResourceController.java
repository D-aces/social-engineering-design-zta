package app;

import static spark.Spark.*;
import com.google.gson.*;

import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.*;

public class ResourceController {
    public static void initRoutes() {
        get("/search", (req, res) -> {
            res.type("application/json");

            String query = req.queryParams("query");
            String username = req.queryParams("username");

            if (query == null || username == null || query.isEmpty() || username.isEmpty()) {
                res.status(400);
                return "{\"error\":\"Missing search parameters\"}";
            }

            Gson gson = new Gson();

            // Load users
            InputStream userStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample_users.json");
            if (userStream == null) {
                res.status(500);
                return "{\"error\":\"User file not found\"}";
            }

            JsonArray users = gson.fromJson(new InputStreamReader(userStream), JsonArray.class);
            int userRank = -1;

            for (JsonElement userElement : users) {
                JsonObject user = userElement.getAsJsonObject();
                if (username.equals(user.get("name").getAsString())) {
                    userRank = user.get("rank").getAsInt();
                    break;
                }
            }

            if (userRank == -1) {
                res.status(403);
                return "{\"error\":\"User not found or unauthorized\"}";
            }

            // Load assets
            InputStream assetStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample_assets.json");
            if (assetStream == null) {
                res.status(500);
                return "{\"error\":\"Asset file not found\"}";
            }

            JsonArray assets = gson.fromJson(new InputStreamReader(assetStream), JsonArray.class);
            JsonArray results = new JsonArray();

            for (JsonElement el : assets) {
                JsonObject asset = el.getAsJsonObject();
                String uri = asset.get("uri").getAsString();
                int assetRank = asset.get("rank").getAsInt();

                if (uri.contains(query) && userRank >= assetRank) {
                    JsonObject filtered = new JsonObject();
                    filtered.addProperty("uri", uri);
                    filtered.addProperty("contents", asset.get("contents").getAsString());
                    results.add(filtered);
                }
            }

            return gson.toJson(results);
        });
    }
}
