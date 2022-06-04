package com.devsectech.math.service;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.devsectech.math.database.DatabaseAccess;
import com.devsectech.math.model.ReminderModel;
import com.devsectech.math.receiver.NotificationScheduler;
import com.devsectech.math.utils.Constant;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.devsectech.math.utils.Constant.simpleDateFormat;


public class AlarmReceiver extends BroadcastReceiver {

    DatabaseAccess manager;
    List<ReminderModel> reminderModels = new ArrayList<>();
    List<String> timeList = new ArrayList<>();

    @Override
    public void onReceive(Context context, Intent intent) {
        manager = DatabaseAccess.getInstance(context);
        manager.open();
        reminderModels = manager.getReminderData();
        manager.close();
        manager.open();
        timeList = manager.getReminderTimeList();
        manager.close();
        Calendar calendarTime = Calendar.getInstance();
        String currentTime = simpleDateFormat.format(calendarTime.getTime());

        Log.e("receive111", "==true");
        Gson gson = new Gson();
        String s = gson.toJson(reminderModels);
        String currentday = new SimpleDateFormat("EE", Locale.ENGLISH).format(calendarTime.getTime());



        if (Constant.getIsReminder(context)){
            if (timeList.contains(currentTime)) {
                int i = timeList.indexOf(currentTime);
                if (reminderModels.get(i).ison.equals("1")) {
                    ArrayList myList = new Gson().fromJson(reminderModels.get(i).repeat, ArrayList.class);
                    if (myList.contains(currentday)) {
                        NotificationScheduler.showReminderNotification(context, currentTime);
                    }
                }
            }
        }





    }
}