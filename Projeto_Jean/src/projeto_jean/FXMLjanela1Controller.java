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
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author 05180206
 */
public class FXMLjanela1Controller implements Initializable {

    @FXML
    private Button buttonconfirmar;
    @FXML
    TextField nome, idade, telefone, email;
    @FXML
    DatePicker sessaoatual, proximasessao;
    @FXML
    CheckBox fototipo1, fototipo2, fototipo3, fototipo4, fototipo5, fototipo6, rosto, pescoco, peito, costas, abdomem, coxas, panturrilha, braco, antebraco, mao, pe, telangiectacias, rugasfinas, depilacao, manchas;

    CheckBox[] partecorpo;
    CheckBox[] fototipos;
    CheckBox[] tratamentos;
    Cliente cliente;
    File imagem;
    @FXML
    ImageView imgcliente;

    /**
     * Initializes the controller class.
     */
    //se clicado no editar dados ler arquivo 
    //se clicado no novo criar novo arquivo
    @FXML
    private void confirmar(ActionEvent event) throws IOException {

        Cliente cli = new Cliente();
        cli.nome = nome.getText();
        cli.email = email.getText();
        cli.telefone = telefone.getText();
        cli.idade = Integer.parseInt(idade.getText());
        cli.sessaoatual = sessaoatual.getEditor().getText();
        cli.proximasessao = proximasessao.getEditor().getText();
        if (imagem != null) {
            cli.foto = imagem.getAbsolutePath();
        } else {
            cli.foto = "fotoperfilgenerica.jpg";
        }
        for (int i = 0; i < partecorpo.length; i++) {
            if (partecorpo[i].selectedProperty() != null) {
                cli.check[i] = partecorpo[i].isSelected();
            }
        }
        for (int i = 0; i < fototipos.length; i++) {
            if (fototipos[i].selectedProperty() != null) {
                cli.fototiposcheck[i] = fototipos[i].isSelected();
            }
        }
        for (int i = 0; i < tratamentos.length; i++) {
            if (tratamentos[i].selectedProperty() != null) {
                cli.tratamentoscheck[i] = tratamentos[i].isSelected();
            }
        }
        if (telangiectacias.isSelected()) {
            cli.intervalosessao = 7;
        }
        if (manchas.isSelected()) {
            cli.intervalosessao = 10;
        }
        if (rugasfinas.isSelected()) {
            cli.intervalosessao = 14;
        }
        if (depilacao.isSelected()) {
            cli.intervalosessao = 20;
        }
        sessaoatual.setValue(LocalDate.now());
        proximasessao.setValue(sessaoatual.getValue().plusDays(cli.intervalosessao));

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(new File(cli.getNome() + ".bin")))) {
            outputStream.writeObject(cli);
            outputStream.flush();
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
            System.err.println("Erro ao abrir Janela Principal");
        }
        Stage stage = (Stage) fototipo1.getScene().getWindow();
        stage.hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partecorpo = new CheckBox[]{rosto, pescoco, peito, costas, abdomem, coxas, panturrilha, braco, antebraco, mao, pe};
        fototipos = new CheckBox[]{fototipo1, fototipo2, fototipo3, fototipo4, fototipo5, fototipo6};
        tratamentos = new CheckBox[]{telangiectacias, rugasfinas, depilacao, manchas};
        for (CheckBox checkBox : partecorpo) {
            checkBox.setSelected(false);
        }
        for (CheckBox checkBox : fototipos) {
            checkBox.setSelected(false);
            checkBox.setOnAction(e -> {
                boolean r = maisDeUmSelecionadoFototipo();

                if (r) {
                    Alert dialogo = new Alert(Alert.AlertType.WARNING);
                    dialogo.setTitle("Sistema de Pacientes");
                    dialogo.setHeaderText("2 Itens selecionados");
                    dialogo.setContentText("Não é possível selecionar mais de um item");
                    dialogo.showAndWait();
                    checkBox.setSelected(false);
                }
            });
        }
        for (CheckBox checkBox : tratamentos) {
            checkBox.setSelected(false);
            checkBox.setOnAction(e -> {
                boolean r = maisDeUmSelecionadoTratamento();

                if (r) {
                    Alert dialogo = new Alert(Alert.AlertType.WARNING);
                    dialogo.setTitle("Sistema de Pacientes");
                    dialogo.setHeaderText("2 Itens selecionados");
                    dialogo.setContentText("Não é possível selecionar mais de um item");
                    dialogo.showAndWait();
                    checkBox.setSelected(false);
                }
            });
        }
        imgcliente.setOnMouseClicked(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Images", "*.*"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png")
            );
            File f = fileChooser.showOpenDialog(buttonconfirmar.getScene().getWindow());
            imagem = f;
            Image image = new Image(imagem.toURI().toString());
            imgcliente.setImage(image);
        });
        sessaoatual.setOnAction(e -> {
            proximasessao.setValue(sessaoatual.getValue().plusDays(cliente.intervalosessao));
        });
        sessaoatual.setValue(LocalDate.now());
        proximasessao.setValue(sessaoatual.getValue().plusDays(7));
    }

    void passaCliente(String nome_cliente) throws FileNotFoundException, IOException, ClassNotFoundException, URISyntaxException {
        if (!nome_cliente.isEmpty()) {//se ainda não existe clientes faz isso
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nome_cliente + ".bin"))) {
                cliente = (Cliente) ois.readObject();

                nome.setText(cliente.getNome());
                email.setText(cliente.getEmail());
                telefone.setText(cliente.telefone);
                idade.setText(String.valueOf(cliente.idade));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                sessaoatual.setValue(LocalDate.parse(cliente.sessaoatual, formatter));
                proximasessao.setValue(sessaoatual.getValue().plusDays(cliente.intervalosessao));
                imagem = new File(cliente.foto);
                Image image = new Image(imagem.toURI().toString());
                imgcliente.setImage(image);

                boolean[] assinalados = cliente.getCheck();
                for (int i = 0; i < partecorpo.length; i++) {
                    if (partecorpo[i] != null) {
                        partecorpo[i].setSelected(assinalados[i]);
                    }
                }
                boolean[] fototiposassinalados = cliente.getFototiposcheck();
                for (int i = 0; i < fototipos.length; i++) {
                    if (fototipos[i] != null) {
                        fototipos[i].setSelected(fototiposassinalados[i]);
                    }
                }
                boolean[] tratamentosassinalados = cliente.getTratamentoscheck();
                for (int i = 0; i < tratamentos.length; i++) {
                    if (tratamentos[i] != null) {
                        tratamentos[i].setSelected(tratamentosassinalados[i]);
                    }
                }

            }
        }
    }

    public void start(Stage primaryStage) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
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
                Stage stage = (Stage) fototipo1.
                        getScene().getWindow();
                stage.hide();
            }
        });
    }

    private boolean maisDeUmSelecionadoFototipo() {
        int c = 0;

        for (CheckBox fototipo : fototipos) {
            if (fototipo.isSelected()) {
                c++;
            }
        }
        return c > 1;
    }

    private boolean maisDeUmSelecionadoTratamento() {
        int c = 0;

        for (CheckBox tratamento : tratamentos) {
            if (tratamento.isSelected()) {
                c++;
            }
        }
        return c > 1;
    }

}
