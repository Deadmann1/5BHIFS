package pkgData;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Created by Manuel Sammer on 08.03.2017.
 */
@ManagedBean(name = "BookBean")
@SessionScoped
public class BookBean {
    public BookBean() {
    }
    private String title = "Test";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
