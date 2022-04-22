package Project.Sudoku.Classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class LanguageHandler
{
    public static void loadCurrentLanguage(Context context)
    {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        Resources resources = context.getResources();
        Configuration configuration = new Configuration();
        String currentLanguage = sharedPreferences.getString("app_language", null);
        Locale locale = resources.getConfiguration().locale;

        if (currentLanguage == null)
        {
            // No previously selected language is saved, default to English
            currentLanguage = "en";
            String country = "US";
            if (locale.getLanguage().equals("no") || locale.getLanguage().equals("nb") || locale.getLanguage().equals("nn"))
            {
                // locale is norwegian
                currentLanguage = "no";
                country = "NO";
            }
            // Save current language
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("app_language", currentLanguage);
            editor.apply();
            configuration.locale = new Locale(currentLanguage, country);
        }
        else
        {
            // Set current language
            if (currentLanguage.equals("no"))
                configuration.locale = new Locale("no", "NO");
            else
                configuration.locale = new Locale("en", "US");
        }
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    public static String getCurrentLanguage(Context context)
    {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        return sharedPreferences.getString("app_language", null);
    }

    public static void setCurrentLanguage(Context context, String language)
    {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Configuration configuration = new Configuration();
        editor.putString("app_language", language);
        editor.apply();
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
    }
}
