package dao;

import java.sql.*;
public class BaseDao {
    /*
     *JDBC-第一步注册驱动
     */
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            //TODD Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
     *JDBC-第二步获取数据库连接，并且返回连接对象
     * @return 连接对象
     * @throws SQLException
     */
    public static Connection getConn() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/yunyuxuan", "root", "123456");
        return conn;
    }

    /*
     *JDBC-第三步关闭资源
     * @param conn
     * @param stmt
     * @param rs
     */
    public static void closeAll(Connection conn, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                //TODD Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                //TODD Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
