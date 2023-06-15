package ui;

import entity.Admin;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import dao.AdminDao;
import javafx.stage.Stage;

import java.util.Objects;

public class LoginUI extends Application {
    private StringProperty accountProperty = new SimpleStringProperty();

    public StringProperty getAccount() {
        return accountProperty;
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("云语轩");
        stage.resizableProperty().setValue(false);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("欢迎使用云语轩");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label userName = new Label("账号：");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("密码：");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Button btn = new Button("登录");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 0, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        // 按钮响应事件
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                String account = userTextField.getText();
                String password = pwBox.getText();

                AdminDao adminDao = new AdminDao();
                Admin admin = adminDao.selectByAdminAccount(account);
                if (!Objects.equals(account, "") && Objects.equals(admin.getAdminPassword(), password)) {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("登录成功");
                    // 存入账号
                    LoginUI.this.accountProperty.set(account);
                    // 跳转到主界面
                    stage.close();
                    MainUI mainUI = new MainUI();
                    mainUI.displayAccountInfo(LoginUI.this);
                    mainUI.start(new Stage());
                } else {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("账号或密码错误");
                }
            }
        });

        // 注册按钮
        Button btn2 = new Button("注册");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn2.getChildren().add(btn2);
        grid.add(hbBtn2, 1, 4);

        // 按钮响应事件
        btn2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                SignInUI signInUI = new SignInUI();
                signInUI.start(stage);
            }
        });

        Scene scene = new Scene(grid, 300, 275);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
