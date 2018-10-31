package dev.whaabaam.com.ui.myprofile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.michaelflisar.rxbus2.RxBus;
import com.michaelflisar.rxbus2.RxBusBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import dev.whaabaam.com.R;
import dev.whaabaam.com.Validation;
import dev.whaabaam.com.app.AppConstants;
import dev.whaabaam.com.app.AppKeys;
import dev.whaabaam.com.app.MyApplication;
import dev.whaabaam.com.data.model.other.Cities;
import dev.whaabaam.com.data.model.other.Militaries;
import dev.whaabaam.com.data.model.other.Politicals;
import dev.whaabaam.com.data.model.other.Relationships;
import dev.whaabaam.com.data.model.other.Religions;
import dev.whaabaam.com.data.model.other.States;
import dev.whaabaam.com.data.model.other.UserImages;
import dev.whaabaam.com.data.model.other.UserModel;
import dev.whaabaam.com.data.remote.ApiEndPoint;
import dev.whaabaam.com.data.remote.ApiManager;
import dev.whaabaam.com.data.remote.ApiResponse;
import dev.whaabaam.com.ui.base.BaseActivity;
import dev.whaabaam.com.ui.family.FamilyMemberList;
import dev.whaabaam.com.ui.fullscreenimage.FullScreenImageActivity;
import dev.whaabaam.com.ui.imagepicker.ImagePickerOptionsDialog;
import dev.whaabaam.com.ui.notificationprefs.NotificationsPreferencesActivity;
import dev.whaabaam.com.ui.resumeviewer.ResumeViewerActivity;
import dev.whaabaam.com.utils.AppDialog;
import dev.whaabaam.com.utils.CommonUtils;
import dev.whaabaam.com.utils.ImageCompressionUtils;
import dev.whaabaam.com.utils.OnImageCompressedCallback;
import fr.ganfra.materialspinner.MaterialSpinner;

import static dev.whaabaam.com.app.MyApplication.userModel;
import static dev.whaabaam.com.data.remote.ApiEndPoint.ENDPOINT_PROFILE_EDIT;

/**
 * @author rahul
 */
public class MyProfileActivityViewModel extends BaseObservable implements OnProfileImageListOptionsCallback,
        ApiResponse, AdapterView.OnItemSelectedListener, OnImageCompressedCallback {

    /*
     * User data
     * */
    public File resumeFile = null;
    public boolean isFirstRun = false;
    public ObservableBoolean isApiSuccessful = new ObservableBoolean();
    public final ObservableField<String> firstName = new ObservableField<>();
    public final ObservableField<String> lastName = new ObservableField<>();
    public final ObservableField<String> email = new ObservableField<>();
    public final ObservableField<String> occupation = new ObservableField<>();
    public final ObservableField<String> workWebsite = new ObservableField<>();
    public final ObservableField<String> highSchool = new ObservableField<>();
    public final ObservableField<String> education = new ObservableField<>();
    public final ObservableField<String> college = new ObservableField<>();
    public final ObservableField<String> almaMatter = new ObservableField<>();
    public final ObservableField<String> likes = new ObservableField<>();
    public final ObservableField<String> facebook = new ObservableField<>();
    public final ObservableField<String> linkedin = new ObservableField<>();
    public final ObservableField<String> twitter = new ObservableField<>();
    public final ObservableField<String> instagram = new ObservableField<>();
    public final ObservableField<String> phone = new ObservableField<>();

    public final ObservableBoolean emailAccess = new ObservableBoolean(false);
    public final ObservableBoolean phoneAccess = new ObservableBoolean(false);
    public final ObservableBoolean lastNameAccess = new ObservableBoolean(false);
    public final ObservableBoolean locationAccess = new ObservableBoolean(false);
    public final ObservableBoolean fromLocationAccess = new ObservableBoolean(false);
    public final ObservableBoolean occupationAccess = new ObservableBoolean(false);
    public final ObservableBoolean workAccess = new ObservableBoolean(false);
    public final ObservableBoolean educationAccess = new ObservableBoolean(false);
    public final ObservableBoolean highSchoolAccess = new ObservableBoolean(false);
    public final ObservableBoolean collegeAccess = new ObservableBoolean(false);
    public final ObservableBoolean almaMatterAccess = new ObservableBoolean(false);
    public final ObservableBoolean likesAccess = new ObservableBoolean(false);
    public final ObservableBoolean militaryAccess = new ObservableBoolean(false);
    public final ObservableBoolean politicalAccess = new ObservableBoolean(false);
    public final ObservableBoolean religionAccess = new ObservableBoolean(false);
    public final ObservableBoolean relationshipAccess = new ObservableBoolean(false);
    public final ObservableBoolean facebookAccess = new ObservableBoolean(false);
    public final ObservableBoolean linkedinAccess = new ObservableBoolean(false);
    public final ObservableBoolean twitterAccess = new ObservableBoolean(false);
    public final ObservableBoolean instagramAccess = new ObservableBoolean(false);
    public final ObservableBoolean familyAccess = new ObservableBoolean(false);

    public final ObservableInt statePos = new ObservableInt(-1);
    public final ObservableInt cityPos = new ObservableInt(-1);
    public final ObservableInt fromStatePos = new ObservableInt(-1);
    public final ObservableInt fromCityPos = new ObservableInt(-1);
    public final ObservableInt militaryPos = new ObservableInt(-1);
    public final ObservableInt politicalPos = new ObservableInt(-1);
    public final ObservableInt religionPos = new ObservableInt(-1);
    public final ObservableInt relationshipPos = new ObservableInt(-1);

    public final ObservableBoolean isResumeAvailable = new ObservableBoolean(false);

    private int military_id, political_id, religion_id, relationship_id, city_id, state_id, fromStateId, fromCityId;
    private ObservableBoolean isProfileImageUploading = new ObservableBoolean(false);
    private ObservableField<String> profileImage = new ObservableField<>();
    private ProfileImageListAdapter adapter;
    private Activity context;
    private ArrayList<UserModel.Images> imagesArrayList = new ArrayList<>();
    private ArrayList<States> statesArrayList = new ArrayList<>();
    private ArrayList<Cities> citiesArrayList = new ArrayList<>(), citiesArrayList2 = new ArrayList<>();
    private ArrayList<Religions> religionsArrayList = new ArrayList<>();
    private ArrayList<Relationships> relationshipsArrayList = new ArrayList<>();
    private ArrayList<Militaries> militariesArrayList = new ArrayList<>();
    private ArrayList<Politicals> politicalsArrayList = new ArrayList<>();
    private Gson gson;
    private CityAdapter cityAdapter, cityAdapter2;

    MyProfileActivityViewModel(Activity context) {
        this.context = context;
        gson = new Gson();
        adapter = new ProfileImageListAdapter(this);
        initImageBroadcastReceiver();
        requestMyProfileApi();
    }

    public void init() {
        setDataToUI();
        profileImage.set(userModel.getImage().getThumb());
        adapter.addImage(new UserModel.Images());
        adapter.addImageList(userModel.getImages());
    }

    private void setDataToUI() {
        isResumeAvailable.set(!TextUtils.isEmpty(userModel.getResume()));
        firstName.set(userModel.getFirst_name());
        lastName.set(userModel.getLast_name());
        email.set(userModel.getEmail());
        workWebsite.set(userModel.getWork_website());
        education.set(userModel.getEducation());
        highSchool.set(userModel.getHigh_school());
        college.set(userModel.getCollege());
        almaMatter.set(userModel.getAlma_matter());
        occupation.set(userModel.getOccupation());
        likes.set(userModel.getLikes());
        facebook.set(userModel.getFb_link());
        linkedin.set(userModel.getLinked_in_link());
        twitter.set(userModel.getTwit_link());
        instagram.set(userModel.getInsta_link());
        phone.set(userModel.getPhone());

        lastNameAccess.set(CommonUtils.intToBoolean(userModel.getLast_name_access()));
        emailAccess.set(CommonUtils.intToBoolean(userModel.getEmail_access()));
        workAccess.set(CommonUtils.intToBoolean(userModel.getWork_website_access()));
        educationAccess.set(CommonUtils.intToBoolean(userModel.getEducation_access()));
        almaMatterAccess.set(CommonUtils.intToBoolean(userModel.getAlma_matter_access()));
        occupationAccess.set(CommonUtils.intToBoolean(userModel.getOccupation_access()));
        likesAccess.set(CommonUtils.intToBoolean(userModel.getLikes_access()));
        highSchoolAccess.set(CommonUtils.intToBoolean(userModel.getHigh_school_access()));
        collegeAccess.set(CommonUtils.intToBoolean(userModel.getCollege_access()));
        militaryAccess.set(CommonUtils.intToBoolean(userModel.getMilitary_id_access()));
        religionAccess.set(CommonUtils.intToBoolean(userModel.getReligion_id_access()));
        relationshipAccess.set(CommonUtils.intToBoolean(userModel.getRelationship_id_access()));
        politicalAccess.set(CommonUtils.intToBoolean(userModel.getPolitical_id_access()));
        locationAccess.set(CommonUtils.intToBoolean(userModel.getCity_id_access()));
        fromLocationAccess.set(CommonUtils.intToBoolean(userModel.getFrom_city_id_access()));
        facebookAccess.set(CommonUtils.intToBoolean(userModel.getFb_link_access()));
        linkedinAccess.set(CommonUtils.intToBoolean(userModel.getLinked_in_link_access()));
        instagramAccess.set(CommonUtils.intToBoolean(userModel.getInsta_link_access()));
        twitterAccess.set(CommonUtils.intToBoolean(userModel.getTwit_link_access()));
        familyAccess.set(CommonUtils.intToBoolean(userModel.getFamily_access()));
        phoneAccess.set(CommonUtils.intToBoolean(userModel.getPhone_access()));


    }

    public String getProfileImage() {
        return profileImage.get();
    }

    public void setProfileImage(String profileImage) {
        this.profileImage.set(profileImage);
        notifyChange();
    }

    @Override
    public void onAddProfileImageRequested() {
        ImagePickerOptionsDialog dialog = new ImagePickerOptionsDialog();
        dialog.setArguments(getImagePickerBundle(AppConstants.IMAGE_PICKER_SOURCE.OTHER_PROFILE_IMAGES, profileImage.get()));
        dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "");
    }

    /*
     * BEGIN: API Response
     * */
    @Override
    public void onSuccess(JsonObject response, AppConstants.API_MODE apiMode) {

        switch (apiMode) {

            case MY_PROFILE:
                JsonObject response1 = response.getAsJsonObject("data");
                handleMyProfileData(response1);
                break;
            case DELETE_PROFILE_PIC:
                setProfileImage("");
                CommonUtils.toast(context, response.get(AppKeys.MESSAGE).getAsString());
                break;
            case DELETE_OTHER_PIC:
                CommonUtils.toast(context, response.get(AppKeys.MESSAGE).getAsString());
                break;
            case DELETE_RESUME:
                isResumeAvailable.set(false);
                CommonUtils.toast(context, "Resume deleted successfully!");
                userModel.setResume("");
                CommonUtils.saveUserData(userModel);
                break;
            default:
                break;
        }

    }

    private void handleMyProfileData(JsonObject response1) {
        userModel = gson.fromJson(response1.getAsJsonObject(AppKeys.USER).toString(), UserModel.class);
        CommonUtils.saveUserData(userModel);
        statesArrayList = gson.fromJson(response1.getAsJsonArray("states"),
                new TypeToken<ArrayList<States>>() {
                }.getType());
//        citiesArrayList = gson.fromJson(response1.getAsJsonArray("cities"),
//                new TypeToken<ArrayList<Cities>>() {
//                }.getType());
        politicalsArrayList = gson.fromJson(response1.getAsJsonArray("politicals"),
                new TypeToken<ArrayList<Politicals>>() {
                }.getType());
        militariesArrayList = gson.fromJson(response1.getAsJsonArray("militaries"),
                new TypeToken<ArrayList<Militaries>>() {
                }.getType());
        religionsArrayList = gson.fromJson(response1.getAsJsonArray("religions"),
                new TypeToken<ArrayList<Religions>>() {
                }.getType());
        relationshipsArrayList = gson.fromJson(response1.getAsJsonArray("relationships"),
                new TypeToken<ArrayList<Relationships>>() {
                }.getType());
        isApiSuccessful.set(true);
    }

    @Override
    public void onFailure(String message, AppConstants.API_MODE apiMode) {
        AppDialog.getInstance(context).showMessageDialogToDismiss(context, message);
    }

    //-----------END_------------------------------


    /*
     * BEGIN: Callbacks
     * */
    @Override
    public void onProfileImageSelected() {

    }

    @Override
    public void onProfileImageRemoved(int position, int imageID) {

        AppDialog.getInstance(context).showTwoButtonDialog(context,
                "Are you sure you want to remove this image?", "Yes", "No",
                value -> {
                    adapter.getList().remove(position);
                    adapter.notifyItemRemoved(position);
                    requestDeleteOtherProfileImageApi(imageID);
                });

    }
    //----------------__END----------

    /*
     * BEGIN: initializing all spinners
     * */


    void initMilitary(MaterialSpinner militarySpinner) {
        MilitaryAdapter militaryAdapter = new MilitaryAdapter(militariesArrayList);
        militarySpinner.setAdapter(militaryAdapter);
        militaryPos.set(militaryAdapter.getItemIndexById(userModel.getMilitary_id()) + 1);
        militarySpinner.setOnItemSelectedListener(this);
    }

    void initPoliticalSpinner(MaterialSpinner politicalSpinner) {
        PoliticalAdapter politicalAdapter = new PoliticalAdapter(politicalsArrayList);
        politicalSpinner.setAdapter(politicalAdapter);
        politicalPos.set(politicalAdapter.getItemIndexById(userModel.getPolitical_id()) + 1);
        politicalSpinner.setOnItemSelectedListener(this);

    }

    void initReligionSpinner(MaterialSpinner religionSpinner) {
        ReligionAdapter religionAdapter = new ReligionAdapter(religionsArrayList);
        religionSpinner.setAdapter(religionAdapter);
        religionPos.set(religionAdapter.getItemIndexById(userModel.getReligion_id()) + 1);
        religionSpinner.setOnItemSelectedListener(this);

    }

    void initRelationships(MaterialSpinner relationshipSpinner) {
        RelationshipAdapter relationshipAdapter = new RelationshipAdapter(relationshipsArrayList);
        relationshipSpinner.setAdapter(relationshipAdapter);
        relationshipPos.set(relationshipAdapter.getItemIndexById(userModel.getRelationship_id()) + 1);
        relationshipSpinner.setOnItemSelectedListener(this);
    }

    public void initState(MaterialSpinner stateSpinner) {

        StateAdapter stateAdapter = new StateAdapter(statesArrayList);
        stateSpinner.setAdapter(stateAdapter);
        stateSpinner.setOnItemSelectedListener(this);
        statePos.set(stateAdapter.getItemIndexById(userModel.getState_id()) + 1);
    }

    public void initCity(MaterialSpinner citySpinner) {
        cityAdapter = new CityAdapter(citiesArrayList);
        citySpinner.setAdapter(cityAdapter);
        citySpinner.setOnItemSelectedListener(this);
//        RxBusBuilder.create(Integer.class).withKey("cityID")
//                .subscribe(value -> {
//                    cityPos.set(cityAdapter.getItemIndexById(value) + 1);
//                });
    }

    public void initFromState(MaterialSpinner stateSpinner) {
        StateAdapter stateAdapter = new StateAdapter(statesArrayList);
        stateSpinner.setAdapter(stateAdapter);
        stateSpinner.setOnItemSelectedListener(this);
        new Handler().postDelayed(() -> {
            fromStatePos.set(stateAdapter.getItemIndexById(userModel.getFrom_state_id()) + 1);
        }, 1000);

    }

    public void initFromCity(MaterialSpinner citySpinner) {
        cityAdapter2 = new CityAdapter(citiesArrayList2);
        citySpinner.setAdapter(cityAdapter2);
        citySpinner.setOnItemSelectedListener(this);
//        RxBusBuilder.create(Integer.class).withKey("fromCityID")
//                .subscribe(value -> {
//
//                });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {

        if (adapterView != null) {
            switch (adapterView.getId()) {
                case R.id.relationship:
                    relationship_id = (i == -1) ? -1 : relationshipsArrayList.get(i).getId();
                    break;
                case R.id.religion:
                    religion_id = (i == -1) ? -1 : religionsArrayList.get(i).getId();
                    break;
                case R.id.military:
                    military_id = (i == -1) ? -1 : militariesArrayList.get(i).getId();
                    break;
                case R.id.political:
                    political_id = (i == -1) ? -1 : politicalsArrayList.get(i).getId();
                    break;
                /*city spinner*/
                case R.id.materialSpinner:
                    if (!citiesArrayList.isEmpty()) {
                        city_id = (i == -1) ? -1 : citiesArrayList.get(i).getId();
                    }

                    break;
                /*state spinner*/
                case R.id.materialSpinner2:
                    state_id = (i == -1) ? -1 : statesArrayList.get(i).getId();
                    cityAdapter.clear();
                    cityPos.set(-1);
                    if (i != -1) {
                        requestCurrentCityApi(state_id);
                    }
                    break;
                case R.id.fromLocationState:
                    fromStateId = (i == -1) ? -1 : statesArrayList.get(i).getId();
                    cityAdapter2.clear();
                    fromCityPos.set(-1);
                    if (i != -1) {
                        requestFromCityApi(fromStateId);
                    }
                    break;
                case R.id.fromLocationCity:
                    if (!citiesArrayList2.isEmpty()) {
                        fromCityId = (i == -1) ? -1 : citiesArrayList2.get(i).getId();
                    }
                    break;
                default:
                    break;
            }

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Do nothing
    }

    //------------------END------------------------


    private void requestMyProfileApi() {
        ApiManager.getInstance().requestApi(context, getMyProfileRequestBody(),
                AppConstants.API_MODE.MY_PROFILE, this, true);
    }

    private JSONObject getMyProfileRequestBody() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppKeys.USER_ID, userModel.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void initProfileImageList(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        adapter.addImageList(imagesArrayList);
    }

    private void initImageBroadcastReceiver() {
        RxBusBuilder.create(String.class)
                .withKey(AppConstants.ACTION_PICK_PROFILE_IMAGE)
                .subscribe(path -> {
                    requestImageCompression(1, path);
                });
        RxBusBuilder.create(String.class)
                .withKey(AppConstants.ACTION_PICK_EXTRA_PROFILE_IMAGE)
                .subscribe(path -> {
                    requestImageCompression(0, path);
                });
        RxBusBuilder.create(String.class)
                .withKey("remove_profile_image")
                .subscribe(path -> {
                    requestDeleteProfileImageApi();
                });


//        imagePickupBroadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context ctx, Intent intent) {
//                if (intent != null) {
//                    if (intent.getStringExtra("action").equals(AppConstants.ACTION_PICK_EXTRA_PROFILE_IMAGE)) {
//                        requestImageCompression(0, intent.getStringExtra(AppConstants.IMAGE_PATH));
//                    } else if (intent.getStringExtra("action").equals(AppConstants.ACTION_PICK_PROFILE_IMAGE)) {
//                        requestImageCompression(1, intent.getStringExtra(AppConstants.IMAGE_PATH));
//                    } else if (intent.getStringExtra("action").equals("remove_image")) {
//                        AppDialog.getInstance(context).showMessageDialogWithAction(context,
//                                "Are you sure you want to remove this picture?", value -> {
//                                    setProfileImage("");
//                                    requestDeleteProfileImageApi();
//                                });
//                    }
//                }
//            }
//        };
    }

    private void requestImageCompression(int i, String name) {
        // show dialog to confirm upload
        if (CommonUtils.isOnline(context)) {
//            AppDialog.getInstance(context).showTwoButtonDialog(context, "Are you sure you want to upload this image?",
//                    "Yes", "No", value -> {
//                    if (value) {
            if (i == 0) {
                UserModel.Images images = new UserModel.Images();
                UserImages userImages = new UserImages();
                userImages.setOrg(name);
                userImages.setThumb(name);
                images.setUserImages(userImages);
                adapter.addImage(images);
            } else if (i == 1) {
                setProfileImage(name);
            }
            ImageCompressionUtils imageCompressionUtils = new
                    ImageCompressionUtils(context, MyProfileActivityViewModel.this, i);
            imageCompressionUtils.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, name);

        } else {
            AppDialog.getInstance(context).showMessageDialogToDismiss(context, context.getString(R.string.no_internet));
        }
    }


    private void requestImageUploadApi(int type, File file) {

        isProfileImageUploading.set(true);
        CommonUtils.toast(context, "Uploading image...");

        AndroidNetworking.upload(ApiEndPoint.ENDPOINT_UPLOAD_PROFILE_IMAGE)
                .addMultipartParameter(AppKeys.USER_ID, String.valueOf(userModel.getId()))
                .addMultipartParameter("image_type", String.valueOf(type))
                .addMultipartFile("image", file)
                .addHeaders("Connection", "Keep-Alive")
                .setContentType("multipart/form-data")
                .addHeaders(AppKeys.TOKEN, MyApplication.TOKEN)
                .setTag("abc")
                .build()
//                .setUploadProgressListener((bytesUploaded, totalBytes) -> {
////                    Toast.makeText(context, bytesUploaded + "/" + totalBytes, Toast.LENGTH_SHORT).show();
//                    int progress = (int) (bytesUploaded / totalBytes) * 100;
//                    updateUploadProgress(progress);
//                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        isProfileImageUploading.set(false);
                        try {
                            if (response.getInt(AppKeys.STATUS) == 200) {
                                CommonUtils.toast(context, "Image uploaded successfully!");
                            } else {
                                profileImage.set("");
                                CommonUtils.toast(context, "Failed to upload image. Please try again!");
                            }
//                                updateNotificationWithResult(true);
//                            else updateNotificationWithResult(false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            CommonUtils.toast(context, "Some error occurred.");
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        isProfileImageUploading.set(false);
                        profileImage.set("");
                        CommonUtils.toast(context, "Some error occurred.");
//                updateNotificationWithResult(false);
                    }
                });
    }

    public View.OnClickListener onProfileImageClick(Context context, CircleImageView profileImageView) {
        return view -> {
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation((MyProfileActivity) context,
                            profileImageView,
                            Objects.requireNonNull(ViewCompat.getTransitionName(profileImageView)));
            Bundle bundle = options.toBundle();
            Objects.requireNonNull(bundle).putString(AppConstants.IMAGE_PATH, getProfileImage());
            FullScreenImageActivity.start(context, bundle);
//                ((MyProfileActivity) context).launchIntent(((MyProfileActivity) context),
//                        FullScreenImageActivity.class, bundle);
        };
    }

    public View.OnClickListener onAddEditProfile(Context context) {
        return view -> {
            if (!isProfileImageUploading.get()) {
                ImagePickerOptionsDialog dialog = new ImagePickerOptionsDialog();
                dialog.setArguments(getImagePickerBundle(AppConstants.IMAGE_PICKER_SOURCE.PROFILE_IMAGE, profileImage.get()));
                dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "");
            } else {
                Toast.makeText(context, "An image upload is under process. Please wait for it to finish.", Toast.LENGTH_SHORT).show();
            }
        };
    }

    public View.OnClickListener onViewResumeClick() {
        return v -> {
            Bundle bundle = new Bundle();
            bundle.putString("resume_url", userModel.getResume());
            ((BaseActivity) context).launchIntent(ResumeViewerActivity.class, bundle, false);

        };
    }

    public View.OnClickListener onDeleteResumeClick() {
        return v -> {
            requestDeleteResumeApi();

        };
    }

    private void requestDeleteResumeApi() {
        ApiManager.getInstance().requestApi(context, CommonUtils.getRequestBodyWithUserID(),
                AppConstants.API_MODE.DELETE_RESUME, this, true);
    }


    public static Bundle getImagePickerBundle(AppConstants.IMAGE_PICKER_SOURCE source, String profileImage) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.IMAGE_SOURCE, source);

        if (source == AppConstants.IMAGE_PICKER_SOURCE.PROFILE_IMAGE) {
            if (!TextUtils.isEmpty(profileImage)) {
                bundle.putBoolean(AppConstants.SHOW_REMOVE_OPTION, true);
            } else {
                bundle.putBoolean(AppConstants.SHOW_REMOVE_OPTION, false);
            }
        }

        return bundle;
    }

    @Override
    public void onImageCompressed(File compressedImage, int uploadType) {


        new Handler().postDelayed(() -> requestImageUploadApi(uploadType, compressedImage), 2000);


    }

    private void requestDeleteProfileImageApi() {
        ApiManager.getInstance().requestApi(context, CommonUtils.getRequestBodyWithUserID(),
                AppConstants.API_MODE.DELETE_PROFILE_PIC, this, false);
    }

    private void requestDeleteOtherProfileImageApi(int imageID) {
        ApiManager.getInstance().requestApi(context, getDeleteOtherPicRequestBody(imageID),
                AppConstants.API_MODE.DELETE_OTHER_PIC, this, true);
    }

//    private JSONObject getDeleteProfilePicRequestBody() {
//        JSONObject object = new JSONObject();
//        try {
//            object.put(AppKeys.USER_ID, userModel.getId());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return object;
//    }

    private JSONObject getDeleteOtherPicRequestBody(int image_id) {
        JSONObject object = new JSONObject();
        try {
            object.put(AppKeys.USER_IMAGE_ID, image_id);
            object.put(AppKeys.USER_ID, userModel.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.emailSwitch:
                emailAccess.set(isChecked);
                break;
            case R.id.occupationSwitch:
                occupationAccess.set(isChecked);
                break;
            case R.id.almaMatterSwitch:
                almaMatterAccess.set(isChecked);
                break;
            case R.id.collegeSwitch:
                collegeAccess.set(isChecked);
                break;
            case R.id.educationSwitch:
                educationAccess.set(isChecked);
                break;
            case R.id.highSchoolSwitch:
                highSchoolAccess.set(isChecked);
                break;
            case R.id.likesHobbiesSwitch:
                likesAccess.set(isChecked);
                break;
            case R.id.militarySwitch:
                militaryAccess.set(isChecked);
                break;
            case R.id.politicalSwitch:
                politicalAccess.set(isChecked);
                break;
            case R.id.religionSwitch:
                religionAccess.set(isChecked);
                break;
            case R.id.workWebsiteSwitch:
                workAccess.set(isChecked);
                break;
            // location switch
            case R.id.include:
                locationAccess.set(isChecked);
                break;
            // relationship switch
            case R.id.include4:
                relationshipAccess.set(isChecked);
                break;
            case R.id.facebookSwitch:
                facebookAccess.set(isChecked);
                break;
            case R.id.linkedinSwitch:
                linkedinAccess.set(isChecked);
                break;
            case R.id.instagramSwitch:
                instagramAccess.set(isChecked);
                break;
            // family
            case R.id.include5:
                familyAccess.set(isChecked);
                break;
            case R.id.phoneSwitch:
                phoneAccess.set(isChecked);
                break;
            case R.id.twitterSwitch:
                twitterAccess.set(isChecked);
                break;
            case R.id.fromLocationSwitch:
                fromLocationAccess.set(isChecked);
                break;

            default:
                break;
        }
    }

    public View.OnClickListener onUpdateClick() {
        return view -> {
            if (Validation.isValidProfileForm(context, email.get(), firstName.get(), state_id, city_id, military_id, political_id,
                    religion_id, relationship_id, isFirstRun, profileImage.get(), fromStateId, fromCityId)) {
                requestUpdateProfileApi();
            }
        };
    }

    public View.OnClickListener OnFamilyMemberClick() {
        return view -> {
            ((BaseActivity) context).launchIntent(FamilyMemberList.class, null, false);
        };
    }

    public View.OnClickListener onUploadResumeClick() {
        return view -> {
            browseDocuments();
        };
    }

    private void browseDocuments() {

        String[] mimeTypes =
                {"application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                        "application/pdf"};

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        } else {
            StringBuilder mimeTypesStr = new StringBuilder();
            for (String mimeType : mimeTypes) {
                mimeTypesStr.append(mimeType).append("|");
            }
            intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
        }
        context.startActivityForResult(Intent.createChooser(intent, "ChooseFile"), 789);

    }

    private void requestUpdateProfileApi() {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Processing...");
        progressDialog.show();

        ANRequest.MultiPartBuilder builder = new ANRequest.MultiPartBuilder(ENDPOINT_PROFILE_EDIT);
        if (resumeFile != null) {
            builder.addMultipartFile("resume", resumeFile);
        }

        builder.addMultipartParameter(getRequestBody())
                .addHeaders("token", MyApplication.TOKEN)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    if (response.getInt(AppKeys.STATUS) == 200) {
                        /*
                         * If first run, then navigate to notification preferences settings from here,
                         * else show profile update dialog
                         * */

                        if (isFirstRun) {
                            ((BaseActivity) context).launchIntent(NotificationsPreferencesActivity.class,
                                    CommonUtils.getFirstRunBundle(), false);
                        } else {
                            AppDialog.getInstance(context).showMessageDialogToDismiss(context, response.getString(AppKeys.MESSAGE));
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(ANError anError) {
                progressDialog.dismiss();
                AppDialog.getInstance(context).showMessageDialogToDismiss(context, context.getString(R.string.connection_error));

            }
        });

    }

    private HashMap getRequestBody() {
        HashMap<String, String> m = new HashMap<>();
        m.put(AppKeys.USER_ID, userModel.getId() + "");
        m.put(AppKeys.FIRST_NAME, firstName.get());
        m.put(AppKeys.LAST_NAME, getValidData(lastName.get()));
        m.put(AppKeys.EMAIL, email.get());
        m.put("email_access", getStringFromBoolean(emailAccess.get()));
        m.put("last_name_access", getStringFromBoolean(emailAccess.get()));
        m.put("state_id", state_id + "");
        m.put("city_id", city_id + "");
        m.put("city_id_access", getStringFromBoolean(locationAccess.get()));
        m.put("occupation", getValidData(occupation.get()));
        m.put("occupation_access", getStringFromBoolean(occupationAccess.get()));
        m.put("work_website", getValidData(workWebsite.get()));
        m.put("work_website_access", getStringFromBoolean(workAccess.get()));
        m.put("education", getValidData(education.get()));
        m.put("education_access", getStringFromBoolean(educationAccess.get()));
        m.put("high_school", getValidData(highSchool.get()));
        m.put("high_school_access", getStringFromBoolean(highSchoolAccess.get()));
        m.put("college", getValidData(college.get()));
        m.put("college_access", getStringFromBoolean(collegeAccess.get()));
        m.put("alma_matter", getValidData(almaMatter.get()));
        m.put("alma_matter_access", getStringFromBoolean(almaMatterAccess.get()));
        m.put("likes", getValidData(likes.get()));
        m.put("likes_access", getStringFromBoolean(likesAccess.get()));
        m.put("military_id", military_id + "");
        m.put("military_id_access", getStringFromBoolean(militaryAccess.get()));
        m.put("political_id", political_id + "");
        m.put("political_id_access", getStringFromBoolean(politicalAccess.get()));
        m.put("religion_id", religion_id + "");
        m.put("religion_id_access", getStringFromBoolean(religionAccess.get()));
        m.put("relationship_id", relationship_id + "");
        m.put("relationship_id_access", getStringFromBoolean(relationshipAccess.get()));
        m.put("family_access", getStringFromBoolean(familyAccess.get()));
        m.put("phone", phone.get());
        m.put("phone_access", getStringFromBoolean(phoneAccess.get()));

        m.put("linked_in_link", getValidData(linkedin.get()));
        m.put("linked_in_link_access", getStringFromBoolean(linkedinAccess.get()));

        m.put("fb_link", getValidData(facebook.get()));
        m.put("fb_link_access", getStringFromBoolean(facebookAccess.get()));

        m.put("insta_link", getValidData(instagram.get()));
        m.put("insta_link_access", getStringFromBoolean(instagramAccess.get()));
        m.put("twit_link", getValidData(twitter.get()));
        m.put("twit_link_access", getStringFromBoolean(twitterAccess.get()));

        m.put("from_state_id", fromStateId + "");
        m.put("from_city_id", fromCityId + "");
        m.put("from_city_id_access", getStringFromBoolean(fromLocationAccess.get()));
        return m;
    }

    private String getStringFromBoolean(boolean value) {
        return value ? "1" : "0";
    }

    private String getValidData(String input) {
        return (!TextUtils.isEmpty(input)) ? input : "N/A";
    }

    private void requestCurrentCityApi(int stateId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("state_id", stateId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiManager.getInstance().requestApi(context, jsonObject, AppConstants.API_MODE.GET_CITIES,
                new ApiResponse() {
                    @Override
                    public void onSuccess(JsonObject response, AppConstants.API_MODE apiMode) {

                        citiesArrayList = gson.fromJson(response.getAsJsonArray(AppKeys.DATA),
                                new TypeToken<ArrayList<Cities>>() {
                                }.getType());
                        city_id = (userModel.getCity_id() == 0 ? -1 : userModel.getCity_id());
                        cityAdapter.addCities(citiesArrayList);
                        cityPos.set(cityAdapter.getItemIndexById(city_id) + 1);
                    }

                    @Override
                    public void onFailure(String message, AppConstants.API_MODE apiMode) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }
                }, false);
    }

    private void requestFromCityApi(int stateId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("state_id", stateId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiManager.getInstance().requestApi(context, jsonObject, AppConstants.API_MODE.GET_CITIES,
                new ApiResponse() {
                    @Override
                    public void onSuccess(JsonObject response, AppConstants.API_MODE apiMode) {

                        citiesArrayList2 = gson.fromJson(response.getAsJsonArray(AppKeys.DATA),
                                new TypeToken<ArrayList<Cities>>() {
                                }.getType());
                        fromCityId = (userModel.getFrom_city_id() == 0 ? -1 : userModel.getFrom_city_id());
                        cityAdapter2.addCities(citiesArrayList2);
                        fromCityPos.set(cityAdapter2.getItemIndexById(fromCityId) + 1);

                    }

                    @Override
                    public void onFailure(String message, AppConstants.API_MODE apiMode) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }
                }, false);
    }

}
