package dev.whaabaam.com.data.model.other;

public class FilterData {
    private String name, key;
    private int id;
    private String selected;
    private boolean isChecked;

    public FilterData(String name, int id, boolean isChecked, String key) {
        this.name = name;
        this.key = key;
        this.id = id;
        this.isChecked = isChecked;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }
}
