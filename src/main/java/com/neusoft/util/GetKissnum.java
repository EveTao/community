package com.neusoft.util;

public class GetKissnum {
    public int getKisssnum(int qiandaoDay){
        int qiandaoKiss=5;
        if(qiandaoDay>=5){
            qiandaoKiss=10;
            if(qiandaoDay>=15){
                qiandaoKiss=15;
                if(qiandaoDay>=30){
                    qiandaoKiss=20;
                }
            }
        }
        return qiandaoKiss;
    }
}
