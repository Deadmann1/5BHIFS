package pkgDatamanager;

/**
 * Created by Andreas Druml on 22.12.2016.
 */
public class Credentials {

    private static Credentials dm = null;
    private static String username = null;
    private static String name = null;
    private static String password = null;

    private Credentials() {
        username = "";
        name = loadName();
        password = "";
    }

    public static Credentials getInstance() {
        if(dm == null) {
            dm = new Credentials();
        }
        return dm;
    }

    public void setUsername(String _username) {
        username = _username;
    }

    public String getUsername() {
        return username;
    }

    private String loadName() {
        //TODO Load Name from Backend
        return "Mustermann";
    }

    public String getName() {
        return name;
    }



}
