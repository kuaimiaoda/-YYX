package dao;

import entity.Friend;
import entity.Request;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestDao {
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    public int addRequest(Integer tableId, Integer requestId, String adminName, String requestTime) {
        String tableName = "request" + tableId;
        String sql = "insert into " + tableName + " values (?,?,?,?)";
        try {
            conn = BaseDao.getConn();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, requestId);
            stmt.setInt(2, requestId);
            stmt.setString(3, adminName);
            stmt.setString(4, requestTime);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            BaseDao.closeAll(conn, stmt, rs);
        }
    }

    public int deleteRequest(String myId, Integer requestId) {
        String tableName = "request" + myId;
        String sql = "delete from " + tableName + " where id = ?";
        try {
            conn = BaseDao.getConn();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, requestId);
            stmt.executeUpdate();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            BaseDao.closeAll(conn, stmt, rs);
        }
    }

    public List<Request> selectFriendApply(String account) {
        String tableName = "request" + account;
        String sql = "select * from " + tableName;
        List<Request> list = new ArrayList<>();
        try {
            conn = BaseDao.getConn();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int requestId = rs.getInt("requestId");
                String adminName = rs.getString("adminName");
                String requestTime = rs.getString("requestTime");
                //将结果集的数据封装成一个实体对象
                Request request = new Request(id, requestId, adminName, requestTime);
                //把数据添加到集合当中
                list.add(request);
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
}
