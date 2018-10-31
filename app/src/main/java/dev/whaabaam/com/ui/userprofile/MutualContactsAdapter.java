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

public class MutualContactsAdapter extends RecyclerView.Adapter<MutualContactsAdapter.MembersVH> {

    private ArrayList<UserProfileData.Mutual_friends.Data> list;
    private RequestManager requestManager;

    public MutualContactsAdapter(Context context) {
        list = new ArrayList<>();
        requestManager = Glide.with(context);
    }

    @NonNull
    @Override
    public MembersVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MembersVH(ItemMembersBinding.inflate(
                LayoutInflater.from(viewGroup.getContext()), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MembersVH membersVH, int i) {

        membersVH.binding.memberName.setText(list.get(membersVH.getAdapterPosition()).getFirst_name());
        requestManager.load(list.get(membersVH.getAdapterPosition()).getImage().getThumb())
                .apply(new RequestOptions().placeholder(R.drawable.pictures)).into(membersVH.binding.memberImage);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addMembers(ArrayList<UserProfileData.Mutual_friends.Data> data) {
        list.addAll(data);
        notifyDataSetChanged();
    }

    public static class MembersVH extends RecyclerView.ViewHolder {
        public ItemMembersBinding binding;

        public MembersVH(@NonNull ItemMembersBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
