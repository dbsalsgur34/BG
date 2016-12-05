package undev.bg;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    Button signIn;
    Button signUp;
    EditText email;
    EditText password;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(){
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Toast.makeText(getContext(), "firebase sign in", Toast.LENGTH_LONG).show();

                    if (user != null) {
                        // Name, email address, and profile photo Url
                        String name = user.getDisplayName();
                        String email = user.getEmail();
                        Uri photoUrl = user.getPhotoUrl();

                        // The user's ID, unique to the Firebase project. Do NOT use this value to
                        // authenticate with your backend server, if you have one. Use
                        // FirebaseUser.getToken() instead.
                        String uid = user.getUid();
                    }
                    Toast.makeText(getContext(), "firebase sign in", Toast.LENGTH_LONG).show();
                } else {
                    // User is signed out
                    Toast.makeText(getContext(), "firebase sign out", Toast.LENGTH_LONG).show();
                }
                // ...
            }
        };


        View view = inflater.inflate(R.layout.fragment_login, container, false);

        signIn = (Button) view.findViewById(R.id.login_sign_in);
        signUp = (Button) view.findViewById(R.id.login_sign_up);
        email = (EditText) view.findViewById(R.id.login_email);
        password = (EditText) view.findViewById(R.id.login_password);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString());
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString());
            }
        });
        return view;
    }

}
