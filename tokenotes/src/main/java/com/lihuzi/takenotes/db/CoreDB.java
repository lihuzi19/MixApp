package com.lihuzi.takenotes.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lihuzi.takenotes.application.TakeNotesApplication;
import com.lihuzi.takenotes.model.NotesModel;
import com.lihuzi.takenotes.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by cocav on 2017/11/13.
 */

public class CoreDB
{
    private static String CORE_MONEY_TABLE_NAME = "core_money";

    private static String CORE_MONEY_ID = "order_id";
    private static String CORE_MONEY_GOODS_NAME = "goods_name";
    private static String CORE_MONEY_GOODS_SUM = "goods_sum";
    private static String CORE_MONEY_GOODS_TYPE = "goods_type";
    private static String CORE_MONEY_GOODS_DESC = "goods_desc";
    private static String CORE_MONEY_DATE = "goods_date";

    private static final String SQL_CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + //
            CORE_MONEY_TABLE_NAME + " (" +//
            CORE_MONEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + //
            CORE_MONEY_GOODS_NAME + " VARCHAR(50)," + //
            CORE_MONEY_GOODS_SUM + " INTEGER," +//
            CORE_MONEY_GOODS_TYPE + " INTEGER," + //
            CORE_MONEY_GOODS_DESC + " VARCHAR(512)," +//
            CORE_MONEY_DATE + " INTEGER);";
    private static String[] CONTACT_DATA_COLUMNS;
    private static SQLiteDatabase _db;

    static
    {
        CONTACT_DATA_COLUMNS = new String[6];
        CONTACT_DATA_COLUMNS[0] = CORE_MONEY_ID;
        CONTACT_DATA_COLUMNS[1] = CORE_MONEY_GOODS_NAME;
        CONTACT_DATA_COLUMNS[2] = CORE_MONEY_GOODS_SUM;
        CONTACT_DATA_COLUMNS[3] = CORE_MONEY_GOODS_TYPE;
        CONTACT_DATA_COLUMNS[4] = CORE_MONEY_GOODS_DESC;
        CONTACT_DATA_COLUMNS[5] = CORE_MONEY_DATE;
    }

    public static void initAndOpen()
    {
        if (_db == null)
        {
            synchronized (CoreDB.class)
            {
                if (_db == null)
                {
                    _db = TakeNotesApplication.getInstance().openOrCreateDatabase(TakeNotesApplication.getInstance().getFilesDir().getAbsolutePath() + "core._db", Context.MODE_PRIVATE, null);
                    _db.execSQL(SQL_CREATE_CONTACTS_TABLE);
                }
            }
        }
    }

    public synchronized static void insertNotes(String name, float sum, int type, String desc, long date)
    {
        initAndOpen();
        ContentValues values = new ContentValues();
        values.put(CORE_MONEY_GOODS_NAME, name);
        values.put(CORE_MONEY_GOODS_SUM, sum);
        values.put(CORE_MONEY_GOODS_TYPE, type);
        values.put(CORE_MONEY_GOODS_DESC, desc);
        values.put(CORE_MONEY_DATE, date);
        _db.insert(CORE_MONEY_TABLE_NAME, null, values);
    }

    public synchronized static ArrayList<NotesModel> queryPay()
    {
        initAndOpen();
        ArrayList<NotesModel> result = new ArrayList<>();
        Cursor cursor = _db.query(CORE_MONEY_TABLE_NAME, CONTACT_DATA_COLUMNS, null, null, null, null, CORE_MONEY_DATE + " ASC");
        if (cursor.moveToFirst())
        {
            do
            {
                NotesModel model = new NotesModel();
                model._db_id = cursor.getInt(cursor.getColumnIndex(CORE_MONEY_ID));
                model._name = cursor.getString(cursor.getColumnIndex(CORE_MONEY_GOODS_NAME));
                model._sum = cursor.getFloat(cursor.getColumnIndex(CORE_MONEY_GOODS_SUM));
                model._type = cursor.getInt(cursor.getColumnIndex(CORE_MONEY_GOODS_TYPE));
                model._desc = cursor.getString(cursor.getColumnIndex(CORE_MONEY_GOODS_DESC));
                model._date = cursor.getLong(cursor.getColumnIndex(CORE_MONEY_DATE));
                result.add(model);
            }
            while (cursor.moveToNext());
        }
        return result;
    }

    public synchronized static ArrayList<NotesModel> query(long start, long end)
    {
        initAndOpen();
        ArrayList<NotesModel> result = new ArrayList<>();
        Cursor cursor = _db.query(CORE_MONEY_TABLE_NAME, CONTACT_DATA_COLUMNS, CORE_MONEY_DATE + "<? AND " + CORE_MONEY_DATE + ">?", new String[]{String.valueOf(start), String.valueOf(end)}, null, null, null);
        if (cursor.moveToFirst())
        {
            do
            {
                NotesModel model = new NotesModel();
                model._db_id = cursor.getInt(cursor.getColumnIndex(CORE_MONEY_ID));
                model._name = cursor.getString(cursor.getColumnIndex(CORE_MONEY_GOODS_NAME));
                model._sum = cursor.getFloat(cursor.getColumnIndex(CORE_MONEY_GOODS_SUM));
                model._type = cursor.getInt(cursor.getColumnIndex(CORE_MONEY_GOODS_TYPE));
                model._desc = cursor.getString(cursor.getColumnIndex(CORE_MONEY_GOODS_DESC));
                model._date = cursor.getLong(cursor.getColumnIndex(CORE_MONEY_DATE));
                result.add(model);
            }
            while (cursor.moveToNext());
        }
        return result;
    }

    public synchronized static ArrayList<NotesModel> queryThisMonth(long now)
    {
        Date d = DateUtils.getDate(now);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        long endMonth = calendar.getTimeInMillis() - 1;
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        long startMonth = calendar.getTimeInMillis() + (24 * 60 * 60 * 1000);
        return query(startMonth, endMonth);
    }

    public synchronized static void deleteById(int id)
    {
        initAndOpen();
        _db.delete(CORE_MONEY_TABLE_NAME, CORE_MONEY_ID + "=?", new String[]{String.valueOf(id)});
    }
}
