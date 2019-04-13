package com.example.user.ex4_group5;

import android.app.Application;
import android.content.Context;

import com.parse.Parse;

/**
 * Created by user on 10/24/2015.
 */
public class RestApplication extends Application {

    private static Context context;

    public static Context getAppContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

         Parse.enableLocalDatastore(this);

        Parse.initialize(this, "Nw3fc9Vo3qrPzZhxWs36wtDi0kdF5XzvJMJtpfaj", "NyKv7Q44TMXTDcQaO97cK3qYXz9C4k98zPyap6Fw");
    }
}
