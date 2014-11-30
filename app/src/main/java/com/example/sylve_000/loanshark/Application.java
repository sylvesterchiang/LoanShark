package com.example.sylve_000.loanshark;

        import android.content.Context;
        import android.content.SharedPreferences;

        import com.parse.Parse;
        import com.parse.ParseObject;

public class Application extends android.app.Application {
    // Debugging switch
    public static final boolean APPDEBUG = false;

    // Debugging tag for the application
    public static final String APPTAG = "AnyWall";

    // Used to pass location from MainActivity to PostActivity
    public static final String INTENT_EXTRA_LOCATION = "location";

    // Key for saving the search distance preference
    private static final String KEY_SEARCH_DISTANCE = "searchDistance";

    private static final float DEFAULT_SEARCH_DISTANCE = 250.0f;

    private static SharedPreferences preferences;

    //private static ConfigHelper configHelper;

    public Application() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Friends.class);
        Parse.initialize(this, "ubmj6oAAyAAD7ARhLkuaTiOkvEpbqjWhi13NEIia", "R7xHxk1EAS24kVUiGuxzJDHTASRI6MXyWBGl7Wnk");

        preferences = getSharedPreferences("com.parse.anywall", Context.MODE_PRIVATE);

        //configHelper = new ConfigHelper();
        //configHelper.fetchConfigIfNeeded();
    }

    public static float getSearchDistance() {
        return preferences.getFloat(KEY_SEARCH_DISTANCE, DEFAULT_SEARCH_DISTANCE);
    }

    //public static ConfigHelper getConfigHelper() {
    //    return configHelper;
    //}

    public static void setSearchDistance(float value) {
        preferences.edit().putFloat(KEY_SEARCH_DISTANCE, value).commit();
    }

}