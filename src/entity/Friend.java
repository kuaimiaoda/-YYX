package entity;

public class Friend {
    private Integer id; // 该朋友在好友表中的id
    private Integer friendId; // 该朋友的id
    private String remarkName;
    private String addTime;
    private String finalMessageTime;


    public Friend() {
        super();
    }

    public Friend(Integer id, Integer friendId, String remarkName, String addTime, String finalMessageTime) {
        super();
        this.id = id;
        this.friendId = friendId;
        this.remarkName = remarkName;
        this.addTime = addTime;
        this.finalMessageTime = finalMessageTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getFinalMessageTime() {
        return finalMessageTime;
    }

    public void setFinalMessageTime(String finalMessageTime) {
        this.finalMessageTime = finalMessageTime;
    }
}
