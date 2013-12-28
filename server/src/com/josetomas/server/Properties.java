/*
Develop by Jose Gonzalez & Tomas Najun
2013 - Argentina
*/

package com.josetomas.server;

import java.util.Locale;
import java.util.ResourceBundle;


public class Properties {
    static Locale[] supportedLocales = new Locale[]{
            new Locale("es","ES"),
            Locale.ENGLISH
    };

    /**
     *@param currentLocale which contains the language and country
     *@param key it is the keyword which corresponds to the value looked for
     *@return the value
     */
    public static String displayValue(Locale currentLocale, String key) {
        ResourceBundle labels = ResourceBundle.getBundle("LabelsBundle", currentLocale);
        String value = labels.getString(key);
        return value;
    }
}
