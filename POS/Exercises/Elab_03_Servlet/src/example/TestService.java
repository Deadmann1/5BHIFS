package example;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

/**
 * Created by Manuel Sammer on 25.01.2017.
 */
@WebService()
public class TestService {
  @WebMethod
  public String sayHelloWorldFrom(String from) {
    String result = "Hello, world, from " + from;
    System.out.println(result);
    return result;
  }
  public static void main(String[] argv) {
    Object implementor = new TestService ();
    String address = "http://localhost:9000/TestService";
    Endpoint.publish(address, implementor);
  }
  public static do
}
