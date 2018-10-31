package dev.whaabaam.com.data.model.other;

public class NotificationPrefsData {
    private String name,id;
    private boolean isChecked;

    public NotificationPrefsData(String name, String id, boolean isChecked) {
        this.name = name;
        this.id = id;
        this.isChecked = isChecked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
