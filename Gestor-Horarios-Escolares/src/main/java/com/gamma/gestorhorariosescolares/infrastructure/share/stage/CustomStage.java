package com.gamma.gestorhorariosescolares.infrastructure.share.stage;

import com.gamma.gestorhorariosescolares.App;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public abstract class CustomStage extends Stage {

    private Scene scene;
    private Parent content;
    private final BordersCustomStage bordersCustomStage;
    private final TitleBar titleBar;

    private final String stylesheetPath;

    public CustomStage() {
        initStyle(StageStyle.UNDECORATED);
        stylesheetPath = App.class.getResource("styles/CustomStage.css").toExternalForm();
        bordersCustomStage = new BordersCustomStage(this);
        scene = new Scene(bordersCustomStage);
        scene.getStylesheets().add(stylesheetPath);
        setScene(scene);
        titleBar = new TitleBar(this);

        loadTitleBar();
    }

    private void loadTitleBar() {
        titleBar.getStyleClass().setAll("title-bar");
        BordersCustomStage.setTopAnchor(titleBar, 0.0);
        BordersCustomStage.setRightAnchor(titleBar, 0.0);
        BordersCustomStage.setLeftAnchor(titleBar, 0.0);

        bordersCustomStage.getChildren().add(0, titleBar);
    }

    public void setContent(Parent content) {
        if (content == null)
            throw new NullPointerException("Content is null");
        content.setId("content");
        BordersCustomStage.setTopAnchor(content, titleBar.getPrefHeight());
        BordersCustomStage.setBottomAnchor(content, 0.0);
        BordersCustomStage.setRightAnchor(content, 0.0);
        BordersCustomStage.setLeftAnchor(content, 0.0);

        if (this.content != null)
            bordersCustomStage.getChildren().remove(0);
        bordersCustomStage.getChildren().add(0, content);
        this.content = content;
    }
}