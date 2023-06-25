package com.login.login;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
public class LoginController implements Initializable {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button loginButton;
    Window window;
    @FXML
    private void login() throws Exception {
        if(username.getText().equals("admin") && password.getText().equals("admin"))
        {  Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("views/panel-view.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Accueil");
            stage.show();
        }else{
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Erreur",
                    "Le user name ou mot de passe sont incorrect ");
        }
        //Authentification via la base de donnée
        /*
        if (this.isValidated()) {
             PreparedStatement ps;
             ResultSet rs;
             String query = "select * from users WHERE user_name = ? and password = ?";
             try {
                 ps = con.prepareStatement(query);
                 ps.setString(1, username.getText());
                 ps.setString(2, password.getText());
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     Stage stage = (Stage) loginButton.getScene().getWindow();
                     stage.close();
                     Parent root = FXMLLoader.load(getClass().getResource("/view/MainPanelView.fxml"));
                     Scene scene = new Scene(root);
                       stage.setScene(scene);
                       stage.setTitle("Admin Panel");
                       stage.getIcons().add(new Image("/asset/icon.png"));
                       stage.show();
                   } else {
                       AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                       "Invalid username and password.");
                     username.requestFocus();
                    }
             } catch (SQLException ex) {
               System.out.println(ex);
             }
         }
 */
        
    }
    @FXML
    private void showRegisterStage() throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("views/register-view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("User Registration");
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}