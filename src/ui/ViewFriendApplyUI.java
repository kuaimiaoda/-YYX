package ui;

import com.mysql.jdbc.log.Log;
import dao.FriendDao;
import dao.MessageDao;
import dao.RequestDao;
import entity.Admin;
import entity.Friend;
import entity.Request;
import function.CreateMessageTable;
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
import javafx.stage.StageStyle;
import org.w3c.dom.ls.LSOutput;

import java.util.List;

public class ViewFriendApplyUI extends Application {

    String myAccount;

    public void displayMyAccountInfo(ViewFriendUI viewFriendUI) {
        myAccount = viewFriendUI.getMyAccountProperty().get();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("好友申请");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        // 设置水平和垂直间距
        grid.setHgap(10);
        grid.setVgap(20);
        // 设置内边距
        grid.setPadding(new Insets(2, 2, 2, 2));

        Text sceneTitle = new Text("好友申请");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        // 好友申请 带滚动条
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(300, 300);

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 10, 10, 10));

        FriendDao friendDao = new FriendDao();
        RequestDao requestDao = new RequestDao();
        // 获取好友申请列表
        List<Request> requestList = requestDao.selectFriendApply(myAccount);
        for (Request request : requestList) {
            HBox hBox = new HBox();
            hBox.setSpacing(10);
            hBox.setPadding(new Insets(10, 10, 10, 10));


            Label friendAccount = new Label(request.getAdminName());
            friendAccount.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            hBox.getChildren().add(friendAccount);

            Button accept = new Button("接受");
            accept.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    CreateMessageTable messageTable = new CreateMessageTable();
                    MessageDao messageDao = new MessageDao();
                    // 接受好友申请
                    int condition1 = friendDao.acceptFriendApply(myAccount, String.valueOf(request.getRequestId()), "");
                    int condition2 = friendDao.acceptFriendApply(String.valueOf(request.getRequestId()), myAccount, "");
                    int condition3 = requestDao.deleteRequest(myAccount, request.getRequestId());
                    int condition4 = messageTable.createTable(Integer.valueOf(myAccount), request.getRequestId());
                    int condition5 = messageDao.insertMessage(Integer.valueOf(myAccount), request.getRequestId(), "我通过了你的好友申请。");
                    if (condition1 * condition2 * condition3 * condition4 * condition5 != 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("提示");
                        alert.setHeaderText("接受好友申请成功");
                        alert.showAndWait();
                    }

                    // 刷新好友申请列表
                    List<Request> requestList = requestDao.selectFriendApply(myAccount);
                    vBox.getChildren().clear();
                    for (Request request : requestList) {
                        HBox hBox = new HBox();
                        hBox.setSpacing(10);
                        hBox.setPadding(new Insets(10, 10, 10, 10));
                    }
                }
            });

            Button reject = new Button("拒绝");
            reject.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    // 拒绝好友申请
                    if (requestDao.deleteRequest(myAccount, request.getRequestId()) != 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("提示");
                        alert.setHeaderText("已拒绝");
                        alert.showAndWait();
                    }
                }
            });
            hBox.getChildren().add(accept);
            hBox.getChildren().add(reject);

            vBox.getChildren().add(hBox);
        }

        scrollPane.setContent(vBox);
        grid.add(scrollPane, 0, 1, 2, 1);


        Scene scene = new Scene(grid, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
