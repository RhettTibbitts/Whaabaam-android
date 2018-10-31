package dev.whaabaam.com.utils;

import java.util.ArrayList;

import dev.whaabaam.com.R;
import dev.whaabaam.com.data.model.other.UserProfileData;
import dev.whaabaam.com.data.model.other.UserProfileInfoModel;

/**
 * @author rahul
 */
public class UserInfoExtractorThread extends Thread {
    private UserProfileData data;
    private OnUserInfoExtractedCallback callback;

    public UserInfoExtractorThread(UserProfileData data, OnUserInfoExtractedCallback callback) {
        this.data = data;
        this.callback = callback;
    }

    @Override
    public void run() {
        ArrayList<UserProfileInfoModel> a = new ArrayList<>();
        ArrayList<UserProfileInfoModel> b = new ArrayList<>();
        if (!CommonUtils.isEmpty(data.getEmail())) {
            a.add(new UserProfileInfoModel(R.drawable.email, data.getEmail(), false, "email"));
        }
        if (!CommonUtils.isEmpty(data.getPhone())) {
            a.add(new UserProfileInfoModel(R.drawable.ic_mobile, data.getPhone().trim(), false, "call"));
        }
        if (data.getState() != null) {
            a.add(new UserProfileInfoModel(R.drawable.location, String.format("Currently lives in: %s, %s", data.getCity().getName(), data.getState().getName()), false, ""));
        }
        if (data.getFromState() != null) {
            a.add(new UserProfileInfoModel(R.drawable.location, "From: " + data.getFromCity().getName() + ", " + data.getFromState().getName(), false, ""));
        }
        if (!CommonUtils.isEmpty(data.getFb_link())) {
            a.add(new UserProfileInfoModel(R.drawable.ic_facebook, data.getFb_link(), false, ""));
        }
        if (!CommonUtils.isEmpty(data.getInsta_link())) {
            a.add(new UserProfileInfoModel(R.drawable.ic_instagram, data.getInsta_link(), false, ""));
        }
        if (!CommonUtils.isEmpty(data.getLinked_in_link())) {
            a.add(new UserProfileInfoModel(R.drawable.ic_linkedin, data.getLinked_in_link(), false, ""));
        }
        if (!CommonUtils.isEmpty(data.getTwit_link())) {
            a.add(new UserProfileInfoModel(R.drawable.ic_twitter, data.getTwit_link(), false, ""));
        }
        if (!CommonUtils.isEmpty(data.getAlma_matter())) {
            b.add(new UserProfileInfoModel(R.drawable.ic_school, data.getAlma_matter(), false, ""));
        }
        if (!CommonUtils.isEmpty(data.getCollege())) {
            b.add(new UserProfileInfoModel(R.drawable.ic_school, data.getCollege(), false, ""));
        }
        if (!CommonUtils.isEmpty(data.getEducation())) {
            b.add(new UserProfileInfoModel(R.drawable.institute, data.getEducation(), false, ""));
        }
        if (!CommonUtils.isEmpty(data.getHigh_school())) {
            b.add(new UserProfileInfoModel(R.drawable.study, data.getHigh_school(), false, ""));
        }
        if (!CommonUtils.isEmpty(data.getLikes())) {
            b.add(new UserProfileInfoModel(R.drawable.share, data.getLikes(), false, ""));
        }

        if (!CommonUtils.isEmpty(data.getOccupation())) {
            b.add(new UserProfileInfoModel(R.drawable.desination, data.getOccupation(), !CommonUtils.isEmpty(data.getResume()), ""));
        }
        if (!CommonUtils.isEmpty(data.getWork_website())) {
            b.add(new UserProfileInfoModel(R.drawable.desination, data.getWork_website(), false, ""));
        }

        if (data.getMilitary() != null) {
            b.add(new UserProfileInfoModel(R.drawable.usaf, data.getMilitary().getName(), false, ""));
        }
        if (data.getPolitical() != null) {
            b.add(new UserProfileInfoModel(R.drawable.flat, data.getPolitical().getName(), false, ""));
        }
        if (data.getReligion() != null) {
            b.add(new UserProfileInfoModel(R.drawable.ic_church, data.getReligion().getName(), false, ""));
        }
        if (data.getRelationship() != null) {
            b.add(new UserProfileInfoModel(R.drawable.status, data.getRelationship().getName(), false, ""));
        }
        callback.onUserInfoExtracted(a, b);
    }

    public interface OnUserInfoExtractedCallback {
        void onUserInfoExtracted(ArrayList<UserProfileInfoModel> data, ArrayList<UserProfileInfoModel> data1);
    }


}
