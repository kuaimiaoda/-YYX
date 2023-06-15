package entity;

public class Request {
    private Integer id; // 该请求在请求表中的id
    private Integer requestId; // 请求者的id
    private String adminName; // 请求者的昵称
    private String requestTime; // 发起请求的时间

    public Request() {
        super();
    }

    public Request(Integer id, Integer requestId, String adminName, String requestTime) {
        this.id = id;
        this.requestId = requestId;
        this.adminName = adminName;
        this.requestTime = requestTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }
}
