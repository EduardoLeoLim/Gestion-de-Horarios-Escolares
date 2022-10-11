package com.gamma.gestorhorariosescolares.compartido.infrestructura.stage;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TitleBar extends AnchorPane {
    private final Stage stage;
    private final Label title;
    private final HBox captionButtons;

    private double xOffset;
    private double yOffset;


    public TitleBar(Stage stage) {
        this.stage = stage;
        setId("titleBar");
        title = new Label();
        captionButtons = new HBox();
        loadTitleBar();
    }

    private void loadTitleBar() {
        setPrefHeight(32);
        stage.setMinHeight(32);
        stage.setMinWidth(144);

        loadTitle();
        loadCaptionButtons();

        setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
            if (stage.isMaximized()) {
                double fullScreenWidth = getWidth();
                stage.setMaximized(false);
                xOffset = getWidth() * event.getSceneX() / fullScreenWidth;
            }
        });
        setOnMouseReleased(event -> {
            if (event.getClickCount() == 2) {
                stage.setMaximized(!stage.isMaximized());
            }
        });

        stage.minHeightProperty().addListener((observable, oldValue, newValue) -> {
            if ((double) newValue < getPrefHeight())
                stage.setMinHeight(getPrefHeight());
        });
        stage.minWidthProperty().addListener((observable, oldValue, newValue) -> {
            if ((double) newValue < captionButtons.getPrefWidth())
                stage.setMinWidth(captionButtons.getPrefWidth());
        });
    }

    private void loadTitle() {
        title.getStyleClass().setAll("title-text");
        AnchorPane.setTopAnchor(title, 0.0);
        AnchorPane.setLeftAnchor(title, 10.0);
        heightProperty().addListener((observable, oldValue, newValue) -> title.setPrefHeight((double) newValue));
        stage.titleProperty().addListener((observable, oldTitle, newTitle) -> Platform.runLater(() -> title.setText(newTitle)));
        getChildren().add(title);
    }

    private void loadCaptionButtons() {
        Button closeButton = new Button("\uF072");
        Button maximizeButton = new Button("\uF031");
        Button minimizeButton = new Button("\uF030");

        heightProperty().addListener((observable, oldValue, newValue) -> {
            title.setPrefHeight((double) newValue);
            closeButton.setPrefHeight((double) newValue);
            maximizeButton.setPrefHeight((double) newValue);
            minimizeButton.setPrefHeight((double) newValue);
        });

        minimizeButton.setPrefWidth(48);
        minimizeButton.getStyleClass().setAll("caption-button");
        minimizeButton.setOnMouseClicked(event -> stage.setIconified(true));

        maximizeButton.setPrefWidth(48);
        maximizeButton.getStyleClass().setAll("caption-button");
        stage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
                maximizeButton.setText("\uF032");
            else
                maximizeButton.setText("\uF031");
        });
        maximizeButton.setOnMouseClicked(event -> stage.setMaximized(!stage.isMaximized()));

        closeButton.getStyleClass().setAll("caption-button", "close-button");
        closeButton.setPrefWidth(48);
        closeButton.setOnMouseClicked(event -> stage.close());

        captionButtons.setPrefWidth(144);
        captionButtons.getStyleClass().setAll("caption-buttons");
        AnchorPane.setTopAnchor(captionButtons, 0.0);
        AnchorPane.setRightAnchor(captionButtons, 0.0);
        captionButtons.getChildren().addAll(minimizeButton, maximizeButton, closeButton);

        getChildren().add(captionButtons);
    }
}