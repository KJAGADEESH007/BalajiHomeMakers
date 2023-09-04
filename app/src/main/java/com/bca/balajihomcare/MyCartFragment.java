package com.bca.balajihomcare;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

import com.bca.balajihomcare.utils.API;
import com.google.firebase.auth.FirebaseAuth;

public class MyCartFragment extends Fragment {
FirebaseAuth firebaseAuth;
    WebView wView;
    public static String email;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "UserPref" ;

    public MyCartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth=FirebaseAuth.getInstance();

        email=firebaseAuth.getCurrentUser().getEmail();
//        loadData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.my_cart_fragment, container, false);
        wView=(WebView) mainView.findViewById(R.id.cwv);
        wView.loadUrl(API.CART_URL+"?email="+email);




        Log.e("STATUS", "onCreateView");

        return mainView;


    }







}
