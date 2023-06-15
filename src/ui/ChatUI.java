package ui;

import dao.BaseDao;
import dao.FriendDao;
import dao.MessageDao;
import entity.Message;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class ChatUI extends Application {

    Integer friendAccount;
    String friendName;
    Integer myAccount;

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    private Integer preRowsCount = 0;
    TextArea textArea1 = new TextArea();

    public void displayFriendAccountInfo(ViewFriendUI viewFriendUI) {
        friendAccount = Integer.valueOf(viewFriendUI.getFriendAccount().get());
        friendName = new FriendDao().selectNameById(friendAccount);
    }

    public void displayFriendAccountInfo(MainUI mainUI) {
        friendAccount = Integer.valueOf(mainUI.getFriendAccount().get());
        friendName = new FriendDao().selectNameById(friendAccount);
    }

    public void displayMyAccountInfo(MainUI mainUI) {
        String account = mainUI.getMyAccountProperty().get();
        AdminDao adminDao = new AdminDao();
        myAccount = adminDao.selectByAdminAccount(account).getAdminId();
    }

    public void displayMyAccountInfo(ViewFriendUI viewFriendUI) {
        String account = viewFriendUI.getMyAccountProperty().get();
        AdminDao adminDao = new AdminDao();
        myAccount = adminDao.selectByAdminAccount(account).getAdminId();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text(friendName);
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 5);

        // 聊天记录显示框（带滚动条）
        textArea1.setPrefSize(300, 300);
        textArea1.setWrapText(true);
        textArea1.setEditable(false);
        grid.add(textArea1, 0, 4, 10, 4);

        // 在聊天记录框中显示聊天记录
        showMessage();


        // 消息输入框
        TextArea textArea = new TextArea();
        textArea.setPrefSize(300, 100);
        textArea.setWrapText(true);
        grid.add(textArea, 0, 8, 10, 1);


        // 发送按钮
        Button btn1 = new Button("发送");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn1);
        grid.add(hbBtn, 9, 9);

        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // 将信息存入数据库中
                MessageDao messageDao = new MessageDao();
                messageDao.insertMessage(myAccount, friendAccount, textArea.getText());

                // 将信息显示在聊天记录框中
                textArea1.appendText("我：" + textArea.getText() + "\n");
                textArea.clear();
            }
        });


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
        // 每隔5s刷新一次聊天记录
        refreshChat();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                refreshChat();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void refreshChat() {
        String tableName;
        if (myAccount < friendAccount) {
            tableName = "message" + myAccount + "to" + friendAccount;
        } else {
            tableName = "message" + friendAccount + "to" + myAccount;
        }
        String sql = "select count(*) from " + tableName + " where adminSenderId = ? and messageStatus = ?";
        try {
            conn = BaseDao.getConn();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, friendAccount);
            stmt.setString(2, "unread");
            rs = stmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            if (count != 0) {
                preRowsCount = count;
                textArea1.clear();
                showMessage();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            BaseDao.closeAll(conn, stmt, rs);
        }
    }

    private void showMessage() {
        MessageDao messageDao = new MessageDao();
        String mes;
        List<Message> messageList = messageDao.selectMessage(myAccount, friendAccount);
        for (Message message : messageList) {
            if (Objects.equals(message.getAdminSenderId(), myAccount)) {
                mes = "我：" + message.getMessageDetail() + "\n";
            } else {
                mes = friendName + "：" + message.getMessageDetail() + "\n";
            }
            textArea1.appendText(mes);
        }
    }
}
