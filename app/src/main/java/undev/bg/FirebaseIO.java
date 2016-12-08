package undev.bg;

import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by mhy on 2016-12-08.
 */

public class FirebaseIO {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    public FirebaseIO(FirebaseDatabase firebaseDatabase, FirebaseAuth firebaseAuth, FirebaseStorage firebaseStorage){
        this.firebaseAuth = firebaseAuth;
        this.firebaseDatabase = firebaseDatabase;
        this.databaseReference = firebaseDatabase.getReference();
        this.firebaseStorage = firebaseStorage;
        this.storageReference = firebaseStorage.getReference();
    }

    public boolean isSignIn(){
        if(firebaseAuth.getCurrentUser() != null)
            return true;
        else
            return false;
    }

    public FirebaseUser getCurrentUser(){
        return firebaseAuth.getCurrentUser();
    }



    public FirebaseDatabase getFirebaseDatabase() {
        return firebaseDatabase;
    }

    public FirebaseStorage getFirebaseStorage() {
        return firebaseStorage;
    }

    public StorageReference getStorageReference() {
        return storageReference;
    }

    public FirebaseAuth getFirebaseAuth(){
        return firebaseAuth;
    }

    public FirebaseUser getUser() {
        return user;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
    }

    public void initUserDatabase(){
        databaseReference = firebaseDatabase.getReference("user");
        databaseReference.child(user.getUid()).child("score").setValue(0);
        databaseReference.child(user.getUid()).child("is online").setValue(true);
    }
    public void userOffLine(){
        databaseReference.child(user.getUid()).child("is online").setValue(false);
    }
    public void userOnLine(){
        databaseReference.child(user.getUid()).child("is online").setValue(true);
    }
    public void userSetScore(int score){
        databaseReference.child(user.getUid()).child("score").setValue(score);
    }
}
