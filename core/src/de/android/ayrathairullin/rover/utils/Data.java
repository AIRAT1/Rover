package de.android.ayrathairullin.rover.utils;


import com.boontaran.DataManager;

public class Data {
    private DataManager manager;
    private static final String PREFERENCE_NAME = "Rover_data";
    private static final String PROGRESS_KEY = "progress";

    public Data() {
        manager = new DataManager(PREFERENCE_NAME);
    }

    public int getProgress() {
        return manager.getInt(PROGRESS_KEY, 1);
    }

    public void setProgress(int progress) {
        manager.saveInt(PREFERENCE_NAME, progress);
    }
}
