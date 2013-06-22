package com.aug3.sys.util;

import java.util.Locale;

public class I18NUtil {
    public static final String LANG_EN = "en";
    public static final String LANG_SZH = "szh";
    
    /**
     * @param lang
     * @return locale according to lang parameter
     */
    public static Locale getLocale(String lang){
        if(LANG_SZH.equals(lang)){
            return Locale.SIMPLIFIED_CHINESE;
        }else{
            return getDefaultLocale();
        }
    }
    
    /**
     * @return simple Chinese locale
     */
    public static Locale getSimpleChineseLocale(){
        return Locale.SIMPLIFIED_CHINESE;
    }
    
    /**
     * @return default locale
     */
    public static Locale getDefaultLocale(){
        return Locale.ENGLISH;
    }
}
