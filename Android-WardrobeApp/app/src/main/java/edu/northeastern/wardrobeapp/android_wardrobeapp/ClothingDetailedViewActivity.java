package edu.northeastern.wardrobeapp.android_wardrobeapp;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.content.Intent;

public class ClothingDetailedViewActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button save;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing_detailed_view);
        imageView = (ImageView) findViewById(R.id.imageViewSmall);
        save = (Button) findViewById(R.id.saveDetails);
        Intent showIntent = getIntent();
        Bitmap bitmap = (Bitmap) showIntent.getParcelableExtra("bitmap");
        imageView.setImageBitmap(bitmap);

    }
}