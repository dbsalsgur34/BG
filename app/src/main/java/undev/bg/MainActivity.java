package undev.bg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static ArrayList<BaseFragment> fragmentStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.fragmentStack = new ArrayList<>();

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
}
