package entity;

public class Message {
    private String messageId; // 消息id
    private Integer adminSenderId;// 发送者id
    private Integer adminReceiverId;// 接收者id
    private String messageDate;// 消息发送时间
    private String messageType;// 消息类型
    private String messageDetail;// 消息内容
    private String messageStatus;// 消息状态

    public Message() {
        super();
    }

    public Message(String messageId, Integer adminSenderId, Integer adminReceiverId, String messageDate, String messageType, String messageDetail, String messageStatus) {
        this.messageId = messageId;
        this.adminSenderId = adminSenderId;
        this.adminReceiverId = adminReceiverId;
        this.messageDate = messageDate;
        this.messageType = messageType;
        this.messageDetail = messageDetail;
        this.messageStatus = messageStatus;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Integer getAdminSenderId() {
        return adminSenderId;
    }

    public void setAdminSenderId(Integer adminSenderId) {
        this.adminSenderId = adminSenderId;
    }

    public Integer getAdminReceiverId() {
        return adminReceiverId;
    }

    public void setAdminReceiverId(Integer adminReceiverId) {
        this.adminReceiverId = adminReceiverId;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageDetail() {
        return messageDetail;
    }

    public void setMessageDetail(String messageDetail) {
        this.messageDetail = messageDetail;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }
}
