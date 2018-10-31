package dev.whaabaam.com.data.model.other;
/*
 * Created by RahulGupta on 11/9/18
 */

public class NotesData {
    public NotesData() {
    }

    public NotesData(String id, String note, String created_at) {
        this.id = id;
        this.note = note;
        this.created_at = created_at;
    }

    private String id, note, created_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
