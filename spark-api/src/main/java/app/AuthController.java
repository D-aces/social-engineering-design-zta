package app;

import static spark.Spark.*;
import com.google.gson.*;
import java.util.HashMap;
import java.util.Map;
import app.DBEngine;
import app.TokenManager;
import app.TwoFactorAuth;
import com.google.gson.reflect.TypeToken;


public class AuthController {
    private static final Gson gson = new Gson();

    public static void initRoutes() {
        post("/login", (req, res) -> {
            res.type("application/json");
            Gson gson = new Gson();
            LoginRequest loginRequest = gson.fromJson(req.body(), LoginRequest.class);
        
            if (loginRequest == null || loginRequest.username == null || loginRequest.password == null) {
                res.status(400);
                return gson.toJson(new ErrorResponse("Missing username or password"));
            }
        
            boolean valid = DBEngine.validateUser(loginRequest.username, loginRequest.password);
        
            if (valid) {
                // Step 1: Trigger 2FA
                String phoneNumber = "+16476418487"; // You can make this dynamic later
                TwoFactorAuth.sendVerification(phoneNumber);
        
                // Step 2: Respond with success and flag that 2FA is needed
                res.status(200);
                return gson.toJson(new StatusResponse("2fa_required"));
            } else {
                res.status(401);
                return gson.toJson(new ErrorResponse("Invalid credentials"));
            }
        });
        
        post("/verify", (req, res) -> {
            res.type("application/json");
            Gson gson = new Gson();
        
            Map<String, String> requestBody = gson.fromJson(req.body(), new TypeToken<Map<String, String>>() {}.getType());
            String code = requestBody.get("code");
        
            // Replace with dynamic phone number logic later
            String phoneNumber = "+16476418487";
        
            boolean isValid = TwoFactorAuth.verifyCode(phoneNumber, code);
        
            if (isValid) {
                String token = TokenManager.issueToken("Nathan"); // Replace with dynamic username
                return gson.toJson(new TokenResponse(token));
            } else {
                res.status(401);
                return gson.toJson(new ErrorResponse("Invalid verification code"));
            }
        });
    }        

    // Simple POJO for incoming login requests
    static class LoginRequest {
        String username;
        String password;
    }

    // Response structure for tokens
    static class TokenResponse {
        String token;
        TokenResponse(String token) {
            this.token = token;
        }
    }

    // Response for errors
    static class ErrorResponse {
        String error;
        ErrorResponse(String error) {
            this.error = error;
        }
    }

    static class MessageResponse {
        String message;
        MessageResponse(String message) {
            this.message = message;
        }
    }
    static class StatusResponse {
        String status;
        StatusResponse(String status) {
            this.status = status;
        }
    }    
    
}
