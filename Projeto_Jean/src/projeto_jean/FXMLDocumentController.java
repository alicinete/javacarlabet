/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto_jean;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.swing.*;

/**
 *
 * @author 05180206
 */
public class FXMLDocumentController extends JFrame implements Initializable {

    @FXML
    private Label label;
    @FXML
    private Button buttoncadastro;
    @FXML
    private Button buttonsessao;
    @FXML
    private Button buttoneditar;
    @FXML
    ImageView fotoinicial;
    @FXML
    TextField info, listadenome;
    @FXML
    ComboBox buscarnome;

    @FXML
    private void novocadastro(ActionEvent event) {
       
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLjanela1.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage1 = new Stage();

            stage1.setTitle("Sistema de Pacientes");
            stage1.setScene(scene);
            stage1.show();
        } catch (IOException ex) {
            System.err.println("Erro ao abrir Janela 1");
        }
    }

    @FXML
    private void iniciarsessao(ActionEvent event){
        //Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //stage.close();
        //ou 
        // button1.getScene().getWindow().hide();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLjanela2.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage1 = new Stage();
            
            stage1.setTitle("Janela Secundária");
            stage1.setScene(scene);
            stage1.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void editardados(ActionEvent event) {
        //pegar arquivo ja aberto
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLjanela1.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage1 = new Stage();

            stage1.setTitle("Janela Secundária");
            stage1.setScene(scene);
            stage1.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Erro ao abrir Janela 1");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
