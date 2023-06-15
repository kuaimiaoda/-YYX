package dao;

import entity.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDao {
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    public Admin selectByAdminAccount(String account) {
        String sql = "select * from admin where adminAccount = ?";
        try {
            conn = BaseDao.getConn();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, account);
            rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("adminId");
                String adminName = rs.getString("adminName");
                String adminAccount = rs.getString("adminAccount");
                String adminPassword = rs.getString("adminPassword");
                Admin admin = new Admin(id, adminName, adminAccount, adminPassword);
                return admin;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            BaseDao.closeAll(conn, stmt, rs);
        }
    }

    public int addAdmin(Admin admin) {
        String sql = "insert into admin values (?,?,?,?)";
        try {
            conn = BaseDao.getConn();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, admin.getAdminId());
            stmt.setString(2, admin.getAdminName());
            stmt.setString(3, admin.getAdminAccount());
            stmt.setString(4, admin.getAdminPassword());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            BaseDao.closeAll(conn, stmt, rs);
        }
    }

    public boolean foundAccount(String account) {
        String sql = "select * from admin where adminAccount = ?";
        try {
            conn = BaseDao.getConn();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, account);
            rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            BaseDao.closeAll(conn, stmt, rs);
        }
    }

    public boolean foundName(String name) {
        String sql = "select * from admin where adminName = ?";
        try {
            conn = BaseDao.getConn();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            BaseDao.closeAll(conn, stmt, rs);
        }
    }
}
