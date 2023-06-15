package dao;

import entity.Admin;
import entity.Friend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FriendDao {
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    public List<Friend> selectAll(String account) {
        String sql = "select * from " + "friend" + account;
        List<Friend> list = new ArrayList<>();
        try {
            this.conn = BaseDao.getConn();
            this.stmt = conn.prepareStatement(sql);
            this.rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int friendId = rs.getInt("friendId");
                String remarkName = rs.getString("remarkName");
                String addTime = rs.getString("addTime");
                String finalMessageTime = rs.getString("finalMessageTime");

                //将结果集的数据封装成一个实体对象
                Friend friend = new Friend(id, friendId, remarkName, addTime, finalMessageTime);
                //把数据添加到集合当中
                list.add(friend);
            }
            //返回集合
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            BaseDao.closeAll(conn, stmt, rs);
        }
    }

    public String selectNameById(Integer id) {
        String sql = "select * from admin where adminId = ?";
        try {
            conn = dao.BaseDao.getConn();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("adminName");
            } else {
                return "该用户已注销";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            dao.BaseDao.closeAll(conn, stmt, rs);
        }
    }

    public List<Friend> selectRecentChatFriends(Integer Id) {
        String tableName = "friend" + Id;
        String sql = "select * from " + tableName + " order by finalMessageTime desc limit 10";
        List<Friend> list = new ArrayList();
        try {
            conn = dao.BaseDao.getConn();
            stmt = conn.prepareStatement(sql);
//            stmt.setString(1, tableName);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int friendId = rs.getInt("friendId");
                String remarkName = rs.getString("remarkName");
                String addTime = rs.getString("addTime");
                String finalMessageTime = rs.getString("finalMessageTime");

                //将结果集的数据封装成一个实体对象
                Friend friend = new Friend(id, friendId, remarkName, addTime, finalMessageTime);
                //把数据添加到集合当中
                list.add(friend);
            }
            return list;
        } catch (
                Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            dao.BaseDao.closeAll(conn, stmt, rs);
        }
    }

    public int acceptFriendApply(String myAccount, String friendAccount, String remakeName)
    {
        String tableName = "friend" + myAccount;
        String sql = "insert into " + tableName + " (id, friendId, remarkName, addTime, finalMessageTime) values (?, ?, ?, ?, ?)";

        // 获取当前时间
        Date date = new Date();
        // 转换时间格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            conn = dao.BaseDao.getConn();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(friendAccount));
            stmt.setInt(2, Integer.parseInt(friendAccount));
            stmt.setString(3, remakeName);
            stmt.setString(4, simpleDateFormat.format(date));
            stmt.setString(5, simpleDateFormat.format(date));
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            dao.BaseDao.closeAll(conn, stmt, rs);
        }
    }

}
