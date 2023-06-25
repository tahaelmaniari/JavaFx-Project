package com.login.login;
import java.io.*;
import java.net.Socket;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
public class MainPanelController {
    @FXML
    private Button send;
    @FXML
    private ListView<String> vlist;
    @FXML
    private TextField message;
    Socket s;
    PrintWriter pw;
    @FXML
    private void close() throws IOException {

        Stage stage = (Stage) send.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("views/login-view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }
    @FXML
    private void send() throws IOException {
        String msgToSend = message.getText();
        pw.println(msgToSend);
        message.clear();
    }

    @FXML
    private void initialize() throws IOException {
        s = new Socket("localhost", 1235);
        InputStream is = s.getInputStream();
        OutputStream os = s.getOutputStream();
        InputStreamReader isr=new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        pw = new PrintWriter(os, true);
        new Thread(()-> {
            while (true)
            {
                try{
                    String response = br.readLine();
                    Platform.runLater(()->{
                        vlist.getItems().add(response);
                    });
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
        vlist.getItems().add(br.readLine());
    }
    @FXML
    private void loadHomeView(ActionEvent e) throws IOException {

    }
}
