package com.example.appthibanglaixe.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.appthibanglaixe.R;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.appthibanglaixe.entity.modify;
import com.example.appthibanglaixe.model.bode;
import com.example.appthibanglaixe.model.cauhoi_traloi;
import com.example.appthibanglaixe.model.lythuyet;
import com.example.appthibanglaixe.model.meothiIterm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class sqDuLieu extends SQLiteOpenHelper {
    private static final String TAG = sqDuLieu.class.getSimpleName();

    private Resources mResources;
    private static final String DB_Name = "banglaixea1.db";
    private static final int DB_VERSION = 1;
    SQLiteDatabase db;
    private static sqDuLieu instance = null;
    //chạy trên nhiều luồng
    public synchronized static sqDuLieu getInstance(Context context){
        if(instance == null){
            instance = new sqDuLieu(context);
        }
        return instance;
    }
    public sqDuLieu(Context context) {
        super(context, DB_Name, null, DB_VERSION);
        mResources = context.getResources();

        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_LOGIN_TABLE = "CREATE TABLE " + DbContract.LoginEntry.TABLE_NAME_LOGIN + " (" +
                DbContract.LoginEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DbContract.LoginEntry.COLUMN_USER + " TEXT NOT NULL, " +
                DbContract.LoginEntry.COLUMN_PASS + " TEXT NOT NULL);";

        final String SQL_CREATE_BUGS_TABLE = "CREATE TABLE " + DbContract.MenuEntry.TABLE_NAME + " (" +
                DbContract.MenuEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DbContract.MenuEntry.COLUMN_CAU + " TEXT NOT NULL, " +
                DbContract.MenuEntry.COLUMN_NOIDUNGCAUHOI + " TEXT NOT NULL, " +
                DbContract.MenuEntry.COLUMN_HINHCAUHOI + " TEXT, " +
                DbContract.MenuEntry.COLUMN_A + " TEXT NOT NULL, " +
                DbContract.MenuEntry.COLUMN_B + " TEXT NOT NULL," +
                DbContract.MenuEntry.COLUMN_C + " TEXT, " +
                DbContract.MenuEntry.COLUMN_D + " TEXT, " +
                DbContract.MenuEntry.COLUMN_CAUDUNG + " TEXT, " +
                DbContract.MenuEntry.COLUMN_CAUDIEMLIET + " TEXT, " +
                DbContract.MenuEntry.COLUMN_LOAICAUHOI + " TEXT, " +
                DbContract.MenuEntry.COLUMN_SOBODE + " TEXT NOT NULL, " +
                DbContract.MenuEntry.COLUMN_CNDC + " TEXT , " +
                DbContract.MenuEntry.COLUMN_NGUOIDUNGLYTHUYET + " TEXT " + " ) ;";

        final String SQL_CREATE_BUGS_TABLE1 = "CREATE TABLE " +DbContract.Lythuyet.TABLE_NAME1+" (" +
                DbContract.Lythuyet._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbContract.Lythuyet.COLUMN_TIEUDE+ " TEXT NOT NULL, " +
                DbContract.Lythuyet.COLUMN_HINH+ " TEXT, " +
                DbContract.Lythuyet.COLUMN_SOCAU+" TEXT, " +
                DbContract.Lythuyet.COLUMN_DIEMLIET+" TEXT, " +
                DbContract.Lythuyet.COLUMN_TIENDO+" TEXT " + ");";

        final String SQL_CREATE_BUGS_TABLE_BODE = "CREATE TABLE "+DbContract.BoDe.TABLE_NAMEBODE+" ("+
                DbContract.BoDe._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                DbContract.BoDe.COLUM_BDSO+" TEXT, "+
                DbContract.BoDe.COLUM_SOCAU+" TEXT, "+
                DbContract.BoDe.COLUM_DIEM+" TEXT, "+
                DbContract.BoDe.COLUM_KQ+" TEXT " +");";

        final String SQL_CREATE_BUGS_TABLE_MEOTHI = "CREATE TABLE "+DbContract.MeoThi.TABLE_NAMEMEOTHI+" ("+
                DbContract.MeoThi._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                DbContract.MeoThi.COLUM_IDMEOTHI+" TEXT, "+
                DbContract.MeoThi.COLUM_NDMEOTHI+" TEXT, "+
                DbContract.MeoThi.COLUM_LOAI+" TEXT "+");";

        db.execSQL(SQL_CREATE_LOGIN_TABLE);
        db.execSQL(SQL_CREATE_BUGS_TABLE);
        db.execSQL(SQL_CREATE_BUGS_TABLE1);
        db.execSQL(SQL_CREATE_BUGS_TABLE_BODE);
        db.execSQL(SQL_CREATE_BUGS_TABLE_MEOTHI);
        Log.d(TAG, "Database Created Successfully" );

        try {
            readDataToDb(db);
            readDataToDb1(db);
            readDataToDb_BoDe(db);
            readDataToDb_MeoThi(db);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    private void readDataToDb(SQLiteDatabase db) throws IOException, JSONException {

        final String MNU_CAU = "cau";
        final String MNU_NOIDUNGCAUHOI = "noidungcauhoi";
        final String MNU_HINHCAUHOI = "hinhcauhoi";
        final String MNU_A = "a";
        final String MNU_B = "b";
        final String MNU_C = "c";
        final String MNU_D = "d";
        final String MNU_CAUDUNG = "caudung";
        final String MNU_CAUDIEMLIET = "caudiemliet";
        final String MNU_LOAICAUHOI = "loaicauhoi";
        final String MNU_SOBODE = "sobode";

        try {
            String jsonDataString = readJsonDataFromFile();
            JSONArray menuItemsJsonArray = new JSONArray(jsonDataString);

            for (int i = 0; i < menuItemsJsonArray.length(); ++i) {

                String cau,noidungcauhoi,hinhcauhoi,a,b,c,d,caudung,caudiemliet,loaicauhoi,sobode,CauNDChon, Nguoidunglythuyet;
                JSONObject menuItemObject = menuItemsJsonArray.getJSONObject(i);


                cau = menuItemObject.getString(MNU_CAU);
                noidungcauhoi = menuItemObject.getString(MNU_NOIDUNGCAUHOI);
                hinhcauhoi = menuItemObject.getString(MNU_HINHCAUHOI);
                a = menuItemObject.getString(MNU_A);
                b = menuItemObject.getString(MNU_B);
                c = menuItemObject.getString(MNU_C);
                d = menuItemObject.getString(MNU_D);
                caudung=menuItemObject.getString(MNU_CAUDUNG);
                caudiemliet=menuItemObject.getString(MNU_CAUDIEMLIET);
                loaicauhoi=menuItemObject.getString(MNU_LOAICAUHOI);
                sobode=menuItemObject.getString(MNU_SOBODE);
                CauNDChon = "";
                Nguoidunglythuyet = "";

                ContentValues menuValues = new ContentValues();

                menuValues.put(DbContract.MenuEntry.COLUMN_CAU, cau);
                menuValues.put(DbContract.MenuEntry.COLUMN_NOIDUNGCAUHOI,noidungcauhoi);
                menuValues.put(DbContract.MenuEntry.COLUMN_HINHCAUHOI,hinhcauhoi);
                menuValues.put(DbContract.MenuEntry.COLUMN_A,a);
                menuValues.put(DbContract.MenuEntry.COLUMN_B,b);
                menuValues.put(DbContract.MenuEntry.COLUMN_C,c);
                menuValues.put(DbContract.MenuEntry.COLUMN_D,d);
                menuValues.put(DbContract.MenuEntry.COLUMN_CAUDUNG,caudung);
                menuValues.put(DbContract.MenuEntry.COLUMN_CAUDIEMLIET,caudiemliet);
                menuValues.put(DbContract.MenuEntry.COLUMN_LOAICAUHOI,loaicauhoi);
                menuValues.put(DbContract.MenuEntry.COLUMN_SOBODE,sobode);
                menuValues.put(DbContract.MenuEntry.COLUMN_CNDC,CauNDChon);
                menuValues.put(DbContract.MenuEntry.COLUMN_NGUOIDUNGLYTHUYET,Nguoidunglythuyet);

                db.insert(DbContract.MenuEntry.TABLE_NAME, null, menuValues);
                Log.d(TAG, "Inserted Successfully " + menuValues );
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }
// lý thuyết
    private void readDataToDb1(SQLiteDatabase db) throws IOException, JSONException {

        final String MNU_TIEUDE = "tieude";
        final String MNU_HINH = "hinh";
        final String MNU_SOCAU = "socau";
        final String MNU_DIEMLIET = "caudiemliet";
        final String MNU_TIENDO = "tiendo";

        try {
            String jsonDataString = readJsonDataFromFile1();
            JSONArray menuItemsJsonArray = new JSONArray(jsonDataString);

            for (int i = 0; i < menuItemsJsonArray.length(); ++i) {

                String tieude, hinh, socau, diemliet, tiendo;
                JSONObject menuItemObject = menuItemsJsonArray.getJSONObject(i);


                tieude = menuItemObject.getString(MNU_TIEUDE);
                hinh = menuItemObject.getString(MNU_HINH);
                socau = menuItemObject.getString(MNU_SOCAU);
                diemliet = menuItemObject.getString(MNU_DIEMLIET);
                tiendo = menuItemObject.getString(MNU_TIENDO);

                ContentValues menuValues = new ContentValues();

                menuValues.put(DbContract.Lythuyet.COLUMN_TIEUDE, tieude);
                menuValues.put(DbContract.Lythuyet.COLUMN_HINH,hinh);
                menuValues.put(DbContract.Lythuyet.COLUMN_SOCAU,socau);
                menuValues.put(DbContract.Lythuyet.COLUMN_DIEMLIET,diemliet);
                menuValues.put(DbContract.Lythuyet.COLUMN_TIENDO,tiendo);

                db.insert(DbContract.Lythuyet.TABLE_NAME1, null, menuValues);


                Log.d(TAG, "Inserted Successfully " + menuValues );
            }


        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }

    }
    private void readDataToDb_MeoThi(SQLiteDatabase db) throws IOException , JSONException{
        final String MNU_IDMEOTHI = "id_meo";
        final String MNU_NDMEOTHI = "nd_meo";
        final String MNU_LOAI = "loai";
        try{
            String jsonDataString = readJsonDataFromFile_MeoThi();
            JSONArray menuItemsJsonArray = new JSONArray(jsonDataString);
            for (int i = 0 ; i < menuItemsJsonArray.length(); i++){
                String idMeo, ndMeo,loai;
                JSONObject menuItemJsonObject = menuItemsJsonArray.getJSONObject(i);
                idMeo = menuItemJsonObject.getString(MNU_IDMEOTHI);
                ndMeo = menuItemJsonObject.getString(MNU_NDMEOTHI);
                loai = menuItemJsonObject.getString(MNU_LOAI);
                ContentValues menuValues = new ContentValues();
                menuValues.put(DbContract.MeoThi.COLUM_IDMEOTHI,idMeo);
                menuValues.put(DbContract.MeoThi.COLUM_NDMEOTHI,ndMeo);
                menuValues.put(DbContract.MeoThi.COLUM_LOAI,loai);
                db.insert(DbContract.MeoThi.TABLE_NAMEMEOTHI,null,menuValues);
                Log.d(TAG, "Inserted Successfully " + menuValues );
            }

        }catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }



    private void readDataToDb_BoDe(SQLiteDatabase db) throws IOException, JSONException {

        final String MNU_BDSo = "bd";
        final String MNU_cauSo = "cau";

        try {
            String jsonDataString = readJsonDataFromFile_BoDe();
            JSONArray menuItemsJsonArray = new JSONArray(jsonDataString);

            for (int i = 0; i < menuItemsJsonArray.length(); ++i) {

                String bode, cau, diem, ketqua;
                JSONObject menuItemObject = menuItemsJsonArray.getJSONObject(i);


                bode = menuItemObject.getString(MNU_BDSo);
                cau = menuItemObject.getString(MNU_cauSo);
                diem ="";
                ketqua = "";

                ContentValues menuValues = new ContentValues();

                menuValues.put(DbContract.BoDe.COLUM_BDSO, bode);
                menuValues.put(DbContract.BoDe.COLUM_SOCAU,cau);
                menuValues.put(DbContract.BoDe.COLUM_DIEM,diem);
                menuValues.put(DbContract.BoDe.COLUM_KQ,ketqua);

                db.insert(DbContract.BoDe.TABLE_NAMEBODE, null, menuValues);


                Log.d(TAG, "Inserted Successfully " + menuValues );
            }


        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }

    }
    private String readJsonDataFromFile() throws IOException {

        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try {
            String jsonDataString = null;
            inputStream = mResources.openRawResource(R.raw.menu_iterm);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            while ((jsonDataString = bufferedReader.readLine()) != null) {
                builder.append(jsonDataString);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return new String(builder);
    }

    private String readJsonDataFromFile1() throws IOException {

        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try {
            String jsonDataString = null;
            inputStream = mResources.openRawResource(R.raw.menu_lythuyet);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            while ((jsonDataString = bufferedReader.readLine()) != null) {
                builder.append(jsonDataString);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return new String(builder);
    }
    private String readJsonDataFromFile_BoDe() throws IOException{
        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();
        try{
            String jsonDataString = null;
            inputStream = mResources.openRawResource(R.raw.menu_bode);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            while ((jsonDataString = bufferedReader.readLine()) != null) {
                builder.append(jsonDataString);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return new String(builder);
    }
    private String readJsonDataFromFile_MeoThi() throws IOException {
        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();
        try {
            String jsonDataString = null;
            inputStream = mResources.openRawResource(R.raw.menu_meothi);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            while ((jsonDataString = bufferedReader.readLine()) != null) {
                builder.append(jsonDataString);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return new String(builder);
    }

    public ArrayList<com.example.appthibanglaixe.model.cauhoi_traloi> getAllPeople(int bode) {
        ArrayList<com.example.appthibanglaixe.model.cauhoi_traloi> listPerson = new ArrayList<>();
        // Select All Query
        String selectQuery = " SELECT  * FROM " + DbContract.MenuEntry.TABLE_NAME + " where " + DbContract.MenuEntry.COLUMN_SOBODE + "=" + bode ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {//trỏ đến vị trí đầu tiên của cursor.
            do {
                cauhoi_traloi person = new cauhoi_traloi();
                person.setID(cursor.getInt(0));
                person.setCau(cursor.getString(1));
                person.setNoidungcauhoi(cursor.getString(2));
                person.setHinhcauhoi(cursor.getString(3));
                person.setA(cursor.getString(4));
                person.setB(cursor.getString(5));
                person.setC(cursor.getString(6));
                person.setD(cursor.getString(7));
                person.setCaudung(cursor.getString(8));
                person.setCauliet(cursor.getString(9));
                person.setLoaicauhoi(cursor.getString(10));
                person.setBode(cursor.getString(11));
                person.setCauNDChon(cursor.getString(12));
                person.setNguoidunglythuet(cursor.getString(13));

                listPerson.add(person);
            } while (cursor.moveToNext());//di chuyển con trỏ đến vị trí tiếp theo.
        }
        cursor.close();
        db.close();
        return listPerson;
    }

    public ArrayList<com.example.appthibanglaixe.model.cauhoi_traloi> getTienDo(int loaicauhoi) {
        ArrayList<com.example.appthibanglaixe.model.cauhoi_traloi> listPerson = new ArrayList<>();
        // Select All Query
        String selectQuery = " SELECT  * FROM " + DbContract.MenuEntry.TABLE_NAME + " where "
                + DbContract.MenuEntry.COLUMN_LOAICAUHOI + " = " + loaicauhoi + " AND "+DbContract.MenuEntry.COLUMN_NGUOIDUNGLYTHUYET+" !=''";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                cauhoi_traloi person = new cauhoi_traloi();
                person.setID(cursor.getInt(0));
                person.setCau(cursor.getString(1));
                person.setNoidungcauhoi(cursor.getString(2));
                person.setHinhcauhoi(cursor.getString(3));
                person.setA(cursor.getString(4));
                person.setB(cursor.getString(5));
                person.setC(cursor.getString(6));
                person.setD(cursor.getString(7));
                person.setCaudung(cursor.getString(8));
                person.setCauliet(cursor.getString(9));
                person.setLoaicauhoi(cursor.getString(10));
                person.setBode(cursor.getString(11));
                person.setCauNDChon(cursor.getString(12));
                person.setNguoidunglythuet(cursor.getString(13));

                listPerson.add(person);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listPerson;
    }

    //select cau hỏi trả lời
    public ArrayList<com.example.appthibanglaixe.model.cauhoi_traloi> getCauHoiTraLoi() {
        ArrayList<com.example.appthibanglaixe.model.cauhoi_traloi> listPerson = new ArrayList<>();
        // Select All Query
        String selectQuery = " SELECT  * FROM " + DbContract.MenuEntry.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                cauhoi_traloi person = new cauhoi_traloi();
                person.setID(cursor.getInt(0));
                person.setCau(cursor.getString(1));
                person.setNoidungcauhoi(cursor.getString(2));
                person.setHinhcauhoi(cursor.getString(3));
                person.setA(cursor.getString(4));
                person.setB(cursor.getString(5));
                person.setC(cursor.getString(6));
                person.setD(cursor.getString(7));
                person.setCaudung(cursor.getString(8));
                person.setCauliet(cursor.getString(9));
                person.setLoaicauhoi(cursor.getString(10));
                person.setBode(cursor.getString(11));
                person.setCauNDChon(cursor.getString(12));
                person.setNguoidunglythuet(cursor.getString(13));

                listPerson.add(person);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listPerson;
    }

    public ArrayList<com.example.appthibanglaixe.model.bode> getDuLieuBoDe(){
        ArrayList<com.example.appthibanglaixe.model.bode> lstbode = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+DbContract.BoDe.TABLE_NAMEBODE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                bode person = new bode();
                person.setId(cursor.getInt(0));
                person.setSobode(cursor.getString(1));
                person.setSocau(cursor.getString(2));
                person.setDiem(cursor.getString(3));
                person.setKetqua(cursor.getString(4));
                lstbode.add(person);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lstbode;
    }

    public ArrayList<com.example.appthibanglaixe.model.lythuyet> getdulieulythuyet() {
        ArrayList<com.example.appthibanglaixe.model.lythuyet> lstlythuyet = new ArrayList<>();
        // Select All Query
        String selectQuery = " SELECT  * FROM " + DbContract.Lythuyet.TABLE_NAME1 ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                lythuyet person = new lythuyet();
                person.setId(cursor.getInt(0));
                person.setLoaicauhoi(cursor.getString(1));
                person.setHinhanh(cursor.getString(2));
                person.setSocauhoi(cursor.getString(3));
                person.setCaudiemliet(cursor.getString(4));
                person.setTiendo(cursor.getString(5));

                lstlythuyet.add(person);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lstlythuyet;
    }

    public ArrayList<com.example.appthibanglaixe.model.meothiIterm> getMeoTHi(String idMeo,String loai){
        ArrayList<com.example.appthibanglaixe.model.meothiIterm> listPerson = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+ DbContract.MeoThi.TABLE_NAMEMEOTHI+" WHERE "
                +DbContract.MeoThi.COLUM_IDMEOTHI +"= '"+idMeo+"' AND "+DbContract.MeoThi.COLUM_LOAI + "='"+loai+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                meothiIterm meothiIterm = new meothiIterm();
                meothiIterm.setId(cursor.getInt(0));
                meothiIterm.setId_meothi(cursor.getString(1));
                meothiIterm.setNoidung(cursor.getString(2));
                meothiIterm.setLoai(cursor.getString(3));

                listPerson.add(meothiIterm);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listPerson;
    }
    //lys thuyeets
    public ArrayList<com.example.appthibanglaixe.model.cauhoi_traloi> getAllCauhoi(int loai) {
        ArrayList<com.example.appthibanglaixe.model.cauhoi_traloi> listPerson = new ArrayList<>();
        // Select All Query
        String selectQuery = " SELECT  * FROM " + DbContract.MenuEntry.TABLE_NAME + " where " + DbContract.MenuEntry.COLUMN_LOAICAUHOI + "=" + loai ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                cauhoi_traloi person = new cauhoi_traloi();
                person.setID(cursor.getInt(0));
                person.setCau(cursor.getString(1));
                person.setNoidungcauhoi(cursor.getString(2));
                person.setHinhcauhoi(cursor.getString(3));
                person.setA(cursor.getString(4));
                person.setB(cursor.getString(5));
                person.setC(cursor.getString(6));
                person.setD(cursor.getString(7));
                person.setCaudung(cursor.getString(8));
                person.setCauliet(cursor.getString(9));
                person.setLoaicauhoi(cursor.getString(10));
                person.setBode(cursor.getString(11));
                person.setCauNDChon(cursor.getString(12));
                person.setNguoidunglythuet(cursor.getString(13));

                listPerson.add(person);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listPerson;
    }
    //update
    public long Update(String table, ContentValues values, String whereClause, String[] whereArgs){
        /*table: tên bảng muốn update
          values: các cặp key/value - tên cột/giá trị muốn cập nhật
          whereClause: điều kiện để dòng được chọn
          whereArgs: mạng các giá trị ứng với whereClause.
          return số bản ghi đc cập nhật
        */
        SQLiteDatabase db = getWritableDatabase();
        return db.update(table,values,whereClause,whereArgs);
    }
}
