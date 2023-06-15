package dao;

import entity.Message;
import function.SystemTime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    public int insertMessage(Integer senderId, Integer receiverId, String detail) {
        String tableName;
        SystemTime systemTime = new SystemTime();
        String stringTime = systemTime.getStringTime();
        String time = systemTime.getCurrentTime();
        if (senderId <= receiverId) {
            tableName = "message" + senderId + "to" + receiverId;
        } else {
            tableName = "message" + receiverId + "to" + senderId;
        }
        String sql = "insert into " + tableName + " values(?,?,?,?,?,?,?)";
        try {
            conn = BaseDao.getConn();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, stringTime);
            stmt.setInt(2, senderId);
            stmt.setInt(3, receiverId);
            stmt.setString(4, time);
            stmt.setString(5, "text"); // 此处默认为text类型
            stmt.setString(6, detail);
            stmt.setString(7, "unread");
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            BaseDao.closeAll(conn, stmt, rs);
        }
    }

    public List<Message> selectMessage(Integer myAccount, Integer friendAccount){
        String tableName;
        List<Message> messageList = new ArrayList<>();
        if (myAccount <= friendAccount) {
            tableName = "message" + myAccount + "to" + friendAccount;
        } else {
            tableName = "message" + friendAccount + "to" + myAccount;
        }
        String sql = "select * from " + tableName + " order by messageDate";
        try {
            conn = BaseDao.getConn();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String messageId = rs.getString("messageId");
                Integer senderId = rs.getInt("adminSenderId");
                Integer receiverId = rs.getInt("AdminReceiverId");
                String messageDate = rs.getString("messageDate");
                String messageType = rs.getString("messageType");
                String messageDetail = rs.getString("messageDetail");
                String messageStatus = rs.getString("messageStatus");
                Message message = new Message(messageId, senderId, receiverId, messageDate,messageType , messageDetail, messageStatus);
                messageList.add(message);
            }
            updateStatus(myAccount,friendAccount);
            return messageList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            BaseDao.closeAll(conn, stmt, rs);
        }
    }

    public void updateStatus(Integer receiver,Integer sender) {
        String tableName;
        if (receiver <= sender) {
            tableName = "message" + receiver + "to" + sender;
        } else {
            tableName = "message" + sender + "to" + receiver;
        }
        String sql = "update " + tableName + " set messageStatus = 'read' where adminReceiverId = ?";
        try {
            conn = BaseDao.getConn();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, receiver);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, stmt, rs);
        }
    }

}
