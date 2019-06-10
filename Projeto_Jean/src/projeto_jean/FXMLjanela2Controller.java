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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 05180206
 */
public class FXMLjanela2Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button buttoniniciarsessao;
    @FXML
    TextField nomesessao, idadesessao;
    @FXML
    DatePicker sessaoatualsessao, proxsessaosessao;
    @FXML
    ImageView imgjanela2;
    @FXML
    ProgressBar barradeprogresso;
    Cliente cliente;
    File imagem;

    @FXML
    private void iniciarsessao(ActionEvent event) throws IOException, InterruptedException {
        //Cliente cli = new Cliente();
        Service<ObservableList<String>> service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected ObservableList<String> call() throws Exception {
                        updateMessage("Finding friends . . .");
                        for (int i = 0; i < 10; i++) {
                            Thread.sleep(200);
                            updateProgress(i + 1, 10);
                        }
                        updateMessage("Finished.");
                        return null;
                    }
                };
            }
        };
        barradeprogresso.progressProperty().bind(service.progressProperty());
        service.stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observableValue, Worker.State oldState, Worker.State newState) {
                if (newState == Worker.State.SUCCEEDED) {
                    cliente.nome = nomesessao.getText();

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    sessaoatualsessao.setValue(proxsessaosessao.getValue());
                    proxsessaosessao.setValue(sessaoatualsessao.getValue().plusDays(cliente.intervalosessao));
                    cliente.sessaoatual = sessaoatualsessao.getEditor().getText();
                    cliente.proximasessao = proxsessaosessao.getEditor().getText();
                    Alert dialogo = new Alert(Alert.AlertType.INFORMATION);
                    dialogo.setTitle("Sistema de Pacientes");
                    dialogo.setHeaderText("Sessão concluída");
                    dialogo.setContentText("Data da próxima sessão: " + cliente.sessaoatual);
                    dialogo.showAndWait();

                    try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(new File(cliente.getNome() + ".bin")))) {
                        outputStream.writeObject(cliente);
                        outputStream.flush();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(FXMLjanela2Controller.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLjanela2Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
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
                        System.err.println("Erro ao abrir Janela 2");
                    }
                    Stage stage = (Stage) barradeprogresso.getScene().getWindow();
                    stage.hide();
                }
            }
        });

        service.start();

    }

    void passaCliente(String nome_cliente) throws FileNotFoundException, IOException, ClassNotFoundException {
        if (!nome_cliente.isEmpty()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nome_cliente + ".bin"))) {
                cliente = (Cliente) ois.readObject();
                nomesessao.setText(cliente.getNome());
                idadesessao.setText(String.valueOf(cliente.idade));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                sessaoatualsessao.setValue(LocalDate.parse(cliente.sessaoatual, formatter));
                proxsessaosessao.setValue(LocalDate.parse(cliente.proximasessao, formatter));

                Image image = new Image(new File(cliente.foto).toURI().toString());
                imgjanela2.setImage(image);

            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /* infojanela2.setText(cliente.nome);
        infojanela2.setText(String.valueOf(cliente.idade));
        infojanela2.setText(cliente.sessaoatual);
        infojanela2.setText(cliente.proximasessao);
         */
    }

}
