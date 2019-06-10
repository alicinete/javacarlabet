/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto_jean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
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
    private Button buttonexcluir;
    @FXML
    private Button buttonsessao;
    @FXML
    private Button buttoneditar;
    @FXML
    ImageView fotoinicial;
    @FXML
    TextField nomeinicial, idadeinicial, telefoneinicial, emailinicial;
    @FXML
    ComboBox<String> buscarnome;

    ObservableList<String> nomes;

    @FXML
    private void excluircliente(ActionEvent event) {

        if (buscarnome.getSelectionModel().getSelectedItem() != null) {
            String nome = buscarnome.getSelectionModel().getSelectedItem();
            String n = nome;
            n = n + ".bin";
            File f = new File(n);
            f.delete();
            Alert dialogo = new Alert(Alert.AlertType.INFORMATION);
            dialogo.setTitle("Sistema de Pacientes");
            dialogo.setHeaderText("Cliente " + nome + " Excluído");
            dialogo.showAndWait();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                Stage stage1 = new Stage();

                stage1.setTitle("Sistema de Pacientes");
                stage1.setScene(scene);
                stage1.show();

            } catch (IOException ex) {
                ex.printStackTrace();
                System.err.println("Erro ao abrir Janela Principal");
            }
            Stage stage = (Stage) buscarnome.getScene().getWindow();
            stage.hide();
        } else {
            Alert dialogo = new Alert(Alert.AlertType.ERROR);
            dialogo.setTitle("Sistema de Pacientes");
            dialogo.setHeaderText("Nenhum Cliente Selecionado");
            dialogo.setContentText("Selecione um cliente para excluir");
            dialogo.showAndWait();
        }

    }

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
            ((Stage) buscarnome.getScene().getWindow()).hide();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Erro ao abrir Janela 1");
        }
    }

    @FXML
    private void iniciarsessao(ActionEvent event) throws FileNotFoundException {
        if (buscarnome.getSelectionModel().getSelectedItem() != null) {
            //Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            //stage.close();
            //ou 
            // button1.getScene().getWindow().hide();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLjanela2.fxml"));
                Parent root = loader.load();

                FXMLjanela2Controller controlador1 = loader.getController();
                try {
                    controlador1.passaCliente(buscarnome.getSelectionModel().getSelectedItem());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
                Scene scene = new Scene(root);
                Stage stage1 = new Stage();

                stage1.setTitle("Sistema de Pacientes");
                stage1.setScene(scene);
                stage1.show();
                ((Stage) buscarnome.getScene().getWindow()).hide();

            } catch (IOException ex) {
            }
        } else {
            Alert dialogo = new Alert(Alert.AlertType.ERROR);
            dialogo.setTitle("Sistema de Pacientes");
            dialogo.setHeaderText("Nenhum Cliente Selecionado");
            dialogo.setContentText("Selecione um cliente para iniciar sesão");
            dialogo.showAndWait();
        }
    }

    @FXML
    private void editardados(ActionEvent event) throws FileNotFoundException, ClassNotFoundException, URISyntaxException, IOException {
        if (buscarnome.getSelectionModel().getSelectedItem() != null) {
//pegar arquivo ja aberto
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLjanela1.fxml"));
                Parent root = loader.load();

                FXMLjanela1Controller controlador1 = loader.getController();
                controlador1.passaCliente(buscarnome.getSelectionModel().getSelectedItem());
                Scene scene = new Scene(root);
                Stage stage1 = new Stage();

                stage1.setTitle("Janela Secundária");
                stage1.setScene(scene);
                stage1.show();
                ((Stage) buscarnome.getScene().getWindow()).hide();

            } catch (IOException ex) {
                ex.printStackTrace();
                System.err.println("Erro ao abrir Janela 1");
            }
        } else {
            Alert dialogo = new Alert(Alert.AlertType.ERROR);
            dialogo.setTitle("Sistema de Pacientes");
            dialogo.setHeaderText("Nenhum Cliente Selecionado");
            dialogo.setContentText("Selecione um cliente para editar");
            dialogo.showAndWait();

        }

    }

    @FXML
    private void listanome(ActionEvent event) throws FileNotFoundException, IOException, ClassNotFoundException {
        if (buscarnome.getSelectionModel().getSelectedItem() != null) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(buscarnome.getSelectionModel().getSelectedItem() + ".bin"))) {
                Cliente cliente = (Cliente) ois.readObject();
                nomeinicial.setText(cliente.getNome());
                emailinicial.setText(cliente.getEmail());
                telefoneinicial.setText(cliente.telefone);
                idadeinicial.setText(String.valueOf(cliente.idade));
                Image image = new Image(new File(cliente.foto).toURI().toString());
                fotoinicial.setImage(image);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nomes = FXCollections.observableArrayList();
        buscarnome.setItems(nomes);
        File pasta = new File(System.getProperty("user.dir"));

        String[] arquivos = pasta.list();
        for (String nome : arquivos) {
            if (nome.contains(".bin")) {
                nomes.add(nome.replace(".bin", ""));
            }
        }

    }

}
