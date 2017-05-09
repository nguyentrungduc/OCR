package com.example.admin.ocr;

import android.graphics.Bitmap;
import android.os.Environment;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;

/**
 * Created by ADMIN on 4/18/2017.
 */

public class TessOCR {
    private TessBaseAPI mTess;

    public TessOCR() {

        mTess = new TessBaseAPI();
        String datapath = Environment.getExternalStorageDirectory() + "/DemoOCR/";
        String language = "eng";

        File dir = new File(datapath + "/tessdata/");
        if (!dir.exists())
            dir.mkdirs();
        mTess.init(datapath, language);
        mTess.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "0123456789");
    }

    public String getOCRResult(Bitmap bitmap) {

        mTess.setImage(bitmap);
        String result = mTess.getUTF8Text();

        return result;
    }

    public void onDestroy() {
        if (mTess != null)
            mTess.end();
    }
}
