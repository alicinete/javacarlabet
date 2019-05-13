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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author 05180206
 */
public class FXMLjanela1Controller implements Initializable {
@FXML
    private Button buttonconfirmar;
    @FXML
    TextField nome, idade, telefone, email, sessaoatual, proximasessao;
    @FXML
    CheckBox fototipo1, fototipo2, fototipo3, fototipo4, fototipo5, fototipo6, rosto, pescoco, peito, costas, abdomem, coxa, panturrilha, braco, antebraco, mao, pe, telangiectacias, rugasfinas, depilacao, manchas;
    /**
     * Initializes the controller class.
     */
    //se clicado no editar dados ler arquivo 
    //se clicado no novo criar novo arquivo
    
     @FXML
    private void confirmar(ActionEvent event) {
        
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
