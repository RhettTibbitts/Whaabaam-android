package dev.whaabaam.com.ui.resumeviewer;

import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;

import java.util.Objects;

import dev.whaabaam.com.R;
import dev.whaabaam.com.ui.base.BaseActivity;
import dev.whaabaam.com.utils.CommonUtils;

public class ResumeViewerActivity extends BaseActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_viewer);
        webView = findViewById(R.id.webView);
        String docUrl = Objects.requireNonNull(getIntent().getExtras()).getString("resume_url");

        if (!TextUtils.isEmpty(docUrl)) {
            webView.getSettings().setAllowFileAccess(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.loadData(getWebUrl(docUrl), "text/html", "UTF-8");
        } else
            CommonUtils.toast(this, "Some error occurred!");
    }

    private String getWebUrl(String filePath) {
        return "\"<iframe src='http://docs.google.com/viewer?url=" + filePath + "&embedded=true'\"+\n" +
                "    \" width='100%' height='100%' style='border: none;'></iframe>\"";
    }
}
