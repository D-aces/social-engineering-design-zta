package app;

import static spark.Spark.*;        // For port(), get(), post(), etc.
import com.google.gson.Gson;        // For JSON serialization

import java.util.*;

public class TokenManager {
    // token -> username
    private static final Map<String, String> tokenMap = new HashMap<>();

    // Issue a new token for a given username
    public static String issueToken(String username) {
        String token = UUID.randomUUID().toString();
        tokenMap.put(token, username);
        return token;
    }

    // Check if token exists
    public static boolean isValid(String token) {
        return token != null && tokenMap.containsKey(token);
    }

    // Get username associated with token
    public static String getUsername(String token) {
        return tokenMap.get(token);
    }

    // Revoke a token (optional)
    public static void revokeToken(String token) {
        tokenMap.remove(token);
    }
}
