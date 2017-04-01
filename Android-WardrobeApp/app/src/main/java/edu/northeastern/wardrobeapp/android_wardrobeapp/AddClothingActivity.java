package edu.northeastern.wardrobeapp.android_wardrobeapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddClothingActivity extends AppCompatActivity implements View.OnClickListener {

    private final int PICK_IMAGE = 12345;
    private final int TAKE_PICTURE = 6352;
    private static final int REQUEST_CAMERA_ACCESS_PERMISSION = 5674;
    private Bitmap bitmap;

    private ImageView imageView;
    private Button fromCamera, fromGallery, addDetails;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clothing);
        imageView = (ImageView) findViewById(R.id.imageView);
        fromCamera = (Button) findViewById(R.id.fromCamera);
        fromGallery = (Button) findViewById(R.id.fromGallery);
        addDetails = (Button) findViewById(R.id.addDetails);
        addDetails.setOnClickListener(this);
        fromCamera.setOnClickListener(this);
        fromGallery.setOnClickListener(this);

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            fromCamera.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fromCamera:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                            REQUEST_CAMERA_ACCESS_PERMISSION);
                } else {
                    getImageFromCamera();
                }
                break;
            case R.id.fromGallery:
                getImageFromGallery();
                break;
            case R.id.addDetails:
                if (bitmap != null)
                    addClothingDetail();
                break;
        }
    }

    private void addClothingDetail() {
        Intent intent = new Intent();
        intent.setClass(this, ClothingDetailedViewActivity.class);
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte [] bitmapByte =baos.toByteArray();
        intent.putExtra("bitmap", bitmapByte);
        startActivity(intent);
    }

    private void getImageFromCamera() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicture.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePicture, TAKE_PICTURE);
        }
    }

    private void getImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    imageView.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        } else if (requestCode == TAKE_PICTURE) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle extras = data.getExtras();
                bitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_ACCESS_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getImageFromCamera();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
