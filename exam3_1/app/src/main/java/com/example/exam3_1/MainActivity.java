package com.example.exam3_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_PHOTO = 1;
    TextView textTxt;
    EditText inputUrl;
    EditText inputShareTxt;
    ImageButton photoBtn;
    String currentPhotoPath;
    Uri photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textTxt = findViewById(R.id.inputTxt);
        inputUrl = findViewById(R.id.inputURL);
        inputShareTxt = findViewById((R.id.inputShareTxt));
        photoBtn = findViewById(R.id.photoBtn);
    }

    public void openSubActivity(View v) {
        Intent intent = new Intent(this,SubActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, textTxt.getText().toString());
        startActivity(intent);
    }

    public void openWebBrowser(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("https://"+inputUrl.getText());
        intent.setData(uri);
        startActivity(intent);
    }

    public void shareBtnClick(View v) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,inputShareTxt.getText().toString());

        Intent chooser = Intent.createChooser(intent,"공유");
        startActivity(chooser);
    }

    public void photoBtnClick(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //startActivityForResult(intent, REQUEST_PHOTO);

        if (intent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this, "com.example.exam3_1", photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent,REQUEST_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_PHOTO && resultCode == RESULT_OK) {
            //Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            //photoBtn.setImageBitmap(bitmap);
            photoBtn.setImageURI(photoURI);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",  /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
