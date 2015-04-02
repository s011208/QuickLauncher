package yhh.bj4.quicklauncher.notification;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteFullException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import yhh.bj4.quicklauncher.IconInfo;

/**
 * Created by yenhsunhuang on 15/4/3.
 */
public class NotificationLauncherIconInfoDatabase extends SQLiteOpenHelper {
    private static final String TAG = "IconInfoDatabase";
    private static final String DATABASE_NAME = "IconInfo.db";
    private static final int DATABASE_VERSION = 1;

    private static final String ID = "_id";
    private static final String PACKAGE_NAME = "package_name";
    private static final String CLASS_NAME = "class_name";
    private static final String TITLE = "title";
    private static final String ICON = "icon";
    private static final String ICON_TYPE = "icon_type";
    private static final String RANK = "rank";

    private static NotificationLauncherIconInfoDatabase sInstance;

    public static synchronized NotificationLauncherIconInfoDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new NotificationLauncherIconInfoDatabase(context.getApplicationContext());
        }
        return sInstance;
    }

    private SQLiteDatabase mDb;

    private NotificationLauncherIconInfoDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        try {
            mDb = getWritableDatabase();
        } catch (SQLiteFullException e) {
            Log.w(TAG, "SQLiteFullException", e);
        } catch (SQLiteException e) {
            Log.w(TAG, "SQLiteException", e);
        } catch (Exception e) {
            Log.w(TAG, "Exception", e);
        }
    }

    public void createTablesIfNeeded(String table) {
        final String cmd = "CREATE TABLE if not exists " + table + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PACKAGE_NAME + " TEXT, "
                + CLASS_NAME + " TEXT, "
                + TITLE + " TEXT, "
                + ICON + " BLOB, "
                + ICON_TYPE + " INTEGER, "
                + RANK + " INTEGER)";
        getDataBase().execSQL(cmd);
    }

    public boolean isEmpty(String table) {
        Cursor count = getDataBase().rawQuery("select count(*) from " + table, null);
        if (count != null) {
            try {
                while (count.moveToNext()) {
                    final int counter = count.getInt(0);
                    return counter == 0;
                }
            } finally {
                count.close();
            }
        }
        return true;
    }

    public static byte[] getBlob(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public static Bitmap getBitmap(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public void updateIconInfo(String table, IconInfo info) {
        ContentValues cv = new ContentValues();
        cv.put(PACKAGE_NAME, info.mPackageName);
        cv.put(CLASS_NAME, info.mClassName);
        cv.put(TITLE, info.mTitle);
        cv.put(ICON, getBlob(info.mIcon));
        cv.put(ICON_TYPE, info.mIconType);
        cv.put(RANK, info.mRank);
        getDataBase().update(table, cv, ID + "=" + info.mId, null);
    }

    public void setIconInfos(String table, ArrayList<IconInfo> infos) {
        for (IconInfo info : infos) {
            ContentValues cv = new ContentValues();
            cv.put(PACKAGE_NAME, info.mPackageName);
            cv.put(CLASS_NAME, info.mClassName);
            cv.put(TITLE, info.mTitle);
            cv.put(ICON, getBlob(info.mIcon));
            cv.put(ICON_TYPE, info.mIconType);
            cv.put(RANK, info.mRank);
            final long result = getDataBase().insert(table, null, cv);
            if (result == -1) {
                getDataBase().update(table, cv, ID + "=" + info.mId, null);
            }
        }
    }

    public ArrayList<IconInfo> getIconInfos(String table) {
        final ArrayList<IconInfo> rtn = new ArrayList<IconInfo>();
        Cursor data = getDataBase().query(table, null, null, null, null, null, null);
        if (data != null) {
            try {
                final int indexId = data.getColumnIndex(ID);
                final int indexPkg = data.getColumnIndex(PACKAGE_NAME);
                final int indexClz = data.getColumnIndex(CLASS_NAME);
                final int indexTitle = data.getColumnIndex(TITLE);
                final int indexIcon = data.getColumnIndex(ICON);
                final int indexIconType = data.getColumnIndex(ICON_TYPE);
                final int indexRank = data.getColumnIndex(RANK);
                while (data.moveToNext()) {
                    rtn.add(new IconInfo(data.getString(indexPkg), data.getString(indexClz), data.getString(indexTitle)
                            , data.getInt(indexId), data.getInt(indexIconType), data.getInt(indexRank), getBitmap(data.getBlob(indexIcon))));
                }
            } finally {
                data.close();
            }
        }
        return rtn;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    public SQLiteDatabase getDataBase() {
        if (mDb != null && mDb.isOpen() == false) {
            try {
                mDb = getWritableDatabase();
            } catch (SQLiteFullException e) {
                Log.w(TAG, "SQLiteFullException", e);
            } catch (SQLiteException e) {
                Log.w(TAG, "SQLiteException", e);
            } catch (Exception e) {
                Log.w(TAG, "Exception", e);
            }
        }
        return mDb;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
