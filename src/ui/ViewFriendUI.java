package ui;

import dao.FriendDao;
import entity.Admin;
import entity.Friend;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import dao.AdminDao;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.List;

public class ViewFriendUI extends Application {
    Admin admin = new Admin(); // 存储当前登录的用户信息

    // 从主界面获取账号
    public void displayAccountInfo(MainUI mainUI) {
        String account = mainUI.getMyAccountProperty().get();
        AdminDao adminDao = new AdminDao();
        admin = adminDao.selectByAdminAccount(account);
    }

    private StringProperty friendAccountProperty = new SimpleStringProperty();
    private StringProperty myAccountProperty = new SimpleStringProperty();

    public StringProperty getFriendAccount() {
        return friendAccountProperty;
    }

    public StringProperty getMyAccountProperty() {
        return myAccountProperty;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("我的好友");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("我的好友");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        // 好友 带滚动条
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(300, 300);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        scrollPane.setContent(vBox);
        grid.add(scrollPane, 0, 1, 2, 1);

        // 好友列表
        FriendDao friendDao = new FriendDao();
        List<Friend> list = friendDao.selectAll(admin.getAdminAccount());
        getFriendList(friendDao, list, primaryStage, vBox);

        // 查看好友申请
        Button viewFriendApplyButton = new Button("查看好友申请");
        viewFriendApplyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ViewFriendUI.this.myAccountProperty.set(admin.getAdminAccount());
                ViewFriendApplyUI viewFriendApplyUI = new ViewFriendApplyUI();
                viewFriendApplyUI.displayMyAccountInfo(ViewFriendUI.this);
                viewFriendApplyUI.start(new Stage());
            }
        });
        grid.add(viewFriendApplyButton, 0, 2);

        // 刷新好友列表
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                refresh(primaryStage, vBox);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);

        // 动态绑定
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHgrow(Priority.ALWAYS); // 列宽度随Stage大小改变
        grid.getColumnConstraints().add(columnConstraints);

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.ALWAYS); // 行高度随Stage大小改变
        grid.getRowConstraints().add(rowConstraints);

        primaryStage.initStyle(StageStyle.DECORATED);

        primaryStage.show();
    }

    private void getFriendList(FriendDao friendDao, List<Friend> list, Stage primaryStage, VBox vBox) {
        for (Friend friend : list) {
            String friendName = friend.getRemarkName();
            if (friendName == null || friendName.equals("")) {
                friendName = friendDao.selectNameById(friend.getFriendId());
            }
            Button button = new Button(friendName);
            // 设置按钮宽度与stage宽度一致
            button.prefWidthProperty().bind(primaryStage.widthProperty());

            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ViewFriendUI.this.myAccountProperty.set(admin.getAdminAccount());
                    ViewFriendUI.this.friendAccountProperty.set(String.valueOf(friend.getFriendId()));
                    // 打开与好友的聊天界面
                    ChatUI chatUI = new ChatUI();
                    chatUI.displayMyAccountInfo(ViewFriendUI.this);
                    chatUI.displayFriendAccountInfo(ViewFriendUI.this);
                    chatUI.start(new Stage());

                    // 关闭当前界面
                    primaryStage.close();
                }
            });

            vBox.getChildren().add(button);
        }
    }

    private void refresh(Stage primaryStage, VBox vBox) {
        FriendDao friendDao = new FriendDao();
        List<Friend> list = friendDao.selectAll(admin.getAdminAccount());
        vBox.getChildren().clear();
        getFriendList(friendDao, list, primaryStage, vBox);
    }
}
