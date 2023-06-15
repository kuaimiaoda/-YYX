package ui;

import com.mysql.jdbc.log.Log;
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
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import dao.AdminDao;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.w3c.dom.ls.LSOutput;

import java.util.List;

public class MainUI extends Application {
    Admin admin = new Admin();

    // 从登录界面获取账号信息
    public void displayAccountInfo(LoginUI loginUI) {
        String account = loginUI.getAccount().get();
        AdminDao adminDao = new AdminDao();
        admin = adminDao.selectByAdminAccount(account);
    }

    private StringProperty myAccountProperty = new SimpleStringProperty();
    private StringProperty friendAccountProperty = new SimpleStringProperty();

    public StringProperty getMyAccountProperty() {
        return myAccountProperty;
    }

    public StringProperty getFriendAccount() {
        return friendAccountProperty;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("");
//        primaryStage.getIcons().add(new Image("img/icon.png"));

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        // 设置水平和垂直间距
        grid.setHgap(10);
        grid.setVgap(20);
        // 设置内边距
        grid.setPadding(new Insets(2, 2, 2, 2));

        Text information = new Text(admin.getAdminName());
        information.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(information, 0, 0, 1, 2);

        // 搜索框
        TextField search = new TextField();
        search.setPromptText("搜索");
        grid.add(search, 0, 4);

        Button btnSearch = new Button("搜索");

        btnSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        });
        HBox hbBtnSearch = new HBox(10);
        hbBtnSearch.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtnSearch.getChildren().add(btnSearch);
        grid.add(hbBtnSearch, 1, 4);

        // 带滚动条的界面里边显示最近的几个聊天的朋友
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(200, 200);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        scrollPane.setContent(vBox);
        grid.add(scrollPane, 0, 6, 2, 5);

        Label label = new Label();
        label.setText("最近聊天的朋友");
        grid.add(label, 0, 5);

        // 从数据库中获取最近的几个聊天的朋友
        FriendDao friendDao = new FriendDao();
        List<Friend> recentChatFriends = friendDao.selectRecentChatFriends(admin.getAdminId());
        getRecentChatFriend(friendDao, recentChatFriends, primaryStage, vBox);

        // 添加好友按钮
        Button btnAddFriend = new Button("添加好友");
        HBox hbBtnAddFriend = new HBox(10);
        hbBtnAddFriend.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtnAddFriend.getChildren().add(btnAddFriend);
        grid.add(hbBtnAddFriend, 1, 11);

        btnAddFriend.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = new Stage();
                MainUI.this.myAccountProperty.set(admin.getAdminAccount());
                // 弹出添加好友界面
                AddFriendUI addFriendUI = new AddFriendUI();
                addFriendUI.displayAccountInfo(MainUI.this);
                addFriendUI.start(stage);
            }
        });

        // 查看好友按钮
        Button btnViewFriend = new Button("查看好友");
        HBox hbBtnViewFriend = new HBox(10);
        hbBtnViewFriend.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtnViewFriend.getChildren().add(btnViewFriend);
        grid.add(hbBtnViewFriend, 1, 12);

        btnViewFriend.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // 弹出查看好友界面
                MainUI.this.myAccountProperty.set(admin.getAdminAccount());
                ViewFriendUI viewFriendUI = new ViewFriendUI();
                viewFriendUI.displayAccountInfo(MainUI.this);
                viewFriendUI.start(new Stage());
            }
        });


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

    // 获取最近聊天的朋友
    private void getRecentChatFriend(FriendDao friendDao, List<Friend> recentChatFriends, Stage primaryStage, VBox vBox) {
        for (Friend recentChatFriend : recentChatFriends) {
            String showName = recentChatFriend.getRemarkName();
            if (showName == null || showName.equals("")) {
                showName = friendDao.selectNameById(recentChatFriend.getFriendId());
            }
            Button button = new Button(showName);
            // 设置按钮上的字居中
            button.setAlignment(Pos.CENTER);
            // 设置按钮的宽度与界面宽度一致
            button.prefWidthProperty().bind(primaryStage.widthProperty());
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    // 不关闭当前界面 打开新聊天界面
                    Stage stage = new Stage();
                    MainUI.this.myAccountProperty.set(admin.getAdminAccount());
                    MainUI.this.friendAccountProperty.set(String.valueOf(recentChatFriend.getFriendId()));
                    ChatUI chatUI = new ChatUI();
                    chatUI.displayMyAccountInfo(MainUI.this);
                    chatUI.displayFriendAccountInfo(MainUI.this);
                    chatUI.start(stage);
                }
            });
            vBox.getChildren().add(button);
        }
    }

    private void refresh(Stage primaryStage, VBox vBox) {
        // 从数据库中获取最近的几个聊天的朋友
        FriendDao friendDao = new FriendDao();
        List<Friend> recentChatFriends = friendDao.selectRecentChatFriends(admin.getAdminId());
        vBox.getChildren().clear();
        getRecentChatFriend(friendDao, recentChatFriends, primaryStage, vBox);
    }
}
