package com.example.seunghee.etitude;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    final static String table = "Etitude";

    //mainpage textview
    TextView tab_tv1, tab_tv2, tab_tv3;

    Button bt1_next, bt2_next, bt3_next, bt1_review, bt2_review, bt3_review;



    Button bt_eng, bt_kaz, bt_rus, bt_ran;

    Button button_help1, button_help2, button_help3;


    EditText eng_test, rus_test, kaz_test;
    TextView eng_text, rus_text, kaz_text;
    String eng=null, rus=null, kaz=null;



    Button bt3_daily, bt3_quiz;
    TextView tx3_eng, tx3_rus, tx3_kaz;

    TabHost th;

    private ArrayList<ListMember> list = new ArrayList<ListMember>();
    private ArrayList<Integer> quiz_list = new ArrayList<>();
    private ArrayList<Integer> today_list = new ArrayList<>();

    private int num_get = 0, second_num=0, checkbutton=1;
    private int quiz_up=0, today_up=0, checkUserbutton = 1;


    DBHelper dbHelper;
    SQLiteDatabase db;

    boolean excel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        dbHelper = new DBHelper(MainActivity.this);
        db = dbHelper.getWritableDatabase();
        excel = CheckAppFirstExecute();
        num_get = dbHelper.getListNum(db);
        second_num = dbHelper.getsecondeNum(db);


        if (excel) {
            System.out.println("처음입니다.");
            copyExcelDataToDatabase();
            dbHelper.insert(db, list);
            num_get = 0;
        }
        list = dbHelper.getList(db);

        //Next Button 3
        bt1_next = (Button) findViewById(R.id.tab1_next);
        bt2_next = (Button) findViewById(R.id.tab2_next);
        bt3_next = (Button) findViewById(R.id.tab3_next);

        //review button
        bt1_review = (Button) findViewById(R.id.tab1_review);
        bt2_review = (Button) findViewById(R.id.tab2_review);
        bt3_review = (Button) findViewById(R.id.tab3_submit);

        bt_eng = (Button) findViewById(R.id.tab2_1button1);
        bt_kaz = (Button) findViewById(R.id.tab2_1button2);
        bt_rus = (Button) findViewById(R.id.tab2_1button3);
        bt_ran = (Button) findViewById(R.id.tab2_1button4);


        tab_tv2 = (TextView) findViewById(R.id.rus_ex);
        tab_tv1 = (TextView) findViewById(R.id.eng_ex);
        tab_tv3 = (TextView) findViewById(R.id.kaz_ex);


        //help button
        button_help1 = (Button) findViewById(R.id.tab1_help);
        button_help2 = (Button) findViewById(R.id.tab2_help);
        button_help3 = (Button) findViewById(R.id.tab3_help);


        //테스트문
        eng_test = (EditText) findViewById(R.id.tab2_eng_test);
        rus_test = (EditText) findViewById(R.id.tab2_rus_test);
        kaz_test = (EditText) findViewById(R.id.tab2_kaz_test);

        //예제문
        eng_text = (TextView) findViewById(R.id.tab2_eng_ex);
        rus_text = (TextView) findViewById(R.id.tab2_rus_ex);
        kaz_text = (TextView) findViewById(R.id.tab2_kaz_ex);


        //3번째 탭 버튼 및 textView

        bt3_daily = (Button) findViewById(R.id.tab3_daily);
        bt3_quiz = (Button) findViewById(R.id.tab3_quiz);

        tx3_eng = (TextView) findViewById(R.id.tab3_eng_ex);
        tx3_rus = (TextView) findViewById(R.id.tab3_rus_ex);
        tx3_kaz = (TextView) findViewById(R.id.tab3_kaz_ex);


        //TabHost Java Code
        th = (TabHost) findViewById(R.id.tabhost);
        th.setup();
        TabHost.TabSpec ts1 = th.newTabSpec("Tab1").setContent(R.id.tab1).setIndicator("Today");
        th.addTab(ts1);
        TabHost.TabSpec ts2 = th.newTabSpec("Tab2").setContent(R.id.tab2).setIndicator("Quiz");
        th.addTab(ts2);
        TabHost.TabSpec ts3 = th.newTabSpec("Tab3").setContent(R.id.tab3).setIndicator("User");
        th.addTab(ts3);


        //today next Button ClickListener
        bt1_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num_get++;
                System.out.println(num_get);
                if (num_get >= list.size()) {
                    num_get = 0;
                }
                tab_tv1.setText(list.get(num_get).getEng());
                tab_tv2.setText(list.get(num_get).getRus());
                tab_tv3.setText(list.get(num_get).getKaz());
            }
        });

        //quiz next button ClickListener
        bt2_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eng = String.valueOf(eng_test.getText());
                rus = String.valueOf(rus_test.getText());
                kaz = String.valueOf(kaz_test.getText());

                System.out.println("bt2_next" + second_num);

                if(second_num >= list.size()){
                    second_num = 0;
                }
                if(checkbutton==1 && eng.compareTo(list.get(second_num).getEng())==0){
                    System.out.println("eng.equlas");
                    showsuccess(MainActivity.this, "Perfect");
                    second_num++;
                    eng_test.setText("");
                    rus_text.setText(list.get(second_num).getRus());
                    kaz_text.setText(list.get(second_num).getKaz());
                }else if(checkbutton==2 && kaz.compareTo(list.get(second_num).getKaz())==0){
                    System.out.println("kaz.equlas");
                    showsuccess(MainActivity.this, "Perfect");
                    second_num++;
                    kaz_test.setText("");
                    rus_text.setText(list.get(second_num).getRus());
                    eng_text.setText(list.get(second_num).getEng());
                }else if(checkbutton==3 && rus.compareTo(list.get(second_num).getRus())==0){
                    System.out.println("rus.equlas");
                    showsuccess(MainActivity.this, "Perfect");
                    second_num++;
                    rus_test.setText("");
                    eng_text.setText(list.get(second_num).getEng());
                    kaz_text.setText(list.get(second_num).getKaz());
                }
            }
        });


        //today Review Button ClickListener
        bt1_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.setToday_review(db, num_get);
            }
        });

        //quiz review Button ClickListener
        bt2_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.setQuiz_review(db, second_num);
            }
        });

        //quiz eng button
        bt_eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("bt_eng click");
                eng_test.setVisibility(View.VISIBLE);
                eng_text.setVisibility(View.GONE);
                kaz_test.setVisibility(View.GONE);
                kaz_text.setVisibility(View.VISIBLE);
                rus_test.setVisibility(View.GONE);
                rus_text.setVisibility(View.VISIBLE);
                rus_text.setText(list.get(second_num).getRus());
                kaz_text.setText(list.get(second_num).getKaz());
                checkbutton=1;
            }
        });

        //quiz kaz button
        bt_kaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("bt_kaz click");
                eng_test.setVisibility(View.GONE);
                eng_text.setVisibility(View.VISIBLE);
                kaz_test.setVisibility(View.VISIBLE);
                kaz_text.setVisibility(View.GONE);
                rus_test.setVisibility(View.GONE);
                rus_text.setVisibility(View.VISIBLE);
                rus_text.setText(list.get(second_num).getRus());
                eng_text.setText(list.get(second_num).getEng());
                checkbutton=2;

            }
        });

        //quiz rus button
        bt_rus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("bt_rus click");
                eng_test.setVisibility(View.GONE);
                eng_text.setVisibility(View.VISIBLE);
                kaz_test.setVisibility(View.GONE);
                kaz_text.setVisibility(View.VISIBLE);
                rus_test.setVisibility(View.VISIBLE);
                rus_text.setVisibility(View.GONE);
                eng_text.setText(list.get(second_num).getEng());
                kaz_text.setText(list.get(second_num).getKaz());
                checkbutton=3;
            }
        });

        //random quiz button click method
        bt_ran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int ran = random.nextInt(3);
                switch (ran){
                    case 0:
                        System.out.println("number0");
                        eng_test.setVisibility(View.VISIBLE);
                        eng_text.setVisibility(View.GONE);
                        kaz_test.setVisibility(View.GONE);
                        kaz_text.setVisibility(View.VISIBLE);
                        rus_test.setVisibility(View.GONE);
                        rus_text.setVisibility(View.VISIBLE);
                        checkbutton=1;
                        rus_text.setText(list.get(second_num).getRus());
                        kaz_text.setText(list.get(second_num).getKaz());
                        break;
                    case 1:
                        System.out.println("number1");
                        eng_test.setVisibility(View.GONE);
                        eng_text.setVisibility(View.VISIBLE);
                        kaz_test.setVisibility(View.VISIBLE);
                        kaz_text.setVisibility(View.GONE);
                        rus_test.setVisibility(View.GONE);
                        rus_text.setVisibility(View.VISIBLE);
                        checkbutton=2;
                        rus_text.setText(list.get(second_num).getRus());
                        eng_text.setText(list.get(second_num).getEng());
                        break;
                    case 2:
                        System.out.println("number2");
                        eng_test.setVisibility(View.GONE);
                        eng_text.setVisibility(View.VISIBLE);
                        kaz_test.setVisibility(View.GONE);
                        kaz_text.setVisibility(View.VISIBLE);
                        rus_test.setVisibility(View.VISIBLE);
                        rus_text.setVisibility(View.GONE);
                        checkbutton=3;
                        eng_text.setText(list.get(second_num).getEng());
                        kaz_text.setText(list.get(second_num).getKaz());
                        break;
                }
            }
        });




        bt3_daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                today_list = dbHelper.getToday_review(db);
                    if(!today_list.isEmpty()){

                }
                tx3_eng.setText(list.get(today_list.get(today_up)).getEng());
                tx3_rus.setText(list.get(today_list.get(today_up)).getKaz());
                tx3_kaz.setText(list.get(today_list.get(today_up)).getRus());
                checkUserbutton=1;
            }
        });

        bt3_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quiz_list = dbHelper.getQuiz_review(db);
                if(!quiz_list.isEmpty()){
                }
                tx3_eng.setText(list.get(quiz_list.get(quiz_up)).getEng());
                tx3_rus.setText(list.get(quiz_list.get(quiz_up)).getRus());
                tx3_kaz.setText(list.get(quiz_list.get(quiz_up)).getKaz());
                checkUserbutton=2;
            }
        });

        bt3_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkUserbutton==1){
                    today_up++;
                    if(today_up >= today_list.size()){
                        today_up=0;
                    }
                    tx3_eng.setText(list.get(today_list.get(today_up)).getEng());
                    tx3_rus.setText(list.get(today_list.get(today_up)).getRus());
                    tx3_kaz.setText(list.get(today_list.get(today_up)).getKaz());
                }else {
                    quiz_up++;
                    if(quiz_up >= quiz_list.size()){
                        quiz_up=0;
                    }
                    tx3_eng.setText(list.get(quiz_list.get(quiz_up)).getEng());
                    tx3_rus.setText(list.get(quiz_list.get(quiz_up)).getRus());
                    tx3_kaz.setText(list.get(quiz_list.get(quiz_up)).getKaz());
                }
            }
        });

        bt3_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkUserbutton==1){
                    dbHelper.setToday_submit(db, today_up);
                }else{
                    dbHelper.setQuiz_submit(db, quiz_up);
                }
            }
        });

        //help message
        button_help1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog(MainActivity.this, "today-help message");
            }
        });
        button_help2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog(MainActivity.this, "quiz-help message");
            }
        });
        button_help3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog(MainActivity.this, "user-help message");
            }
        });


        //setTextView contents
        tab_tv1.setText(list.get(num_get).getEng());
        tab_tv2.setText(list.get(num_get).getRus());
        tab_tv3.setText(list.get(num_get).getKaz());

        rus_text.setText(list.get(second_num).getRus());
        kaz_text.setText(list.get(second_num).getKaz());

    }




    //close app method
    @Override
    protected void onStop() {
        System.out.println("종료메소드 실행");
        dbHelper.setmainpage(db, num_get);
        dbHelper.setsecondepage(db, second_num);
        super.onStop();
    }

    //Review Button click and Show Dialog
    private static void show(final Activity activity) {
        AlertDialog.Builder ad = new AlertDialog.Builder(activity);
        ad.setMessage("Review Save");
        ad.setCancelable(false);
        ad.setPositiveButton("close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                activity.setResult(Activity.RESULT_OK);
            }

        });
        ad.create();
        ad.show();
    }

    //Help Dialog
    private static void showdialog(final Activity activity, String text) {

        AlertDialog.Builder ad = new AlertDialog.Builder(activity);
        ad.setMessage(text);
        ad.setCancelable(false);

        ad.setPositiveButton("close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                activity.setResult(Activity.RESULT_OK);

            }

        });
        ad.create();
        ad.show();
    }

    //Success Dialog
    private static void showsuccess(final Activity activity, String text) {

        AlertDialog.Builder ad = new AlertDialog.Builder(activity);
        ad.setMessage(text);
        ad.setCancelable(false);

        ad.setPositiveButton("Next", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                activity.setResult(Activity.RESULT_OK);

            }

        });
        ad.create();
        ad.show();
    }

    //copyExcelData To ArrayList
    private void copyExcelDataToDatabase() {

        int number = 1;   //Set Numberline
        InputStream in = null;
        XSSFWorkbook workbook = new XSSFWorkbook();

        try {

            in = getBaseContext().getResources().getAssets().open("500.xlsx");

            workbook = new XSSFWorkbook(in);

            //set Sheet Number
            XSSFSheet curSheet = workbook.getSheetAt(0);

            XSSFRow curRow;
            XSSFCell curCell;
            ListMember li;
            int count = 0;

            //row Index get Number
            for (int rowIndex = 0; rowIndex < curSheet.getPhysicalNumberOfRows(); rowIndex++) {

                if (rowIndex != 0) {
                    //List create
                    li = new ListMember();
                    //rowIndex get value
                    curRow = curSheet.getRow(rowIndex);
                    //Create nuw String value
                    String value;

                    //CellIndex get value
                    for (int cellIndex = 0; cellIndex < curRow.getPhysicalNumberOfCells(); cellIndex++) {
                        curCell = curRow.getCell(cellIndex);
                        value = curCell.getStringCellValue();

                        switch (cellIndex) {
                            case 0:
                                value = value.trim();
                                li.setEng(value);
                                break;
                            case 1:
                                value = value.trim();
                                li.setRus(value);
                                break;
                            case 2:
                                value = value.trim();
                                li.setKaz(value);
                                break;
                        }
                    }
                    li.setNum(number);
                    list.add(li);
                    number++;

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //frist check
    public boolean CheckAppFirstExecute() {
        SharedPreferences pref = getSharedPreferences("IsFirst", Activity.MODE_PRIVATE);
        boolean isFirst = pref.getBoolean("isFirst", false);
        if (!isFirst) { //최초 실행시 true 저장
            System.out.println("최초실행입니다.");
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isFirst", true);
            editor.commit();
        }
        return !isFirst;
    }

}


