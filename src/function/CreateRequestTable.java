package function;

import dao.BaseDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CreateRequestTable {
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    public int createTable(Integer Id) {
        String tableName = "request" + Id;
        String sql = "create table " +tableName+ " (" +
                "id Integer primary key," +
                "requestId Integer references admin(adminId)," +
                "adminName varchar(50)," +
                "requestTime varchar(50) not null" +
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
