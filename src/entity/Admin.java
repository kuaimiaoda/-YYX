package entity;

public class Admin {
    private Integer adminId;
    private String adminName;
    private String adminAccount;
    private String adminPassword;

    public Admin() {
        super();
    }

    public Admin(Integer adminId, String adminName, String adminAccount, String adminPassword) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminAccount = adminAccount;
        this.adminPassword = adminPassword;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminAccount() {
        return adminAccount;
    }

    public void setAdminAccount(String adminAccount) {
        this.adminAccount = adminAccount;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", adminName='" + adminName + '\'' +
                ", adminAccount='" + adminAccount + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                '}';
    }
}
