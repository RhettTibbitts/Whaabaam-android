package dev.whaabaam.com.ui.myprofile;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Objects;

import dev.whaabaam.com.R;
import dev.whaabaam.com.app.AppKeys;
import dev.whaabaam.com.data.model.other.UserModel;
import dev.whaabaam.com.databinding.ItemAddProfilePicBinding;
import dev.whaabaam.com.databinding.ItemOtherProfilePicBinding;
import dev.whaabaam.com.ui.base.BaseActivity;
import dev.whaabaam.com.utils.CommonUtils;

public class ProfileImageListAdapter extends RecyclerView.Adapter {
    private ArrayList<UserModel.Images> list;
    private OnProfileImageListOptionsCallback callback;

    ProfileImageListAdapter(OnProfileImageListOptionsCallback callback) {
        list = new ArrayList<>(1);
        this.callback = callback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        ViewDataBinding binding;
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        switch (viewType) {

            case R.layout.item_add_profile_pic:
                binding = ItemAddProfilePicBinding.inflate(inflater, viewGroup, false);
                viewHolder = new NewProfileImageVH((ItemAddProfilePicBinding) binding);
                break;
            default:// R.layout.item_other_profile_pic:
                binding = ItemOtherProfilePicBinding.inflate(inflater, viewGroup, false);
                viewHolder = new ProfileImageVH((ItemOtherProfilePicBinding) binding);
                break;
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
        if (TextUtils.isEmpty(list.get(i).getUserImages().getThumb())) {
            ((NewProfileImageVH) viewHolder).binding.add.setOnClickListener(view -> {
                if (list.size() <= 3)
                    callback.onAddProfileImageRequested();
                else CommonUtils.toast(view.getContext(), "Cannot add more than three images.");
            });
        } else {
            ProfileImageVH profileImageVH = (ProfileImageVH) viewHolder;
            UserModel.Images images = list.get(viewHolder.getAdapterPosition());
            profileImageVH.binding.setImagePath(images.getUserImages().getThumb());
            profileImageVH.binding.circleImageView.setOnClickListener(
                    view -> callback.onProfileImageRemoved(viewHolder.getAdapterPosition(), images.getId()));
            profileImageVH.binding.image17.setOnClickListener(view -> {

                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((AppCompatActivity) view.getContext(),
                                profileImageVH.binding.image17,
                                Objects.requireNonNull(ViewCompat.getTransitionName(profileImageVH.binding.image17)));

                Bundle bundle = options.toBundle();
                if (bundle == null)
                    bundle = new Bundle();  // creates a new instance if options returns null for pre-lollipop or any case
                else {
                    bundle.putInt(AppKeys.CURRENT_POSITION, viewHolder.getAdapterPosition());
                    bundle.putParcelableArrayList(AppKeys.IMAGE_ARRAY_LIST, list);
                }
                ((BaseActivity) view.getContext()).launchIntent(
                        ImageSliderActivity.class, bundle, false);
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addImageList(ArrayList<UserModel.Images> arrayList) {
        for (UserModel.Images item : arrayList) {
            addImage(item);
        }
    }

    public void addImage(UserModel.Images images) {
        list.add(images);
        notifyItemInserted(list.size());
    }

//    public void showHideAddButton() {
//        UserModel.Images img = new UserModel.Images();
//        img.setName("");
//        if (list.size() == 4 && list.get(0).equals(img)) {
//            list.remove(0);
//            notifyItemRemoved(0);
//        } else if (list.size() == 2 && !list.contains(img)) {
//            list.add(0, img);
//            notifyItemInserted(0);
//        }
//    }

    public ArrayList<UserModel.Images> getList() {
        return list;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? R.layout.item_add_profile_pic : R.layout.item_other_profile_pic;
    }

    static class ProfileImageVH extends RecyclerView.ViewHolder {
        private ItemOtherProfilePicBinding binding;

        ProfileImageVH(@NonNull ItemOtherProfilePicBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    static class NewProfileImageVH extends RecyclerView.ViewHolder {
        private ItemAddProfilePicBinding binding;

        NewProfileImageVH(@NonNull ItemAddProfilePicBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
