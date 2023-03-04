package com.example.android1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
ImageButton imageButton;
EditText phone,e_search;
Button  search;
ImageView imageView;
ActivityResultLauncher<Intent>activityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageButton=findViewById(R.id.whatsapp);
        phone=findViewById(R.id.phoneNum);
        imageView=findViewById(R.id.galleryicon);
        search=findViewById(R.id.google);

        e_search=findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search =e_search.getText().toString();
                if(!search.equals("")){
                    setSearch(search);
                }

            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                activityResultLauncher.launch(intent);
            }
        });
        activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                        imageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                }
            });




        imageButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                String ph = phone.getText().toString();
                if (!ph.isEmpty()){
                    String url = "whatsapp://send?phone=" + ph;
                    Intent intent=new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
            }
           }
        });

    }
    private void setSearch(String words){
        Intent intent=new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY,words);
        startActivity(intent);
    }
    private void SearchNet(String words){
        Uri uri=Uri.parse("http://www.google.com/#q="+words);
        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
        intent.putExtra(SearchManager.QUERY,words);
        startActivity(intent);
    }
}