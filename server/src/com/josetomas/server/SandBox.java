package com.josetomas.server;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * User: Tomas Najun
 * Date: 16/07/13
 * Time: 18:30
 */
public class SandBox {
    static String displayValue(Locale currentLocale, String key) {

        ResourceBundle labels =
                ResourceBundle.getBundle("LabelsBundle",currentLocale);
        String value  = labels.getString(key);
        return value;

    } // displayValue


    static void iterateKeys(Locale currentLocale) {

        ResourceBundle labels =
                ResourceBundle.getBundle("LabelsBundle",currentLocale);

        Enumeration bundleKeys = labels.getKeys();

        while (bundleKeys.hasMoreElements()) {
            String key = (String)bundleKeys.nextElement();
            String value  = labels.getString(key);
            System.out.println("key = " + key + ", " +
                    "value = " + value);
        }

    } // iterateKeys


    static public void main(String[] args) {

        Locale[] supportedLocales = {
                new Locale("es", "ES"),
                Locale.ENGLISH
        };

//        for (int i = 0; i < supportedLocales.length; i ++) {
//            displayValue(supportedLocales[i], "s2");
//        }
        System.out.println("\n"+ displayValue(Locale.getDefault(), "titleID"));
        System.out.println();

        iterateKeys(supportedLocales[0]);

    }

    public static void main2(String[] args) {
        Locale locale = Locale.getDefault();
        String lang = locale.getDisplayLanguage();
        String country = locale.getDisplayCountry();

        Locale locale2 = new Locale(System.getProperty("user.language") ,System.getProperty("user.country"));
        System.out.println("English" + Locale.ENGLISH);

        System.out.println("locale = " + locale);
        System.out.println("language = " + lang);
        System.out.println("country = " + country);
        System.out.println("locale2 = " + locale2);

    }
}
