package com.example.finalprojectvegan;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

public class ocrActivity extends AppCompatActivity {

    final private static String TAG = "tag";
    private static final int CAMERA = 100;
    private static final int GALLERY = 101;

    TessBaseAPI tessBaseAPI;
    String dataPath = "";
    String langData = "kor";

    Button cameraBtn;
    Button galleryBtn;
    Button goOcr;
    ImageView ocrImage;
    Intent intent;

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat imageDate = new SimpleDateFormat("yyyyMMdd_HHmmss");
    String imagePath;

    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);

        ocrImage = findViewById(R.id.ocrimagepopup);
        cameraBtn = findViewById(R.id.cameraBtn);
        galleryBtn = findViewById(R.id.galleryBtn);
        goOcr = findViewById(R.id.goOcr);

        try {
            dataPath = getFilesDir() + "/tesseract/";
            checkFile(new File(dataPath + "tessdata/"), "kor");
//            checkFile(new File(dataPath + "tessdata/"), "eng");

            tessBaseAPI = new TessBaseAPI();
            tessBaseAPI.init(dataPath, langData);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "?????? ?????? ??????");
            } else {
                Log.d(TAG, "?????? ?????? ??????");
                ActivityCompat.requestPermissions(ocrActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        goOcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOcrConvert(ocrImage);

//                OcrThread thread = new OcrThread();
//                thread.start();
            }
        });

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.cameraBtn:

                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA);
                        break;
                }
            }
        });

        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY);
            }
        });

    }

//    class OcrThread extends Thread {
//        @Override
//        public void run() {
//            getOcrConvert(ocrImage);
//            Log.d("Thread", "thread ?????????");
//        }
//    }

    public void checkFile(File dir, String lang){
        Log.d("---","---");
        Log.d("//===========//","================================================");
        Log.d("","\n"+"[A_CameraOcr > checkFile() ????????? : OCR ?????? ?????? ?????? ?????? ?????? ??????]");
        Log.d("","\n"+"[?????? ?????? : "+String.valueOf(lang)+"]");
        Log.d("//===========//","================================================");
        Log.d("---","---");
        try {
            if(!dir.exists() && dir.mkdirs()){
                copyFiles(lang);
            }

            if(dir.exists()){
                String dataFilePath = dataPath + "/tessdata/" + lang + ".traineddata";
                File dataFile = new File(dataFilePath);
                if(!dataFile.exists()){
                    copyFiles(lang);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void copyFiles(String lang){
        Log.d("---","---");
        Log.d("//===========//","================================================");
        Log.d("","\n"+"[A_CameraOcr > copyFiles() ????????? : OCR ?????? ?????? ?????? ?????? ?????? ??????]");
        Log.d("","\n"+"[?????? ?????? : "+String.valueOf(lang)+"]");
        Log.d("//===========//","================================================");
        Log.d("---","---");
        try {
            String filePath = dataPath + "/tessdata/" + lang + ".traineddata";

            AssetManager assetManager = getAssets();

            InputStream inputStream = assetManager.open("tessdata/" + lang + ".traineddata");
            OutputStream outputStream = new FileOutputStream(filePath);

            byte[] buffer = new byte[1024];
            int read;
            while((read = inputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getOcrConvert(ImageView image){
        Log.d("---","---");
        Log.d("//===========//","================================================");
        Log.d("","\n"+"[A_CameraOcr > getOcrConvert() ????????? : OCR ?????? ?????? ??????]");
        Log.d("//===========//","================================================");
        Log.d("---","---");
        try {
            //TODO [drawable ???????????? ??????????????? ????????????]
            //Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon_resource);

            //TODO [????????? ?????? ???????????? ??????????????? ????????????]
            Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();

            //TODO [OCR ?????? ??????]
            tessBaseAPI.setImage(bitmap);
            String result = String.valueOf(tessBaseAPI.getUTF8Text());
//            String str = String.valueOf(tessBaseAPI.getUTF8Text());
//            String target = "????????????";
//            int target_num = str.indexOf(target);
//            String result = str.substring(target_num, (str.substring(target_num).indexOf("??????") + target_num));

//            String[] array = result.split(",");
//
//            for (int i=0; i<array.length; i++) {
//                System.out.println(array[i]);
//            }

            //TODO [Alert ????????? ?????? ??????]
            getAlertDialog("[OCR] ?????? ?????? ??????",
                    String.valueOf(result),
                    "??????", "", "");

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getAlertDialog(String header, String content, String ok, String no, String normal){

        //TODO ????????? ??? ?????? ??????
        final String Tittle = header;
        final String Message = content;

        //TODO ?????? ?????? ??????
        String buttonNo = no;
        String buttonYes = ok;
        String buttonNature = normal;

        //TODO AlertDialog ????????? ??????
        new AlertDialog.Builder(ocrActivity.this)
                .setTitle(Tittle) //[????????? ????????? ??????]
                //.setIcon(R.drawable.tk_app_icon) //[????????? ????????? ??????]
                .setMessage(Message) //[????????? ?????? ??????]
                .setCancelable(false) //[?????? ???????????? ???????????? ???????????? ?????????????????? ??????]
                .setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                })
                .setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                })
                .setNeutralButton(buttonNature, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ocrImage.setImageBitmap(imageBitmap);
        }
        else if(requestCode == GALLERY && resultCode == RESULT_OK) {
            Cursor cursor = getContentResolver().query(data.getData(), null, null, null, null);
            if (cursor != null) {

                cursor.moveToFirst();
                int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                imagePath = cursor.getString(index);
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                cursor.close();
            }
//                    InputStream ?????? ????????? ????????????
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                ocrImage.setImageBitmap(bitmap);
                Glide.with(this).load(imagePath).into(ocrImage);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}