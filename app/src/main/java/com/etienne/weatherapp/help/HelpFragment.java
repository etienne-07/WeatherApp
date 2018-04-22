package com.etienne.weatherapp.help;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.etienne.weatherapp.MainActivity;
import com.etienne.weatherapp.R;

public class HelpFragment extends Fragment {

    private static final String HELP_HTML_PAGE_LOCATION = "file:///android_asset/help.html";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).enableBackButton(true);
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        WebView webView = view.findViewById(R.id.web_view);
        webView.loadUrl(HELP_HTML_PAGE_LOCATION);
        return view;
    }
}
