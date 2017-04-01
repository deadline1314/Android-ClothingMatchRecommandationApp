package edu.northeastern.wardrobeapp.android_wardrobeapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

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
        byte[] bis = showIntent.getByteArrayExtra("bitmap");
        Bitmap bitmap = BitmapFactory.decodeByteArray(bis, 0, bis.length);
        imageView.setImageBitmap(bitmap);

    }
}
