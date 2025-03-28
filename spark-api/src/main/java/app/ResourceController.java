package app;

import static spark.Spark.*;
import com.google.gson.*;

import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceController {
    public static void initRoutes() {
        get("/search", (req, res) -> {
            res.type("application/json");
        
            String query = req.queryParams("query");
            String username = req.queryParams("username");
        
            if (query == null || username == null || query.isEmpty() || username.isEmpty()) {
                res.status(400);
                return "{\"error\":\"Missing query or username\"}";
            }
        
            Gson gson = new Gson();
            JsonArray results = new JsonArray();
        
            try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample_assets.json")) {
                if (is == null) {
                    res.status(500);
                    return "{\"error\":\"Asset file not found\"}";
                }
        
                InputStreamReader reader = new InputStreamReader(is);
                JsonArray allAssets = gson.fromJson(reader, JsonArray.class);
        
                for (JsonElement el : allAssets) {
                    JsonObject obj = el.getAsJsonObject();
        
                    // 👇 match on both user and query term (in uri or contents)
                    boolean isOwner = username.equals(obj.get("owner_uuid").getAsString());
                    boolean matchesQuery = obj.get("uri").getAsString().contains(query) ||
                                            obj.get("contents").getAsString().contains(query);
        
                    if (isOwner && matchesQuery) {
                        JsonObject filtered = new JsonObject();
                        filtered.add("uri", obj.get("uri"));
                        filtered.add("contents", obj.get("contents"));
                        results.add(filtered);
                    }
                }
            } catch (Exception e) {
                res.status(500);
                return "{\"error\":\"Server error\"}";
            }
        
            return gson.toJson(results);
        });
        
    }
}
