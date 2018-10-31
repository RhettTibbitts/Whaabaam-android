package dev.whaabaam.com.data.model.other;

import java.util.Date;

public class CalendarTabData {
    private String title;
    private boolean isSelected;
    private Date date;

    public CalendarTabData(String title, boolean isSelected, Date date) {
        this.title = title;
        this.isSelected = isSelected;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CalendarTabData
                && this.getTitle().trim().equals(((CalendarTabData) obj).getTitle())
                && this.getDate() == ((CalendarTabData) obj).getDate();
    }
}
