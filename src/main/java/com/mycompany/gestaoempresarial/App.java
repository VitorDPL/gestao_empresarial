package com.mycompany.gestaoempresarial;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // Obtém as dimensões da tela do usuário
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        // Define a cena com o tamanho da tela
        scene = new Scene(FXMLLoader.load(getClass().getResource("primaryView.fxml")),
                           screenBounds.getWidth(), screenBounds.getHeight());

        stage.setScene(scene);

        // Define o tamanho mínimo, mas sem limitar o máximo
        configurarJanela(stage, 900, 700);

        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    // Função para configurar as propriedades da janela (Stage)
    public static void configurarJanela(Stage stage, int minWidth, int minHeight) {
        stage.setMinWidth(minWidth);
        stage.setMinHeight(minHeight);
    }

    public static void main(String[] args) {
        launch();
    }
}
