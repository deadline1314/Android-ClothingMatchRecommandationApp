package edu.northeastern.wardrobeapp.android_wardrobeapp;


import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DataAccess {
    // Firebase variables
    private StorageReference mStorageRef;
    // User-related variables
    private String userId;

    public DataAccess() {
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    // Firebase AUTH Functions.............

    public String getUserId() {
        return userId;
    }

    public void setUserId(String uid) {
        this.userId = uid;
    }

    public boolean isUserLoggedIn() {
        return userId != null;
    }
}
