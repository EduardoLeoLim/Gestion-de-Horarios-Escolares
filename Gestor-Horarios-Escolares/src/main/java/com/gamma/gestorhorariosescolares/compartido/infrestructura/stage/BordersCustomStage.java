package com.gamma.gestorhorariosescolares.compartido.infrestructura.stage;

import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class BordersCustomStage extends AnchorPane {
    private final Stage stage;
    private Pane topBorder;
    private Pane topRightBorder;
    private Pane topLeftBorder;
    private Pane rightBorder;
    private Pane leftBorder;
    private Pane bottomBorder;
    private Pane bottomRightBorder;
    private Pane bottomLeftBorder;

    private double prePositionX;
    private double prePositionY;
    private double preWidth;
    private double preHeight;

    public BordersCustomStage(Stage stage) {
        this.stage = stage;
        loadBorders();
        loadMaximizedConfiguration();
    }

    private void loadBorders() {
        loadTopBorder();
        loadTopRightBorder();
        loadTopLeftBorder();
        loadRightBorder();
        loadLeftBorder();
        loadBottomBorder();
        loadBottomRightBorder();
        loadBottomLeftBorder();
    }

    private void loadBottomLeftBorder() {
        bottomLeftBorder = new Pane();
        bottomLeftBorder.setId("bottomLeftBorder");
        bottomLeftBorder.setCursor(Cursor.SW_RESIZE);
        bottomLeftBorder.setPrefSize(10, 10);
        BordersCustomStage.setBottomAnchor(bottomLeftBorder, 0.0);
        BordersCustomStage.setLeftAnchor(bottomLeftBorder, 0.0);
        getChildren().add(bottomLeftBorder);

        bottomLeftBorder.setOnMouseEntered(event -> {
            if (stage.isResizable() && !stage.isMaximized())
                bottomLeftBorder.setCursor(Cursor.SW_RESIZE);
            else
                bottomLeftBorder.setCursor(Cursor.DEFAULT);
        });
        bottomLeftBorder.setOnMousePressed(this::PressedDetected);
        bottomLeftBorder.setOnMouseDragged(event -> {
            if (!isResizable())
                return;
            if (!event.isPrimaryButtonDown()) {
                bottomLeftBorder.setCursor(Cursor.DEFAULT);
                return;
            }
            if (bottomLeftBorder.getCursor() != Cursor.SW_RESIZE)
                return;

            double width = prePositionX - event.getScreenX() + preWidth;
            width = calculateWidth(width);

            double height = calculateHeight(event.getSceneY());

            double positionX = stage.getX();
            if (width > stage.getMinWidth() && width < stage.getMaxWidth())
                positionX = event.getScreenX();

            setLocation(positionX, prePositionY, width, height);
        });
    }

    private void loadBottomRightBorder() {
        bottomRightBorder = new Pane();
        bottomRightBorder.setId("bottomRightBorder");
        bottomRightBorder.setCursor(Cursor.SE_RESIZE);
        bottomRightBorder.setPrefSize(10, 10);
        BordersCustomStage.setBottomAnchor(bottomRightBorder, 0.0);
        BordersCustomStage.setRightAnchor(bottomRightBorder, 0.0);
        getChildren().add(bottomRightBorder);

        bottomRightBorder.setOnMouseEntered(event -> {
            if (stage.isResizable() && !stage.isMaximized())
                bottomRightBorder.setCursor(Cursor.SE_RESIZE);
            else
                bottomRightBorder.setCursor(Cursor.DEFAULT);
        });
        bottomRightBorder.setOnMousePressed(this::PressedDetected);
        bottomRightBorder.setOnMouseDragged(event -> {
            if (!isResizable())
                return;
            if (!event.isPrimaryButtonDown()) {
                bottomRightBorder.setCursor(Cursor.DEFAULT);
                return;
            }
            if (bottomRightBorder.getCursor() != Cursor.SE_RESIZE)
                return;


            double height = calculateHeight(event.getSceneY());
            double width = calculateWidth(event.getSceneX());

            setLocation(prePositionX, prePositionY, width, height);
        });
    }

    private void loadBottomBorder() {
        bottomBorder = new Pane();
        bottomBorder.setId("bottomBorder");
        bottomBorder.setPrefHeight(8);
        BordersCustomStage.setBottomAnchor(bottomBorder, 0.0);
        BordersCustomStage.setRightAnchor(bottomBorder, 10.0);
        BordersCustomStage.setLeftAnchor(bottomBorder, 10.0);
        getChildren().add(bottomBorder);

        bottomBorder.setOnMouseEntered(event -> {
            if (stage.isResizable() && !stage.isMaximized())
                bottomBorder.setCursor(Cursor.S_RESIZE);
            else
                bottomBorder.setCursor(Cursor.DEFAULT);
        });
        bottomBorder.setOnMousePressed(this::PressedDetected);
        bottomBorder.setOnMouseDragged(event -> {
            if (!isResizable())
                return;
            if (!event.isPrimaryButtonDown()) {
                bottomBorder.setCursor(Cursor.DEFAULT);
                return;
            }
            if (bottomBorder.getCursor() != Cursor.S_RESIZE)
                return;

            double height = calculateHeight(event.getSceneY());
            setLocation(prePositionX, prePositionY, preWidth, height);
        });
    }

    private void loadLeftBorder() {
        leftBorder = new Pane();
        leftBorder.setId("leftBorder");
        leftBorder.setPrefWidth(8);
        BordersCustomStage.setTopAnchor(leftBorder, 10.0);
        BordersCustomStage.setBottomAnchor(leftBorder, 10.0);
        BordersCustomStage.setLeftAnchor(leftBorder, 0.0);
        getChildren().add(leftBorder);

        leftBorder.setOnMouseEntered(event -> {
            if (stage.isResizable() && !stage.isMaximized())
                leftBorder.setCursor(Cursor.W_RESIZE);
            else
                leftBorder.setCursor(Cursor.DEFAULT);
        });
        leftBorder.setOnMousePressed(this::PressedDetected);
        leftBorder.setOnMouseDragged(event -> {
            if (!isResizable())
                return;
            if (!event.isPrimaryButtonDown()) {
                leftBorder.setCursor(Cursor.DEFAULT);
                return;
            }
            if (leftBorder.getCursor() != Cursor.W_RESIZE)
                return;

            double width = prePositionX - event.getScreenX() + preWidth;
            width = calculateWidth(width);

            double positionX = stage.getX();
            if (width > stage.getMinWidth() && width < stage.getMaxWidth())
                positionX = event.getScreenX();

            setLocation(positionX, prePositionY, width, preHeight);
        });
    }

    private void loadRightBorder() {
        rightBorder = new Pane();
        rightBorder.setId("rightBorder");
        rightBorder.setPrefWidth(8);
        BordersCustomStage.setTopAnchor(rightBorder, 10.0);
        BordersCustomStage.setBottomAnchor(rightBorder, 10.0);
        BordersCustomStage.setRightAnchor(rightBorder, 0.0);
        getChildren().add(rightBorder);

        rightBorder.setOnMouseEntered(event -> {
            if (stage.isResizable() && !stage.isMaximized())
                rightBorder.setCursor(Cursor.E_RESIZE);
            else
                rightBorder.setCursor(Cursor.DEFAULT);
        });
        rightBorder.setOnMousePressed(this::PressedDetected);
        rightBorder.setOnMouseDragged(event -> {
            if (!isResizable())
                return;
            if (!event.isPrimaryButtonDown()) {
                rightBorder.setCursor(Cursor.DEFAULT);
                return;
            }
            if (rightBorder.getCursor() != Cursor.E_RESIZE)
                return;

            double width = calculateWidth(event.getSceneX());

            setLocation(prePositionX, prePositionY, width, preHeight);
        });
    }

    private void loadTopLeftBorder() {
        topLeftBorder = new Pane();
        topLeftBorder.setId("topLeftBorder");
        topLeftBorder.setCursor(Cursor.NW_RESIZE);
        topLeftBorder.setPrefSize(10, 10);
        BordersCustomStage.setTopAnchor(topLeftBorder, 0.0);
        BordersCustomStage.setLeftAnchor(topLeftBorder, 0.0);
        getChildren().add(topLeftBorder);

        topLeftBorder.setOnMouseEntered(event -> {
            if (stage.isResizable() && !stage.isMaximized())
                topLeftBorder.setCursor(Cursor.NW_RESIZE);
            else
                topLeftBorder.setCursor(Cursor.DEFAULT);
        });
        topLeftBorder.setOnMousePressed(this::PressedDetected);
        topLeftBorder.setOnMouseDragged(event -> {
            if (!isResizable())
                return;
            if (!event.isPrimaryButtonDown()) {
                topLeftBorder.setCursor(Cursor.DEFAULT);
                return;
            }
            if (topLeftBorder.getCursor() != Cursor.NW_RESIZE)
                return;

            double width = prePositionX - event.getScreenX() + preWidth;
            width = calculateWidth(width);

            double height = prePositionY - event.getScreenY() + preHeight;
            height = calculateHeight(height);

            double positionY = stage.getY();
            if (height > stage.getMinHeight() && height < stage.getMaxHeight())
                positionY = event.getScreenY();

            double positionX = stage.getX();
            if (width > stage.getMinWidth() && width < stage.getMaxWidth())
                positionX = event.getScreenX();

            setLocation(positionX, positionY, width, height);
        });
    }

    private void loadTopRightBorder() {
        topRightBorder = new Pane();
        topRightBorder.setId("topRightBorder");
        topRightBorder.setPrefSize(10, 10);
        BordersCustomStage.setTopAnchor(topRightBorder, 0.0);
        BordersCustomStage.setRightAnchor(topRightBorder, 0.0);
        getChildren().add(topRightBorder);

        topRightBorder.setOnMouseEntered(event -> {
            if (stage.isResizable() && !stage.isMaximized())
                topRightBorder.setCursor(Cursor.NE_RESIZE);
            else
                topRightBorder.setCursor(Cursor.DEFAULT);
        });
        topRightBorder.setOnMousePressed(this::PressedDetected);
        topRightBorder.setOnMouseDragged(event -> {
            if (!isResizable())
                return;
            if (!event.isPrimaryButtonDown()) {
                topRightBorder.setCursor(Cursor.DEFAULT);
            }
            if (topRightBorder.getCursor() != Cursor.NE_RESIZE)
                return;

            double width = calculateWidth(event.getSceneX());

            double height = prePositionY - event.getScreenY() + preHeight;
            height = calculateHeight(height);

            double positionY = stage.getY();
            if (height > stage.getMinHeight() && height < stage.getMaxHeight())
                positionY = event.getScreenY();

            setLocation(stage.getX(), positionY, width, height);
        });
    }

    private void loadTopBorder() {
        topBorder = new Pane();
        topBorder.setId("topBorder");
        topBorder.setPrefHeight(8);
        BordersCustomStage.setTopAnchor(topBorder, 0.0);
        BordersCustomStage.setLeftAnchor(topBorder, 10.0);
        BordersCustomStage.setRightAnchor(topBorder, 10.0);
        getChildren().add(topBorder);

        topBorder.setOnMouseEntered(event -> {
            if (stage.isResizable() && !stage.isMaximized())
                topBorder.setCursor(Cursor.N_RESIZE);
            else
                topBorder.setCursor(Cursor.DEFAULT);
        });
        topBorder.setOnMousePressed(this::PressedDetected);
        topBorder.setOnMouseDragged(event -> {
            if (!isResizable())
                return;
            if (!event.isPrimaryButtonDown()) {
                topBorder.setCursor(Cursor.DEFAULT);
                return;
            }
            if (topBorder.getCursor() != Cursor.N_RESIZE)
                return;

            double height = prePositionY - event.getScreenY() + preHeight;
            height = calculateHeight(height);

            double positionY = stage.getY();
            if (height > stage.getMinHeight() && height < stage.getMaxHeight())
                positionY = event.getScreenY();

            setLocation(stage.getX(), positionY, stage.getWidth(), height);
        });
    }

    private void PressedDetected(MouseEvent event) {
        prePositionX = stage.getX();
        prePositionY = stage.getY();
        preWidth = stage.getWidth();
        preHeight = stage.getHeight();
    }

    private void loadMaximizedConfiguration() {
        stage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            topBorder.setVisible(!newValue);
            topRightBorder.setVisible(!newValue);
            topLeftBorder.setVisible(!newValue);
            rightBorder.setVisible(!newValue);
            leftBorder.setVisible(!newValue);
            bottomBorder.setVisible(!newValue);
            bottomRightBorder.setVisible(!newValue);
            bottomLeftBorder.setVisible(!newValue);

            if (!newValue)
                return;

            ObservableList<Screen> screens = Screen.getScreens();
            Rectangle2D currentScreenBounds = screens.stream().filter(screen -> {
                Rectangle2D bounds = screen.getVisualBounds();
                double positionX = stage.getX() + (getWidth() / 2);
                double positionY = stage.getY() + (getHeight() / 2);
                return positionX > bounds.getMinX() && positionX <= bounds.getMaxX() && positionY > bounds.getMinY() && positionY <= bounds.getMaxY();
            }).findFirst().get().getVisualBounds();
            setLocation(currentScreenBounds.getMinX(), currentScreenBounds.getMinY(), currentScreenBounds.getWidth(), currentScreenBounds.getHeight());
        });
    }

    private void setLocation(double positionX, double positionY, double width, double height) {
        stage.setX(positionX);
        stage.setY(positionY);
        stage.setWidth(width);
        stage.setHeight(height);
    }

    private double calculateHeight(double posibleHeight) {
        if (posibleHeight > stage.getMaxHeight())
            return stage.getMaxHeight();
        if (posibleHeight < stage.getMinHeight())
            return stage.getMinHeight();
        return posibleHeight;
    }

    private double calculateWidth(double posibleWidth) {
        if (posibleWidth > stage.getMaxWidth())
            return stage.getMaxWidth();
        if (posibleWidth < stage.getMinWidth())
            return stage.getMinWidth();
        return posibleWidth;
    }

}
