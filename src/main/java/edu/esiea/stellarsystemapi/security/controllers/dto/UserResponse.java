package edu.esiea.stellarsystemapi.security.controllers.dto;

import edu.esiea.stellarsystemapi.security.model.Profile;
import edu.esiea.stellarsystemapi.security.model.User;

public class UserResponse {

    private int id;
    private String login;
    private Profile profile;

    public UserResponse() {
    }

    public UserResponse(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.profile = user.getProfile();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
