package com.example.appthibanglaixe.entity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.appthibanglaixe.data.sqDuLieu;
import com.example.appthibanglaixe.model.cauhoi_traloi;

import java.util.ArrayList;

public class modify {
    public Context context;
    private static final String TABLE_NAME ="cauhoi";

    public static final String QUERY_CREATE_TABLE = "CREATE TABLE "+TABLE_NAME +" (" +
            "\tcau TEXT,\n" +
            "\tnd_cauhoi TEXT,\n" +
            "\thinh TEXT,\n" +
            "\tdapana TEXT,\n" +
            "\tdapanb TEXT,\n" +
            "\tdapanc TEXT,\n" +
            "\tdapand TEXT,\n" +
            "\tcaudung TEXT,\n" +
            "\tcaudiemliet TEXT,\n" +
            "\tloaicauhoi TEXT,\n" +
            "\tbode TEXT,\n"+
            ")";

    // phương thức lấy hết dữ liệu của bảng
    public static Cursor finAll(){
        String sql = "select * from "+TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = sqDuLieu.getInstance(null).getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        cursor.close();
        return cursor;
    }

    public static void insertTestUser(Context context) {
        SQLiteDatabase db = sqDuLieu.getInstance(context).getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", "testuser");
        values.put("password", "testpassword");
        db.insert("login", null, values);
    }

    public static boolean isValidLogin(Context context, String username, String password) {
        // Kết nối tới cơ sở dữ liệu để kiểm tra thông tin đăng nhập
        SQLiteDatabase db = sqDuLieu.getInstance(context).getReadableDatabase();

        // Thực hiện truy vấn để lấy thông tin người dùng từ cơ sở dữ liệu
        Cursor cursor = db.rawQuery("SELECT * FROM login WHERE username = ? AND password = ?", new String[]{username, password});

        if (cursor != null && cursor.getCount() > 0) {
            // Đăng nhập thành công
            cursor.close();
            return true;
        } else {
            // Đăng nhập thất bại
            if (cursor != null) {
                cursor.close();
            }
            return false;
        }
    }


    // phương thức thêm dữ liệu
    public static void insert(cauhoi_traloi cauhoi) {
SQLiteDatabase sqLiteDatabase = sqDuLieu.getInstance(null).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cau",  cauhoi.getCau());
        values.put("nd_cauhoi", cauhoi.getNoidungcauhoi());
        values.put("hinh", cauhoi.getHinhcauhoi());
        values.put("dapana", cauhoi.getA());
        values.put("dapanb", cauhoi.getB());
        values.put("dapanc", cauhoi.getC());
        values.put("dapand", cauhoi.getD());
        values.put("caudung", cauhoi.getCaudung());
        values.put("caudiemliet", cauhoi.getCauliet());
        values.put("loaicauhoi", cauhoi.getLoaicauhoi());
        values.put("bode", cauhoi.getBode());
        sqLiteDatabase.insert("cauhoi", null, values);
    }

    public static boolean Check(String cauu) {
        ArrayList<cauhoi_traloi> arrcauhoi;
        arrcauhoi = (ArrayList<cauhoi_traloi>) modify.finAll();
        for (cauhoi_traloi c : arrcauhoi) {
            if(c.getCau() == cauu){
                return true;
            }
        }
        return false;
    }
}
