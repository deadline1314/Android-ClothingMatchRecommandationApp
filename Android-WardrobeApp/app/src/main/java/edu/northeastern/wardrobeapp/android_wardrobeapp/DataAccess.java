package edu.northeastern.wardrobeapp.android_wardrobeapp;


import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

public class DataAccess {
    // Firebase variables
    private StorageReference storageRef;

    public DataAccess() {
        storageRef = FirebaseStorage.getInstance().getReference();
    }

    // Firebase AUTH Functions.............

    // Firebase STORAGE Functions...........

    // tODO: function to store detail data then upload file
//    public String saveClothingData(String fileName, Bitmap image) {
//
//        OnSuccessListener successListener = new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                @SuppressWarnings("VisibleForTests")
//                Uri downloadUrl = taskSnapshot.getDownloadUrl();
//            }
//        }
//        this.saveToStorage(fileName, image);
//    }

    /**
     * Saves off an image bitmap to firebase. Needs an onSuccess and onFailure handler.
     *
     * @param fileName - Name of file without unique identified
     * @param userId - The firebase uuid
     * @param fileObj - Image file
     * @param onSuccess - Override onSuccess(UploadTask.TaskSnapshot taskSnapshot). Do a taskSnapshot.getDownloadUrl() to get the uploaded image URL
     * @param onFailure - Override onFailure(@NonNull Exception exception)
     */
    public void saveToStorage(String fileName, String userId, File fileObj, OnSuccessListener<UploadTask.TaskSnapshot> onSuccess, OnFailureListener onFailure) {
        // Create storage reference
        String fullName = fileName.concat(getFileNameSuffix());
        StorageReference filePath = storageRef.child(userId).child(WardrobeApp.STORAGE_DIR_CLOTHING).child(fullName);

        // Use path
        try {
            InputStream stream = new FileInputStream(fileObj);
            UploadTask uploadTask = filePath.putStream(stream);
            uploadTask.addOnSuccessListener(onSuccess).addOnFailureListener(onFailure);
        } catch(FileNotFoundException err) {
            err.printStackTrace();
        }
    }

    /**
     * Generates a unique file name prefix for captured photos
     * @return String
     */
    public String getFileNameSuffix() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return "_" + timeStamp + ".png";
    }
}
