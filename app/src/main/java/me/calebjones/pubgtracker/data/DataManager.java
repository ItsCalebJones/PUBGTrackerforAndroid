package me.calebjones.pubgtracker.data;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

@Singleton
public class DataManager {

    private static DataManager mInstance;
    private DataSaver dataSaver;

    @Inject
    public DataManager() {
        Timber.d("DataManager creating new.");
        this.dataSaver = new DataSaver();
    }

    public static void create() {
        mInstance = new DataManager();
    }

    public static DataManager getInstance() {
        if (mInstance == null) {
            throw new AssertionError("Did you forget to call create() ?");
        }
        return mInstance;
    }

    public DataSaver getDataSaver() {
        return dataSaver;
    }

    public void getTest(){
    }

}
