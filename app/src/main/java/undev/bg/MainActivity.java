package undev.bg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import undev.bg.wait.WaitFragment;


public class MainActivity extends AppCompatActivity {

    public static ArrayList<BaseFragment> fragmentStack;
    public static FirebaseIO firebaseIO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.fragmentStack = new ArrayList<>();
        firebaseIO = new FirebaseIO(FirebaseDatabase.getInstance(), FirebaseAuth.getInstance(), FirebaseStorage.getInstance());

        if(firebaseIO.isSignIn()){
            firebaseIO.setUser(firebaseIO.getCurrentUser());
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, WaitFragment.newInstance()).commit();
        }
        else
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, LoginFragment.newInstance()).commit();
    }

    @Override
    public void onBackPressed(){
        if(fragmentStack.size() == 0)
            super.onBackPressed();
        else if(fragmentStack.size() == 1){
            fragmentStack.remove(fragmentStack.size()-1);
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, GameFragment.newInstance()).commit();
        }
        else{
            fragmentStack.remove(fragmentStack.size()-1);
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, fragmentStack.get(fragmentStack.size()-1)).commit();

        }

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

}
