package com.arcsoft.arcfacedemo.fragment;

import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.TextView;

import com.arcsoft.arcfacedemo.R;

import me.yokeyword.fragmentation.SupportFragment;

public class LabelViewFragment extends SupportFragment {

    private TextView labelView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.label_view, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        labelView = (TextView) view.findViewById(R.id.label_view);
    }

}
