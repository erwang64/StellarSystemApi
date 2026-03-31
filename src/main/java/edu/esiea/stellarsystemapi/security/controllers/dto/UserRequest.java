package edu.esiea.stellarsystemapi.security.controllers.dto;

import edu.esiea.stellarsystemapi.controllers.dto.AbstractRequest;
import edu.esiea.stellarsystemapi.controllers.dto.error.ResourceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequest extends AbstractRequest {

    @NotBlank
    @Size(min = 8, max = 30)
    private String login;

    @NotBlank
    @Size(min = 8)
    private String password;

    private String profile;

    public UserRequest() {
        super(ResourceType.USER);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
