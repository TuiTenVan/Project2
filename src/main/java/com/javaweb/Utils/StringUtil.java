package com.javaweb.Utils;

public class StringUtil {
    public static boolean checkString(String data){
        if(data != null && !data.isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }
}
