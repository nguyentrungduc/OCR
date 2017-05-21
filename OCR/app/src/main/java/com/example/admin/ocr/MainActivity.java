package com.example.admin.ocr;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;

import android.graphics.*;
import android.net.Uri;
import android.os.Environment;


import android.provider.MediaStore;
import android.support.annotation.NonNull;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.os.TraceCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.theartofdev.edmodo.cropper.CropImageView;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.jar.Manifest;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    private String mCurrentPhotoPath;
    private static int IMG_RESULT = 2;
    public static final String lang = "eng";
    public static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/DemoOCR/";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String TAG = MainActivity.class.toString();
    private ProgressDialog mProgressDialog;
    private TessOCR mTessOCR;
    private Uri outputFileDir;

    @BindView(R.id.albunBtn)
    ImageView albumbtn;
    @BindView(R.id.camBtn)
    ImageView cambtn;
    @BindView(R.id.imv)
    CropImageView imv;
    @BindView(R.id.btndone)
    ImageView btnDone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},200);
        }
        if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},300);
        }


        String[] paths = new String[] { DATA_PATH, DATA_PATH + "tessdata/" };

        for (String path : paths) {
            File dir = new File(path);
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    Log.v("Main", "ERROR: Creation of directory " + path + " on sdcard failed");
                    break;
                } else {
                    Log.v("Main", "Created directory " + path + " on sdcard");
                }
            }

        }
        if (!(new File(DATA_PATH + "tessdata/" + lang + ".traineddata")).exists()) {
            try {

                AssetManager assetManager = getAssets();

                InputStream in = assetManager.open(lang + ".traineddata");

                OutputStream out = new FileOutputStream(DATA_PATH
                        + "tessdata/" + lang + ".traineddata");

                byte[] buf = new byte[1024];
                int len;

                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();

                out.close();

                 Log.v(TAG, "Copied " + lang + " traineddata");
            } catch (IOException e) {
                 Log.e(TAG, "Was unable to copy " + lang + " traineddata " + e.toString());
            }


        }
        mTessOCR =new TessOCR();

        btnDone.setVisibility(View.INVISIBLE);

        addListenner();

    }



    private void addListenner() {
//        cambtn.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onClick(View v) {
//                dispatchTakePictureIntent();
//
//            }
//        });
        cambtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();


            }
        });
        albumbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(galleryIntent, IMG_RESULT);

            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap cropped = imv.getCroppedImage(500, 500);
                if (cropped != null)
                    imv.setImageBitmap(cropped);
                OCR(convertColorIntoBlackAndWhiteImage(cropped));

            }
        });

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            setPic();
            btnDone.setVisibility(View.VISIBLE);



        }
        if (requestCode == IMG_RESULT && resultCode == MainActivity.RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            imv.setImageUriAsync(selectedImage);
            btnDone.setVisibility(View.VISIBLE);

        }
    }




    private void dispatchTakePictureIntent1() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void startCameraActivity(){
        try{
            String imagePath = DATA_PATH+ "/imgs";
            File dir = new File(imagePath);
            if(!dir.exists()){
                dir.mkdir();
            }
            Log.d(TAG,imagePath);
            String imageFilePath = imagePath+"/ocr.jpg";
            outputFileDir = Uri.fromFile(new File(imageFilePath));
            Log.d(TAG,outputFileDir.toString());
            final Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileDir);
            if(pictureIntent.resolveActivity(getPackageManager() ) != null){
                startActivityForResult(pictureIntent,100);
            }
        } catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }

    private File createImageFile1() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        String storageDir = Environment.getExternalStorageDirectory()
                + "/TessOCR";
        File dir = new File(storageDir);
        if (!dir.exists())
            dir.mkdir();

        File image = new File(storageDir + "/" + imageFileName + ".jpg");


        mCurrentPhotoPath = image.getAbsolutePath();
        return image;

    }

    public void changeFragment(Fragment fragment, boolean addToBackStack){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main, fragment);
        if(addToBackStack){
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    private void setPic() {
        int targetW = imv.getWidth();
        int targetH = imv.getHeight();

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor << 1;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imv.setImageBitmap(bitmap);
    }

    public void OCR(final Bitmap bitmap) {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(this, "Đang nhận dạng",
                    "", true);

        }
        else {
            mProgressDialog.show();
        }

        new Thread(new Runnable() {
            public void run() {

                final String result = mTessOCR.getOCRResult(bitmap).toLowerCase();


                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        if (result != null && !result.equals("")) {
                            Log.d(TAG,result);

                        }

                        mProgressDialog.dismiss();
                        ResultFragment resultFragment = new ResultFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("data", result);

                        resultFragment.setArguments(bundle);
                        changeFragment(resultFragment, true);
                    }

                });

            };
        }).start();

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){

        case 200:{
            if(grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Read permission denied", Toast.LENGTH_SHORT).show();
            }
        }
        case 300:{
            if(grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Write permission denied", Toast.LENGTH_SHORT).show();
            }
        }
        }
    }


}
