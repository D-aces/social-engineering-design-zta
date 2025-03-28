package app;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public class DBEngine {

    static class User {
        String uuid;
        String name;
        String password;
        String email;
        boolean tfa;
        String status;
    }

    static class Asset {
        String uuid;
        String uri;
        String owner_uuid;
        int sensitivity;
        String location;
    }

    public static boolean validateUser(String username, String password) {
        System.out.println("Checking username: " + username + ", password: " + password);
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader("sample_users.json");
            Type userListType = new TypeToken<List<User>>() {}.getType();
            List<User> users = gson.fromJson(reader, userListType);

            for (User user : users) {
                if (user.name.equalsIgnoreCase(username) && user.password.equals(password)) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading user data: " + e.getMessage());
        }
        return false;
    }

    public static Optional<Asset> getAssetById(String uuid) {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader("sample_assets.json");
            Type assetListType = new TypeToken<List<Asset>>() {}.getType();
            List<Asset> assets = gson.fromJson(reader, assetListType);

            return assets.stream()
                .filter(asset -> asset.uuid.equals(uuid))
                .findFirst();

        } catch (Exception e) {
            System.err.println("Error reading asset data: " + e.getMessage());
        }

        return Optional.empty();
    }
}
