package pkgDatamanager;

import pkgData.Account;

/**
 * Created by Andreas Druml on 22.12.2016.
 */
public class DatamanagerLogin {

    private static DatamanagerLogin dm = null;

    private DatamanagerLogin() {
    }

    public static DatamanagerLogin getInstance() {
        if(dm == null) {
            dm = new DatamanagerLogin();
        }
        return dm;
    }

    public int checkLogin(Account acc) {
        Account retAccount;
        //TODO Login
        //return retAccount.getRole_id();
        return 2;
    }

}
