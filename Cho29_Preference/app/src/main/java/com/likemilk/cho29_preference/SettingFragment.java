package com.likemilk.cho29_preference;




/*채크 다풀어준다 체크 다푼상태에서 풀어야한다*/

import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
//이거 있어야한다..?

public class SettingFragment extends PreferenceFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);
    // 이 xml 프리퍼런스를 읽어서 여기다가 보인다. 여기 한군대로 다 합쳐진다.
    }


}
