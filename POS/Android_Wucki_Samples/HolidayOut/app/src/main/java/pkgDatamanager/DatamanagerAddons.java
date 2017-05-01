package pkgDatamanager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import AsyncTasks.AsyncAddon;
import pkgData.Addon;

/**
 * Created by Andreas Druml on 22.12.2016.
 */
public class DatamanagerAddons {

    private static DatamanagerAddons dm = null;
    private List<Addon> listAddons = null;

    private DatamanagerAddons() {
        //TODO get Addons from Webservice
       listAddons = new ArrayList<Addon>();
    }

    public static DatamanagerAddons getInstance() {
        if(dm == null) {
            dm = new DatamanagerAddons();
        }
        return dm;
    }

    public List<Addon> getAllAddons() throws ExecutionException, InterruptedException {
        listAddons = new AsyncAddon().execute().get();
        return listAddons;
    }

    public void bookAddon(int ID_Addon) {
        //TODO Book in Webservice
    }

    public Addon getItemByIndex(int index) {
        return listAddons.get(index);
    }


}
