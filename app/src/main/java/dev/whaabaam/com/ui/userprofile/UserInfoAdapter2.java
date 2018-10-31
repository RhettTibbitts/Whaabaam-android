package dev.whaabaam.com.ui.userprofile;


import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import dev.whaabaam.com.R;
import dev.whaabaam.com.data.model.other.UserProfileInfoModel;
import dev.whaabaam.com.databinding.ItemUserInfoBinding;

/**
 * @author rahul
 */
public class UserInfoAdapter2 extends RecyclerView.Adapter<UserInfoAdapter.UserInfoVH> {
    private ArrayList<UserProfileInfoModel> a;
    private UserInfoAdapter.OnItemClickCallback callback;

    public UserInfoAdapter2(UserInfoAdapter.OnItemClickCallback callback) {
        a = new ArrayList<>();
        this.callback = callback;
    }

    @NonNull
    @Override
    public UserInfoAdapter.UserInfoVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new UserInfoAdapter.UserInfoVH(ItemUserInfoBinding.inflate(LayoutInflater.from(viewGroup.getContext()),
                viewGroup, false), callback);
    }

    @Override
    public void onBindViewHolder(@NonNull UserInfoAdapter.UserInfoVH userInfoVH, int i) {

        userInfoVH.binding.setModel(a.get(userInfoVH.getAdapterPosition()));

//        (userInfoVH.binding.textDetails).setCompoundDrawablesWithIntrinsicBounds(a.get(userInfoVH.getAdapterPosition())
//                .getDrawableId(), 0, 0, 0);
//        (userInfoVH.binding.textDetails).setCompoundDrawablePadding(40);

        if (!TextUtils.isEmpty(a.get(userInfoVH.getAdapterPosition()).getAction())) {
            userInfoVH.binding.textDetails.setTextColor(ContextCompat.getColor(
                    userInfoVH.binding.btnViewResume.getContext(),
                    R.color.colorOrange));
        }

    }

    @Override
    public int getItemCount() {
        return a.size();
    }

    public void addData(ArrayList<UserProfileInfoModel> data) {
        a.addAll(data);
        notifyDataSetChanged();
    }
}

