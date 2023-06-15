package ui;

import entity.Admin;
import function.CreateFriendTable;
import function.CreateRequestTable;
import javafx.application.Application;
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

public class SignInUI extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("注册");
        primaryStage.resizableProperty().setValue(false);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("注册");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label userName = new Label("用户名：");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label adminAccount = new Label("账号：");
        grid.add(adminAccount, 0, 2);

        TextField adminAccountField = new TextField();
        grid.add(adminAccountField, 1, 2);

        Label pw = new Label("密码：");
        grid.add(pw, 0, 3);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 3);

        Label pw2 = new Label("确认密码：");
        grid.add(pw2, 0, 4);

        PasswordField pwBox2 = new PasswordField();
        grid.add(pwBox2, 1, 4);

        Button btn = new Button("注册");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 0, 5);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 7);

        // 按钮响应事件
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                AdminDao adminDao = new AdminDao();
                Admin admin = new Admin();
                admin.setAdminId(Integer.valueOf(adminAccountField.getText()));
                admin.setAdminName(userTextField.getText());
                admin.setAdminAccount(adminAccountField.getText());
                admin.setAdminPassword(pwBox.getText());

                if (adminDao.foundName(userTextField.getText())) {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("用户名已存在！");
                    return;
                }

                if (adminDao.foundAccount(adminAccountField.getText())) {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("该账号已被注册！");
                    return;
                }

                if (Objects.equals(pwBox.getText(), pwBox2.getText())) {
                    CreateFriendTable friendTable = new CreateFriendTable();
                    CreateRequestTable requestTable = new CreateRequestTable();

                    int condition1 = friendTable.createTable(Integer.valueOf(adminAccountField.getText()));
                    int condition2 = requestTable.createTable(Integer.valueOf(adminAccountField.getText()));


                    // 三个条件都满足才算注册成功
                    if (condition1 * condition2 != 0) {
                        if (adminDao.addAdmin(admin) == 0) {
                            actiontarget.setFill(Color.FIREBRICK);
                            actiontarget.setText("注册失败！");
                            return;
                        }
                        actiontarget.setFill(Color.GREEN);
                        actiontarget.setText("注册成功！");
                    } else {
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("注册失败！");
                    }
                } else {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("两次密码不一致！");
                }
            }
        });

        Button btn2 = new Button("已有账号？去登录");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn2.getChildren().add(btn2);
        grid.add(hbBtn2, 1, 5);

        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                LoginUI loginUI = new LoginUI();
                loginUI.start(primaryStage);
            }
        });
        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}


