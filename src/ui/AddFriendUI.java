package ui;

import dao.RequestDao;
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

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddFriendUI extends Application {

    Admin me = new Admin(); // 存储当前登录的用户信息

    public void displayAccountInfo(MainUI mainUI) {
        String account = mainUI.getMyAccountProperty().get();
        AdminDao adminDao = new AdminDao();
        me = adminDao.selectByAdminAccount(account);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("添加好友");
        primaryStage.resizableProperty().setValue(false);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("添加好友");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 5);

        Label userName = new Label("账号：");
        grid.add(userName, 0, 5);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 5);

        Button btn1 = new Button("发送请求");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn1);
        grid.add(hbBtn, 9, 9);

        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // 将信息存入数据库中
                String user = userTextField.getText();
                AdminDao adminDao = new AdminDao();
                Admin admin = adminDao.selectByAdminAccount(user);
                if (admin != null) {
                    // 获取现在的系统时间
                    Date date = new Date();
                    // 更改格式为 yyyy-MM-dd HH:mm:ss
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    RequestDao requestDao = new RequestDao();
                    if (requestDao.addRequest(admin.getAdminId(), me.getAdminId(), me.getAdminName(), simpleDateFormat.format(date)) != 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("提示");
                        alert.setHeaderText("添加成功");
                        alert.setContentText("已向该用户发送好友请求");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("提示");
                        alert.setHeaderText("添加失败");
                        alert.setContentText("已向该用户发送过好友请求");
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("提示");
                    alert.setHeaderText("添加失败");
                    alert.setContentText("该用户不存在");
                    alert.showAndWait();
                }
            }
        });

        Button btn2 = new Button("返回");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn2.getChildren().add(btn2);
        grid.add(hbBtn2, 10, 9);

        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                primaryStage.close();
            }
        });

        Scene scene = new Scene(grid, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
