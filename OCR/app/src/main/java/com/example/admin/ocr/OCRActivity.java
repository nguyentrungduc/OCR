package com.example.admin.ocr;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;

public class OCRActivity extends AppCompatActivity {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.imvDone)
    ImageView imvdone;
    private static String TAG = OCRActivity.class.toString();
    private ProgressDialog mProgressDialog;
    @BindView(R.id.tv)
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);
        ButterKnife.bind(this);
        getImage();
        //addListenner();


    }

    private void getImage(){

//        Bundle extras = getIntent().getBundleExtra("imgC");
//        if(extras!=null) {
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            imageView.setImageBitmap(imageBitmap);
//
//        }
        String uriImgC = getIntent().getStringExtra("imgC");
        if(uriImgC!=null){
            Log.d(TAG, uriImgC + "Path OCR");
            Uri uriC = Uri.parse(uriImgC);
            imageView.setImageURI(uriC);
        }
        String uriImg = getIntent().getStringExtra("imgG");
        if(uriImg!=null) {
            Log.d(TAG, uriImg);
            Uri myUri = Uri.parse(uriImg);
            imageView.setImageURI(myUri);
        }
    }

    private void addListenner(){
        imvdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap bm = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                Bitmap bitmap = convertColorIntoBlackAndWhiteImage(bm);
                //OCR(bitmap);
            }
        });

    }

    private Bitmap convertColorIntoBlackAndWhiteImage(Bitmap orginalBitmap) {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);

        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
                colorMatrix);

        Bitmap blackAndWhiteBitmap = orginalBitmap.copy(
                Bitmap.Config.ARGB_8888, true);

        Paint paint = new Paint();
        paint.setColorFilter(colorMatrixFilter);

        Canvas canvas = new Canvas(blackAndWhiteBitmap);
        canvas.drawBitmap(blackAndWhiteBitmap, 0, 0, paint);

        return blackAndWhiteBitmap;
    }


    private void OCR(Bitmap bitmap){
        TessOCR tessOCR = new TessOCR();
        String s = tessOCR.getOCRResult(bitmap);
        Log.d(TAG, s+"hihi");
        Log.d(TAG, String.valueOf(Utilities.getColumn(s))+"jiji");
        Log.d(TAG, String.valueOf(Utilities.getRow(s))+"oo");
        tv.setText(s);
        tessOCR.onDestroy();
        Log.d(TAG, Utilities.createMatrix(s).toString());
    }
}
