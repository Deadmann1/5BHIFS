package pkgModels;

import javax.ejb.Singleton;

/**
 * Created by Manuel Sammer on 01.03.2017.
 */
@Singleton(name = "UserEJB")
public class User {
    public User() {
    }

    private String Username;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }
}
