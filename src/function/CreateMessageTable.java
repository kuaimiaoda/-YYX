package function;

import dao.BaseDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CreateMessageTable {
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    public int createTable(Integer AId, Integer BId) {
        // 要求AId<BId
        if(AId>BId)
        {
            int i = AId;
            AId = BId;
            BId = i;
        }
        String tableName = "message" + AId + "to" + BId;
        String sql = "create table " + tableName + " (" +
                "messageId varchar(50) primary key," +
                "adminSenderId Integer references admin(adminId)," +
                "adminReceiverId Integer references admin(adminId)," +
                "messageDate varchar(50) not null," +
                "messageType varchar(50) not null," +
                "messageDetail varchar(5000) not null," +
                "messageStatus varchar(10) not null" +
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
