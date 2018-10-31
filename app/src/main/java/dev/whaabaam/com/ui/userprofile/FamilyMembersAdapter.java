package dev.whaabaam.com.ui.userprofile;
/*
 * Created by RahulGupta on 11/9/18
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import dev.whaabaam.com.R;
import dev.whaabaam.com.data.model.other.UserProfileData;
import dev.whaabaam.com.databinding.ItemMembersBinding;

public class FamilyMembersAdapter extends RecyclerView.Adapter<MutualContactsAdapter.MembersVH> {
    private ArrayList<UserProfileData.Family.Data> familyList;
    private RequestManager requestManager;

    FamilyMembersAdapter(Context context) {
        familyList = new ArrayList<>();
        requestManager = Glide.with(context);
    }

    @NonNull
    @Override
    public MutualContactsAdapter.MembersVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MutualContactsAdapter.MembersVH(ItemMembersBinding.inflate(
                LayoutInflater.from(viewGroup.getContext()), viewGroup, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull MutualContactsAdapter.MembersVH membersVH, int i) {
        membersVH.binding.memberName.setText(familyList.get(membersVH.getAdapterPosition()).getUser_info().getFirst_name());
        requestManager.load(familyList.get(membersVH.getAdapterPosition()).getUser_info().getImage().getThumb())
                .apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(membersVH.binding.memberImage);
    }

    public void addFamilyList(ArrayList<UserProfileData.Family.Data> list) {
        for (UserProfileData.Family.Data d :
                list) {
            familyList.add(d);
            notifyItemInserted(familyList.size() - 1);
        }
    }

    @Override
    public int getItemCount() {
        return familyList.size();
    }
}
