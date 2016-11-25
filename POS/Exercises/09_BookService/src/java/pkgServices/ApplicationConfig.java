package pkgServices;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Manuel Sammer
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }


    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put("jersey.config.server.provider.classnames",
                "org.glassfish.jersey.media.multipart.MultiPartFeature");
        return props;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(pkgServices.BookService.class);
        resources.add(pkgServices.ReaderService.class);
    }

}
