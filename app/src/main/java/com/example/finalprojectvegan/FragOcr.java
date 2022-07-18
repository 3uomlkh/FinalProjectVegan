package com.example.finalprojectvegan;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class FragOcr extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

//    ImageView ocrimageview = (ImageView) getView().findViewById(R.id.ocrImageView);

    public FragOcr() {
        // Required empty public constructor
    }

    public static FragOcr newInstance(String param1, String param2) {
        FragOcr fragment = new FragOcr();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_ocr, container, false);
//        ImageView ocrimageview = (ImageView) v.findViewById(R.id.ocrImageView);
//
//        Bitmap bitmap = getArguments().getParcelable("BitmapImage");
//        ocrimageview.setImageBitmap(bitmap);
        return v;
    }
}