package undev.bg;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.ArrayList;

import undev.bg.wait.WaitFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment {

    private Button buttonSignIn;
    private Button buttonSignUp;
    private EditText textviewEmail;
    private EditText textviewPassword;
    private EditText nameText;
    private AlertDialog signUpDialog;
    private AlertDialog.Builder builder;

    private boolean isNewUser = false;

    private FirebaseIO firebaseIO;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(){
        LoginFragment fragment = new LoginFragment();
        ((MainActivity)fragment.getActivity()).fragmentStack = new ArrayList<>();
        fragment.firebaseIO = ((MainActivity)fragment.getActivity()).firebaseIO;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ///////////////////////////////////////////////////////////////////////////////////////


        //////////////////////////////////////////////////////////////////////////////////////


        builder = new AlertDialog.Builder(getContext());
        nameText = new EditText(getContext());
        nameText.setHint("Enter your Name");

        builder.setView(nameText);
        builder.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(nameText.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Your Name is Empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    signUp(textviewEmail.getText().toString(), textviewPassword.getText().toString());
                }
            }
        });

        signUpDialog = builder.create();
        buttonSignIn = (Button) view.findViewById(R.id.login_sign_in);
        buttonSignUp = (Button) view.findViewById(R.id.login_sign_up);
        textviewEmail = (EditText) view.findViewById(R.id.login_email);
        textviewPassword = (EditText) view.findViewById(R.id.login_password);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textviewEmail.getText().toString().equals(""))
                    Toast.makeText(getActivity(), "Enter your Email", Toast.LENGTH_LONG).show();
                else if(textviewPassword.getText().toString().equals(""))
                    Toast.makeText(getActivity(), "Enter your Password", Toast.LENGTH_LONG).show();
                else{
                    signIn(textviewEmail.getText().toString(), textviewPassword.getText().toString());
                }
            }
        });
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textviewEmail.getText().toString().equals(""))
                    Toast.makeText(getActivity(), "Enter your Email", Toast.LENGTH_LONG).show();
                else if(textviewPassword.getText().toString().equals(""))
                    Toast.makeText(getActivity(), "Enter your Password", Toast.LENGTH_LONG).show();
                else {
                    signUpDialog.show();
                }
            }
        });
        return view;
    }

    public void signUp(String email, String password){
        this.isNewUser = true;
        firebaseIO.getFirebaseAuth().createUserWithEmailAndPassword(email, password).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(), "sign up success", Toast.LENGTH_SHORT);
                    signIn(textviewEmail.getText().toString(), textviewPassword.getText().toString());
                }
                else
                    Toast.makeText(getActivity(), "sign up fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void signIn(String email, String password){

        firebaseIO.getFirebaseAuth().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    firebaseIO.setUser(firebaseIO.getFirebaseAuth().getCurrentUser());
                    if(isNewUser){
                        firebaseIO.initUserDatabase();
                        updateUserName(nameText.getText().toString());
                    }
                    firebaseIO.userOnLine();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, WaitFragment.newInstance()).commit();
                    Toast.makeText(getActivity(), "sign in", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getActivity(), "sign in fail", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateUserName(String name) {
        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        firebaseIO.getFirebaseAuth().getCurrentUser().updateProfile(userProfileChangeRequest).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                firebaseIO.setUser(firebaseIO.getCurrentUser());
            }
        });
    }
}
