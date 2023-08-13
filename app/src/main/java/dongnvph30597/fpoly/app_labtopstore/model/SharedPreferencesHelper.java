package dongnvph30597.fpoly.app_labtopstore.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SharedPreferencesHelper {
    private static final String PREF_NAME = "MyPreferences";
    private static final String PREF_CHECKED_ITEMS = "CheckedItems";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void saveCheckedItems(ArrayList<Integer> checkedPositions) {
        Set<String> stringSet = new HashSet<>();
        for (Integer position : checkedPositions) {
            stringSet.add(String.valueOf(position));
        }
        editor.putStringSet(PREF_CHECKED_ITEMS, stringSet);
        editor.apply();
    }

    public ArrayList<Integer> getCheckedItems() {
        ArrayList<Integer> checkedPositions = new ArrayList<>();
        Set<String> stringSet = sharedPreferences.getStringSet(PREF_CHECKED_ITEMS, new HashSet<>());
        for (String position : stringSet) {
            checkedPositions.add(Integer.parseInt(position));
        }
        return checkedPositions;
    }
    public void clearCheckedItems() {
        editor.remove(PREF_CHECKED_ITEMS);
        editor.apply();
    }
}
