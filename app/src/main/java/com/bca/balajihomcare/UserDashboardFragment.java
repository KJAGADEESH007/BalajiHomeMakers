package com.bca.balajihomcare;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.bca.balajihomcare.utils.API;
import com.google.firebase.auth.FirebaseAuth;

public class UserDashboardFragment extends Fragment {
    private FirebaseAuth mAuth;
    WebView wView;
    public static String email;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "UserPref" ;

    public UserDashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        email= mAuth.getCurrentUser().getEmail();
//        loadData();

    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.dashboard_fragment, container, false);
      wView=(WebView) mainView.findViewById(R.id.mwv);
        wView.setVisibility(View.INVISIBLE);
        CookieManager.getInstance().setAcceptCookie(true);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(wView, true);
        }

        wView.getSettings().setJavaScriptEnabled(true);
        wView.getSettings().setSupportZoom(true);

        wView.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecaion")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);

                wView.setVisibility(View.VISIBLE);
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                super.onPageStarted(view, url, favicon);

            }
        });
        String imgUrl= API.DASHBOARD_URL+"?email="+email;

        if (!imgUrl.equals(""))
            wView.loadUrl(imgUrl);








        Log.e("STATUS", "onCreateView");

        return mainView;


    }







}
