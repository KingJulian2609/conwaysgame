package com.example.conwaysgame;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Random;

public class ConwaysApplication extends Application {
    int res = 1000;
    int numberRects = 50;
    int rectChance = 5;
    int rectSize = res / numberRects;
    long lastCalled = System.currentTimeMillis();
    static boolean showMenu = true;



    boolean[][] spielfeld = new boolean[numberRects][numberRects];


    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        Scene scene = new Scene(root, res, res);



        AnchorPane anchor = new AnchorPane();
        Scene scene2 = new Scene(anchor, res, res);

        stage.setScene(scene);
        stage.show();
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (showMenu) {
                    showMenu(anchor);
                } else {
                    stage.setScene(scene2);
                    showGrid(anchor);
                }
            }
        }.start();
    }

    private Button getMenu() {
        TextField t = new TextField("");
        Button b = new Button("Start");
        b.setPrefSize(500, 100);

        AnchorPane.setRightAnchor(b, (double) (res/2) - 500/2 );
        AnchorPane.setLeftAnchor(b, (double) (res/2) - 500/2);
        AnchorPane.setBottomAnchor(b,(double) (res/2));


        b.setStyle(String.format("-fx-font-size: %dpx;", (int) (0.45 * 100)));
        b.setOnAction(
                _ -> showMenu = false
        );
//        StackPane r = new StackPane();
//        r.getChildren().add(b);
//
//        r.getChildren().add(t);
        return b;
    }

    public void showMenu(AnchorPane anchor) {
        if (anchor.getChildren().stream().noneMatch(
                (Node node) -> node instanceof Button))
        {
            System.out.println("gibts nicht macht er");

            anchor.getChildren().add(getMenu());
//            anchor.getChildren().clear();


//            anchor.getChildren().add(node);
//            AnchorPane.setRightAnchor(node, (double) (res/2) - 500/2 );
//            AnchorPane.setLeftAnchor(node, (double) (res/2) - 500/2);
//            AnchorPane.setBottomAnchor(node,(double) (res/2));
        }
    }
    private GridPane getGrid() {
        Random rand = new Random();
        GridPane gridPane = new GridPane();

        gridPane.setGridLinesVisible(true);
        for (int i = 0; i < numberRects; i++) {
            for (int j = 0; j < numberRects; j++) {
                rectChance = ConwaysController.probabilty;
                int chance = rand.nextInt(rectChance);
                int randN = rand.nextInt(99);
                if (chance < randN) {
                    spielfeld[i][j] = true;
                }
                Rectangle rectangle;
                if (spielfeld[i][j]) {
                    rectangle = new Rectangle(rectSize, rectSize, Color.BLACK);
                } else {
                    rectangle = new Rectangle(rectSize, rectSize, Color.TRANSPARENT);
                }
                gridPane.add(rectangle, i, j);
            }
        }
        return gridPane;
    }
    public void showGrid(AnchorPane anchor) {
        if (anchor.getChildren().stream().noneMatch(
                (Node node) -> node instanceof GridPane))
        {
            System.out.println("gibts nicht macht er");
            anchor.getChildren().clear();
            anchor.getChildren().add(getGrid());
        }
        GridPane gridPane = (GridPane) anchor.getChildren().getFirst();

        new AnimationTimer() {
            @Override
            public void handle(long l) {
                long millis = System.currentTimeMillis();
                if (millis - lastCalled < 500 || showMenu) {
                    return;
                }
                lastCalled = millis;


                Node node = gridPane.getChildren().getFirst();
                gridPane.getChildren().clear();
                gridPane.getChildren().addFirst(node);
                gridPane.setGridLinesVisible(true);

                for (int i = 0; i < numberRects; i++) {
                    for (int j = 0; j < numberRects; j++) {
                        if (!checkStayAlive(i, j)) {
                            spielfeld[i][j] = false;
                        }
                        if (ressurect(i, j)) {
                            spielfeld[i][j] = true;
                        }
                        Rectangle rectangle;
                        if (spielfeld[i][j]) {
                            rectangle = new Rectangle(rectSize, rectSize, Color.BLACK);
                        } else {
                            rectangle = new Rectangle(rectSize, rectSize, Color.TRANSPARENT);
                        }
                        gridPane.add(rectangle, i, j);
                    }
                }
            }
        }.start();
    }

    public boolean checkStayAlive(int x, int y) {
        boolean dreiLebend = false;
        int aliveCounter = 0;
        for (int i = -1; i <= 1; i++) {
            int toCheckX = x + i;
            if (toCheckX < 0 || toCheckX >= numberRects) {
                continue;
            }
            for (int j = -1; j <= 1; j++) {
                int toCheckY = y + j;
                if (toCheckX == x && toCheckY == y || toCheckY < 0 || toCheckY >= numberRects) {
                    continue;
                }
                if (spielfeld[toCheckX][toCheckY]) {
                    aliveCounter++;
                }
            }
        }

        if (aliveCounter == 2 || aliveCounter == 3) {
            dreiLebend = true;
        }

        return dreiLebend;
    }

    public boolean ressurect(int x, int y) {
        boolean dreiLebend = false;
        int aliveCounter = 0;
        for (int i = -1; i <= 1; i++) {
            int toCheckX = x + i;
            if (toCheckX < 0 || toCheckX >= numberRects) {
                continue;
            }
            for (int j = -1; j <= 1; j++) {
                int toCheckY = y + j;
                if (toCheckX == x && toCheckY == y || toCheckY < 0 || toCheckY >= numberRects) {
                    continue;
                }
                if (spielfeld[toCheckX][toCheckY]) {
                    aliveCounter++;
                }
            }
        }

        if (aliveCounter == 3) {
            dreiLebend = true;
        }

        return dreiLebend;
    }

    public void setShowMenu(boolean state) {
        showMenu = state;
    }

    public static void main(String[] args) {
        launch();

    }
}