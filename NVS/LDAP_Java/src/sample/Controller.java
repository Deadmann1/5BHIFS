package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.naming.AuthenticationNotSupportedException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.security.sasl.AuthenticationException;
import java.util.Hashtable;

public class Controller {

    @FXML
    private Button btnConnect;

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPass;

    @FXML
    private Label lblMsg;

    @FXML
    void btnConnectClicked(ActionEvent event) {
        if (txtPass.getText() == null || txtPass.getText().equals("") || txtUser.getText() == null || txtUser.getText().equals("")) {
            lblMsg.setText("ERROR: Please fill out all fields!");
        } else {
            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.SECURITY_AUTHENTICATION, "Simple");
            env.put(Context.SECURITY_PRINCIPAL, "cn=" + txtUser.getText() + ",OU=USERS,DC=htl-vil,DC=local");
            env.put(Context.SECURITY_CREDENTIALS, txtPass.getText());
            env.put(Context.PROVIDER_URL, "ldap://192.168.128.253:389");
            env.put(Context.REFERRAL, "follow");
            try {
                DirContext ctx = new InitialLdapContext(env, null);
                lblMsg.setText("connected");
                System.out.println(ctx.getEnvironment());
                ctx.close();
            } catch (AuthenticationNotSupportedException ex) {
                lblMsg.setText("The authentication is not supported by the server");
            } catch (NamingException ex) {
                lblMsg.setText("error when trying to create the context");
            }
        }
    }
}
