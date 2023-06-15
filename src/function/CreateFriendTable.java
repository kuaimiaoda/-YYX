package function;

import dao.BaseDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CreateFriendTable {
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    public int createTable(Integer Id) {
        String tableName = "friend" + Id;
        String sql = "create table " + tableName + " (" +
                "id Integer primary key," +
                "friendId Integer references admin(adminId)," +
                "remarkName varchar(50)," +
                "addTime varchar(50) not null," +
                "finalMessageTime varchar(50)" +
                ");";
        try {
            conn = BaseDao.getConn();
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            // 判断表是否创建成功 成功的返回值为1
            String sql2 = "select count(*) from information_schema.TABLES where table_name = ?";
            stmt = conn.prepareStatement(sql2);
            stmt.setString(1, tableName);
            rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            BaseDao.closeAll(conn, stmt, rs);
        }
    }
}
