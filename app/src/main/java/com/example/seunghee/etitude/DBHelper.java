package com.example.seunghee.etitude;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by seunghee on 2017. 8. 12..
 */

public class DBHelper extends SQLiteOpenHelper {


    public static final String tableName = "Etitude";
    private static final int Version = 1;
    ArrayList<Integer> list=new ArrayList<>();

    public DBHelper(Context context) {
        super(context, tableName, null, Version);
        System.out.println("DBHelper-open");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + tableName+
                "(num INTEGER PRIMARY KEY AUTOINCREMENT, eng TEXT, kaz TEXT, rus TEXT, mainpage INTEGER DEFAULT 0, secondpage INTEGER DEFAULT 0," +
                " today_review INTEGER DEFAULT 0 , quiz_review INTEGER DEFAULT 0);";
        db.execSQL(sql);
    }

     public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }


    public void insert(SQLiteDatabase db, ArrayList<ListMember> list){
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getEng().indexOf('\'') > 0){
                list.get(i).setEng(list.get(i).getEng().replace("'", "\""));
            }
            if(i == 0 ){
                String sql = "INSERT INTO " + tableName +
                        "(eng, kaz, rus, mainpage, secondpage) VALUES ('" + list.get(i).getEng()+"' , '" + list.get(i).getKaz() +"' , '" + list.get(i).getRus()+ "', '1' , '1');";
                db.execSQL(sql);
            }else {
                String sql = "INSERT INTO " + tableName +
                        "(eng, kaz, rus) VALUES ('" + list.get(i).getEng() + "' , '" + list.get(i).getKaz() + "' , '" + list.get(i).getRus() + "' );";
                db.execSQL(sql);
            }
        }
        System.out.println("초기데이터 추가성공");
    }

    public int getListNum(SQLiteDatabase db) {
        String sql = "SELECT * FROM " + tableName+ " WHERE mainpage = 1 ";
        Cursor result = db.rawQuery(sql, null);
        int frist = 1;
        if(result.moveToNext()) {
            frist = Integer.parseInt(result.getString(0));
        }
        result.close();
        System.out.println("getList " + frist);
        frist = frist-1;
        return frist;
    }

    public int getsecondeNum(SQLiteDatabase db) {
        String sql = "SELECT * FROM " + tableName+ " WHERE secondpage = 1 ";
        Cursor result = db.rawQuery(sql, null);
        int frist = 1;
        if(result.moveToNext()) {
            frist = Integer.parseInt(result.getString(0));
        }
        result.close();
        System.out.println("getsecondNum " + frist);
        frist = frist-1;
        return frist;
    }

    public ArrayList<ListMember> getList(SQLiteDatabase db){
        ArrayList<ListMember> list = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;
        Cursor result = db.rawQuery(sql, null);
        if(result.moveToNext()) {
            do {
                ListMember listmember = new ListMember();
                listmember.setNum(Integer.parseInt(result.getString(0)));
                if (result.getString(1).indexOf('\"') > 0) {
                    String s = result.getString(1);
                    s = s.replace("\"", "'");
                    listmember.setEng(s);
                } else {
                    listmember.setEng(result.getString(1));
                }
                listmember.setKaz(result.getString(2));
                listmember.setRus(result.getString(3));
                list.add(listmember);
            } while (result.moveToNext());
        }
        result.close();
        System.out.println("getList");
        return list;
    }

    public void setmainpage(SQLiteDatabase db, int num_get){
        num_get = num_get+1;
        String sql = "UPDATE "+tableName +" SET mainpage = '0' WHERE mainpage = '1'";
        db.execSQL(sql);
        System.out.println("mainpage 0 setting");
        sql = "UPDATE " +tableName + " SET mainpage = '1' WHERE num = '" + num_get +"';" ;
        db.execSQL(sql);
        System.out.println("mainpage 1 setting");
    }

    public void setsecondepage(SQLiteDatabase db, int num_get){
        num_get = num_get+1;
        String sql = "UPDATE "+tableName +" SET secondpage = '0' WHERE secondpage = '1'";
        db.execSQL(sql);
        System.out.println("secondpage 0 setting");
        sql = "UPDATE " +tableName + " SET secondpage = '1' WHERE num = '" + num_get +"';" ;
        db.execSQL(sql);
        System.out.println("secondpage 1 setting");
    }

    public ArrayList<Integer> getToday_review(SQLiteDatabase db) {
        list.clear();
        String sql = "SELECT * FROM " + tableName+ " WHERE today_review = '1';";
        Cursor result = db.rawQuery(sql, null);
        int frist = 0;
        if(result.moveToFirst()) {
            do {
                frist = Integer.parseInt(result.getString(0));
                frist = frist - 1;
                list.add(frist);
            }while (result.moveToNext());
        }

        result.close();
        System.out.println("getToday_review ");
        return list;
    }

    public ArrayList<Integer> getQuiz_review(SQLiteDatabase db) {
        list.clear();
        String sql = "SELECT * FROM " + tableName+ " WHERE quiz_review = '1'; ";
        Cursor result = db.rawQuery(sql, null);
        int frist = 1;
        if(result.moveToNext()) {
            do {
                frist = Integer.parseInt(result.getString(0));
                frist = frist - 1;
                list.add(frist);
            }while (result.moveToNext());
        }
        result.close();

        System.out.println("getQuiz_review ");
        return list;
    }

    //today column setting 1
    public void setToday_review(SQLiteDatabase db, int number) {
        number = number+1;
        String sql = "UPDATE " + tableName+ " SET today_review = '1' WHERE num = '" + number+"';" ;
        db.execSQL(sql);
        System.out.println("setToday_review " + number);
    }

    //quiz column setting 1
    public void setQuiz_review(SQLiteDatabase db, int number) {
        number = number+1;
        String sql = "UPDATE " + tableName+ " SET quiz_review = '1' WHERE num = '" + number+"';" ;
        db.execSQL(sql);
        System.out.println("setQuiz_review " + number);
    }

    public void setToday_submit(SQLiteDatabase db, int number){
        number = number+1;
        String sql = "UPDATE " + tableName+ " SET today_review = '2' WHERE num = '" + number+"';" ;
        db.execSQL(sql);
        System.out.println("setToday_submit");
    }

    public void setQuiz_submit(SQLiteDatabase db, int number){
        number = number+1;
        String sql = "UPDATE " + tableName+ " SET quiz_review = '2' WHERE num = '" + number+"';" ;
        db.execSQL(sql);
        System.out.println("setQuiz_submit");
    }
}
