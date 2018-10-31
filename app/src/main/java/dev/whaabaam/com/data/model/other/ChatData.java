package dev.whaabaam.com.data.model.other;

public class ChatData {
    private String message, time;
    private boolean isSent;

    public ChatData(String message, String time, boolean isSent) {
        this.message = message;
        this.time = time;
        this.isSent = isSent;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }
}
