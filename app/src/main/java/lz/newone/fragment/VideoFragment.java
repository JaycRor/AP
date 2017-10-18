package lz.newone.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import lz.newone.R;

/**
 * 该包下的全部Fragment，先作为框架填充效果，后面会被替换掉
 * Created by AdminJax on 2017/10/12.
 */

public class VideoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout, (ViewGroup) getActivity().findViewById(R.id.vp_content),false);

        return rootView;
    }
}
