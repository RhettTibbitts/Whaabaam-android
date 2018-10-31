//package dev.whaabaam.com.ui.home.closecontacts;
//
//
//import android.annotation.SuppressLint;
//import android.databinding.DataBindingUtil;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//
//import dev.whaabaam.com.R;
//import dev.whaabaam.com.app.AppConstants;
//import dev.whaabaam.com.data.model.other.CalendarTabData;
//import dev.whaabaam.com.databinding.FragmentCloseContactsBinding;
//import dev.whaabaam.com.utils.CommonUtils;
//import dev.whaabaam.com.utils.StartSnapHelper;
//
//import static dev.whaabaam.com.utils.CommonUtils.getDayOfMonthWithSuffix;
//import static dev.whaabaam.com.utils.CommonUtils.getMonthNameByDate;
//
//public class CloseContactsFragment extends Fragment implements OnCalendarTabSelectedCallback {
//    private CalendarTabAdapter calendarTabAdapter;
//
//    private FragmentCloseContactsBinding binding;
//    private LinearLayoutManager layoutManager;
//    private CloseContactsViewModel model;
//
//    public CloseContactsFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        binding = DataBindingUtil.inflate(inflater,
//                R.layout.fragment_close_contacts, container, false);
//        model = new CloseContactsViewModel(getActivity());
//        binding.setViewModel(model);
//        model.setupContactsList(binding.closeContactsList);
//        initiateTabSetup(binding.calendarTab);
//        return binding.getRoot();
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        getCalendarDates(new Date(), true, AppConstants.TIME.PREVIOUS);
//    }
//
//    private void initiateTabSetup(RecyclerView calendarTab) {
//        calendarTab.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(getActivity(),
//                LinearLayoutManager.HORIZONTAL, false);
//        calendarTab.setLayoutManager(layoutManager);
//        calendarTabAdapter = new CalendarTabAdapter(this);
//        new StartSnapHelper().attachToRecyclerView(calendarTab);
//        calendarTab.setAdapter(calendarTabAdapter);
////        calendarTab.addOnScrollListener(new RecyclerView.OnScrollListener() {
////            @Override
////            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
////                super.onScrolled(recyclerView, dx, dy);
////                LinearLayoutManager layoutManager = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
////                int totalItemCount = layoutManager.getItemCount();
////                int lastVisible = layoutManager.findLastVisibleItemPosition();
////                int firstVisible = layoutManager.findFirstVisibleItemPosition();
////
////                boolean endHasBeenReached = lastVisible + 2 >= totalItemCount;
//////                boolean startHasBeenReached = firstVisible - 2 <= totalItemCount;
////                if (totalItemCount > 0) {
////                    if (endHasBeenReached)
////                        getCalendarDates(getYesterDayDate(calendarTabAdapter.getLastDate()),
////                                false, AppConstants.TIME.PREVIOUS);
//////                    if (startHasBeenReached)
//////                        getCalendarDates(calendarTabAdapter.getFirstDate(), false, AppConstants.TIME.FUTURE);
////                }
////            }
////        });
//    }
//
//    private Date getYesterDayDate(Date currentDate) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(currentDate);
//        calendar.add(Calendar.DATE, -1);
//        return calendar.getTime();
//    }
//
//    private Date getTomorrowDate(Date currentDate) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(currentDate);
//        calendar.add(Calendar.DATE, 1);
//        return calendar.getTime();
//    }
//
//    @SuppressLint("StaticFieldLeak")
//    private void getCalendarDates(final Date inputDate, final boolean isFirstTime, final AppConstants.TIME time) {
//
//        new AsyncTask<Void, Void, Void>() {
//            Calendar calendar = Calendar.getInstance();
//            ArrayList<CalendarTabData> dates;
//            int count, numIterations;
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                dates = new ArrayList<>();
//                count = 0;
//                numIterations = isFirstTime ? 10 : 5;
//            }
//
//            @Override
//            protected Void doInBackground(Void... voids) {
//                calendar.setTimeInMillis(inputDate.getTime());
//                while (count < 5 && inputDate.before(new Date())) {
//                    if (CommonUtils.getDDMMYY(calendar.getTime()).equals(CommonUtils.getDDMMYY(new Date())))
//                        calendarTabAdapter.add(new CalendarTabData("Today", true, calendar.getTime()));
//                    else if (CommonUtils.getDDMMYY(calendar.getTime()).equals(CommonUtils.getDDMMYY(getYesterDayDate(new Date()))))
//                        calendarTabAdapter.add(new CalendarTabData("Yesterday", false, calendar.getTime()));
//                    else
//                        calendarTabAdapter.add(new CalendarTabData(getDayOfMonthWithSuffix(calendar.get(Calendar.DAY_OF_MONTH))
//                                + getMonthNameByDate(calendar.getTime()) + attachYearToDate(calendar.get(Calendar.YEAR)),
//                                false, calendar.getTime()));
//                    if (!isFirstTime) {
//                        if (time == AppConstants.TIME.PREVIOUS)
//                            calendar.add(Calendar.DATE, -1);
//                        else
//                            calendar.add(Calendar.DATE, +1);
//                    } else calendar.add(Calendar.DATE, -1);
//
//                    count++;
//                }
//                return null;
//            }
//        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//    }
//
//    private String attachYearToDate(int inputYear) {
//        return CommonUtils.getCurrentYear() != inputYear ? ", " + inputYear : "";
//    }
//
//
//    @Override
//    public void onCalendarTabSelected(CalendarTabData data, int position) {
//        model.selectedDate.set(data.getDate());
//        data.setSelected(true);
//        unSelectOtherTabs(data);
//        layoutManager.scrollToPosition(position);
//
//    }
//
//    /*
//     * BEGIN: UnSelect other tabs when one is selected in order to make RecyclerView function Like TabLayout
//     * */
//    private void unSelectOtherTabs(CalendarTabData data) {
//        for (CalendarTabData tab : calendarTabAdapter.getList()) {
//            if (tab != data && tab.isSelected())
//                tab.setSelected(false);
//        }
//        calendarTabAdapter.notifyDataSetChanged();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
////        if (calendarTabAdapter != null)
////            calendarTabAdapter.notifyDataSetChanged();
//    }
//}
package dev.whaabaam.com.ui.home.closecontacts;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.Date;

import dev.whaabaam.com.R;
import dev.whaabaam.com.app.AppConstants;
import dev.whaabaam.com.data.model.other.CalendarTabData;
import dev.whaabaam.com.databinding.FragmentCloseContactsBinding;
import dev.whaabaam.com.utils.CommonUtils;
import dev.whaabaam.com.utils.StartSnapHelper;

import static dev.whaabaam.com.utils.CommonUtils.getDayOfMonthWithSuffix;
import static dev.whaabaam.com.utils.CommonUtils.getMonthNameByDate;

public class CloseContactsFragment extends Fragment implements OnCalendarTabSelectedCallback {
    private CalendarTabAdapter calendarTabAdapter;

    private FragmentCloseContactsBinding binding;
    private LinearLayoutManager layoutManager;
    private CloseContactsViewModel model;

    public CloseContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_close_contacts, container, false);
        model = new CloseContactsViewModel(getActivity());
        binding.setViewModel(model);
        model.setupContactsList(binding.closeContactsList);
        initiateTabSetup(binding.calendarTab);
        model.initSwipeRefresh(binding.swipeRefreshCloseContacts);
        return binding.getRoot();
    }

    private void initiateTabSetup(RecyclerView calendarTab) {
        calendarTab.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        calendarTab.setLayoutManager(layoutManager);
        calendarTabAdapter = new CalendarTabAdapter(this);
        new StartSnapHelper().attachToRecyclerView(calendarTab);
        calendarTab.setAdapter(calendarTabAdapter);
        getCalendarDates(new Date(), true, AppConstants.TIME.PREVIOUS);
    }

    private Date getYesterDayDate(Date currentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    private Date getTomorrowDate(Date currentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }


    private void getCalendarDates(final Date inputDate, final boolean isFirstTime, final AppConstants.TIME time) {
        calendarTabAdapter.clear();
        int count = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(inputDate.getTime());
        while (count <= 20) {
            if (CommonUtils.getDDMMYY(calendar.getTime()).equals(CommonUtils.getDDMMYY(new Date())))
                calendarTabAdapter.add(new CalendarTabData("Today", true, calendar.getTime()));
            else if (CommonUtils.getDDMMYY(calendar.getTime()).equals(CommonUtils.getDDMMYY(getYesterDayDate(new Date()))))
                calendarTabAdapter.add(new CalendarTabData("Yesterday", false, calendar.getTime()));
            else
                calendarTabAdapter.add(new CalendarTabData(getWeekdayName(calendar.get(Calendar.DAY_OF_WEEK))
                        + getMonthNameByDate(calendar.getTime()) + attachYearToDate(calendar.get(Calendar.YEAR))
                        + /*getDayOfMonthWithSuffix(*/calendar.get(Calendar.DAY_OF_MONTH/*)*/),
                        false, calendar.getTime()));
            if (!isFirstTime) {
                if (time == AppConstants.TIME.PREVIOUS)
                    calendar.add(Calendar.DATE, -1);
                else
                    calendar.add(Calendar.DATE, +1);
            } else calendar.add(Calendar.DATE, -1);
            count++;
        }
    }

    private String getWeekdayName(int day) {
        switch (day) {
            case 1:
                return "Sunday, ";
            case 2:
                return "Monday, ";
            case 3:
                return "Tuesday, ";
            case 4:
                return "Wednesday, ";
            case 5:
                return "Thursday, ";
            case 6:
                return "Friday, ";
            default:
                return "Saturday, ";
        }
    }

    private String attachYearToDate(int inputYear) {
        return CommonUtils.getCurrentYear() != inputYear ? ", " + inputYear : "";
    }


    @Override
    public void onCalendarTabSelected(CalendarTabData data, int position) {
        data.setSelected(true);
        unSelectOtherTabs(data);
        binding.calendarTab.smoothScrollToPosition(position);
        model.selectedDate.set(data.getDate());
        model.selectedDatePosition=position;
    }

    /*
     * BEGIN: UnSelect other tabs when one is selected in order to make RecyclerView function Like TabLayout
     * */
    private void unSelectOtherTabs(CalendarTabData data) {
        for (CalendarTabData tab : calendarTabAdapter.getList()) {
            if (tab != data && tab.isSelected())
                tab.setSelected(false);
        }
        calendarTabAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        calendarTabAdapter.setSelection(model.selectedDatePosition);
    }
}
