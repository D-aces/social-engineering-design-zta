package main.java.com.otu2.zt_engine.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

// Access request comes in
// TODO: Cross-check that this format works with the json passed from the user 
public class AccessRequest {

    @NotBlank
    private String userUuid;

    @NotBlank
    private String resourceUri;

    @NotBlank
    private String action;

    @NotNull
    private Map<String, Object> context;

    // Getters and setters

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getResourceUri() {
        return resourceUri;
    }

    public void setResourceUri(String resourceUri) {
        this.resourceUri = resourceUri;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }
}
