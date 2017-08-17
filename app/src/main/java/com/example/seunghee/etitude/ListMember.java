package com.example.seunghee.etitude;

/**
 * Created by seunghee on 2017. 7. 16..
 */

public class ListMember {
    private String Rus;
    private String Eng;
    private String Kaz;
    private int Num;
    private int mainpage;

    public int getMainpage() {
        return mainpage;
    }
    public void setMainpage(int mainpage) {
        this.mainpage = mainpage;
    }

    public int getNum() {
        return Num;
    }

    public void setNum(int num) {
        Num = num;
    }

    public String getRus() {
        return Rus;
    }

    public void setRus(String rus) {
        Rus = rus;
    }

    public String getEng() {
        return Eng;
    }

    public void setEng(String eng) {
        Eng = eng;
    }

    public String getKaz() {
        return Kaz;
    }

    public void setKaz(String kaz) {
        Kaz = kaz;
    }


}

