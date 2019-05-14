package com.example.gym4u;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class MyDB extends SQLiteOpenHelper {
    // SQLiteDatabase db;
    // Context ctx;
    private static final String DATABASE_NAME = "gym_database";
    private static final String TABLE_POSTS = "gymPosts";
    private static final String TABLE_ANNOUNCE = "announcements";
    private static final int VERSION = 1;
    private static final String KEY_ID = "post_ID";
    private static final String KEY_NAME = "name ";
    private static final String KEY_POST = "post";
    private static final String KEY_IMAGE = "image ";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";
    private static final String CREATE_TABLE_IMAGE = "CREATE TABLE " + TABLE_POSTS + "( " +KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ KEY_NAME + " TEXT NOT NULL," + KEY_POST + " TEXT NOT NULL, "+ KEY_DATE + " TEXT NOT NULL, " + KEY_TIME + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_ANNOUNCE = "CREATE TABLE " + TABLE_ANNOUNCE + "( " +KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ KEY_NAME + " TEXT NOT NULL," + KEY_POST + " TEXT NOT NULL, "+ KEY_DATE + " TEXT NOT NULL, " + KEY_TIME + " TEXT NOT NULL);";

    public MyDB(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_ANNOUNCE);

        db.execSQL(CREATE_TABLE_IMAGE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANNOUNCE);
        onCreate(db);
    }



    public boolean betaInsert(String table,String name, String post, String date, String time) throws SQLException {
        SQLiteDatabase database= this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String NAME_TABLE  = table;

        cv.put(KEY_NAME, name);
        //cv.put(KEY_IMAGE, image);
        cv.put(KEY_POST, post);
        cv.put(KEY_DATE, date);
        cv.put(KEY_TIME, time);
        long result = database.insert(NAME_TABLE, null, cv);
        if (result == -1) {
            return (false);
        } else {
            return (true);
        }
    }

    public void insert(String name, byte[] image, String post, String date, String time) throws SQLException {
        SQLiteDatabase database= this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_NAME, name);
        cv.put(KEY_IMAGE, image);
        cv.put(KEY_POST, post);
        cv.put(KEY_DATE, date);
        cv.put(KEY_TIME, time);
        database.insert(TABLE_POSTS, null, cv);
    }
    /*
    public void delete(int id, String title){
        SQLiteDatabase db = this.getWritableDatabase();
        if(id != 0 && title == null){
            db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE image_ID = \"" + id + "\";");
        }
        else if(id == 0 && title != null){
            db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE image_title = \"" + title + "\";");
        }
        //else if(id != 0 && title != null){
        else{
            db.execSQL("DELETE FROM "+ TABLE_NAME + " WHERE image_title = \"" + title + "\" OR image_ID = \"" + id + "\";" );
        }
        //Toast.makeText(, "deleted table", Toast.LENGTH_LONG).show();
    }

    public int count(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + ";", null);
        return c.getCount();

    }
    /*
    public MyDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        ctx = context;
        VERSION = version;
        DB_NAME = name;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(id_INTEGER PRIMARY KEY, TITLE, BLOB );");
        Toast.makeText(ctx, "TAbLe created ", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (VERSION == oldVersion){
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
            VERSION = newVersion;
            onCreate(db);
            Toast.makeText(ctx,"UPGraded", Toast.LENGTH_LONG).show();
        }
    }

    public void insert(String title, byte[] image)throws SQLException{
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("TITLE", title);
        cv.put("BLOB", image);
        db.insert(TABLE_NAME, null, cv);
        Toast.makeText(ctx, "inserted", Toast.LENGTH_LONG).show();

    }
    public void delete(String title, byte[] image){
        db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + "WHERE TITLE = \"" + title +"\" OR BLOB = \"" + image +");");
    }

    public void update(String s, byte[] image){
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("TITLE", s);
        cv.put("BLOB", image);
        db.execSQL("UPDATE " + TABLE_NAME + "SET TITLE = \"" + s + "\", BLOB = \"" + image + "\" WHERE TITLE = \"" + s + "\" OR BLOB = \"" + image + "\";");
    }
    */
    public List<Postdata> getEverything(String table){
        SQLiteDatabase db = this.getWritableDatabase();
        String NAME_TABLE = table;
        List<Postdata> allTheData = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + NAME_TABLE + " ORDER BY " + KEY_ID + " DESC ";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Postdata post = new Postdata();
                post.setTitle(cursor.getString(1));
                /*byte[] outImage = cursor.getBlob(2);
                if(outImage != null) {
                    Bitmap bMap = BitmapFactory.decodeByteArray(outImage, 0, outImage.length);
                    Bitmap resized = Bitmap.createScaledBitmap(bMap, (int) (bMap.getWidth() * 0.8), (int) (bMap.getHeight() * 0.8), true);
                    post.setImg(resized);
                }
                else{
                    post.setImg(null);
                }
                */
                post.setPost(cursor.getString(2));
                post.setDate(cursor.getString(3));
                post.setTime(cursor.getString(4));
                allTheData.add(post);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return allTheData;

    }
    /*
    public List<PicData>getRange(int start, int end){
        SQLiteDatabase db = this.getWritableDatabase();
        List<PicData> rangeData = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID + " BETWEEN " + start + " AND " + end;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                PicData pic = new PicData();
                pic.setTitle(cursor.getString(1));
                Log.d("PicData", pic.getTitle());
                byte [] outImage = cursor.getBlob(2);
                Bitmap bMap = BitmapFactory.decodeByteArray(outImage, 0, outImage.length);
                Bitmap resized = Bitmap.createScaledBitmap(bMap, (int)(bMap.getWidth()*0.8), (int)(bMap.getHeight()*0.8),true);
                pic.setImg(resized);
                rangeData.add(pic);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return rangeData;
    }

    public Boolean checkID(int ID){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID + " = " + ID;
        Cursor c = db.rawQuery(selectQuery, null);
        if(c.getCount() <= 0){
            c.close();
            db.close();
            return false;
        }
        c.close();
        db.close();
        return true;
    }
    public Boolean checkTitle(String title){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_NAME + " = " + "'"+title+"'";
        Cursor c = db.rawQuery(selectQuery, null);
        if(c.getCount() <= 0){
            c.close();
            db.close();
            return false;
        }
        c.close();
        db.close();
        return true;
    }
*/
}

