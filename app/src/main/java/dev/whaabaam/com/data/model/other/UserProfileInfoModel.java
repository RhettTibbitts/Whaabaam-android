package dev.whaabaam.com.data.model.other;

/**
 * @author rahul
 */
public class UserProfileInfoModel {
    private int drawableId;
    private String value;
    private boolean showBtn;
    private String action;

    public UserProfileInfoModel(int drawableId, String value, boolean showBtn, String action) {
        this.drawableId = drawableId;
        this.value = value;
        this.showBtn = showBtn;
        this.action = action;
    }

    public boolean isShowBtn() {
        return showBtn;
    }

    public void setShowBtn(boolean showBtn) {
        this.showBtn = showBtn;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
