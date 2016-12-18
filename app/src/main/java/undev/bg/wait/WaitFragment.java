package undev.bg.wait;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import undev.bg.BaseFragment;
import undev.bg.GameFragment;
import undev.bg.MainActivity;
import undev.bg.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class WaitFragment extends BaseFragment {
    private DatabaseReference matchRef;
    private ArrayList<String> matchList;

    private Button buttonRank;
    private Button buttonCreate;
    private RecyclerView recyclerViewMatches;

    private MatchContent matchContent;

    private AlertDialog createDialog;
    private AlertDialog.Builder builder;
    public WaitFragment() {
        // Required empty public constructor
    }

    public static WaitFragment newInstance(){
        WaitFragment fragment = new WaitFragment();
        ((MainActivity)fragment.getActivity()).fragmentStack.add(fragment);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wait, container, false);
        init(view);
        databaseReferenceInit();

        final EditText matchTitle = new EditText(getContext());
        builder.setView(matchTitle);
        builder.setPositiveButton("CREATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!matchTitle.getText().equals(""))
                    createNewGame(matchTitle.getText().toString());
            }
        });
        createDialog = builder.create();

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog.show();
            }
        });

        matchContent = new MatchContent();
        recyclerViewMatches.setAdapter(new MatchRecyclerViewAdapter(matchContent.ITEMS));
        return view;
    }
    private void init(View view){
        matchList = new ArrayList<>();
        builder = new AlertDialog.Builder(getContext());

        buttonCreate = (Button) view.findViewById(R.id.button_create);
        recyclerViewMatches = (RecyclerView) view.findViewById(R.id.recycler_matches);
    }
    private void databaseReferenceInit(){
        matchRef = MainActivity.firebaseIO.getDatabaseReference().child("matches");
        matchRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                matchList.add(dataSnapshot.getKey().toString());
                matchContent.addItem(new Match(dataSnapshot.child("title").getValue(String.class), dataSnapshot.getKey().toString()));
                recyclerViewMatches.invalidate();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void createNewGame(String title){
        String matchID = matchRef.push().getKey();
        matchRef.child(matchID).setValue(true);
        matchRef.child(matchID).child("title").setValue(title);
        matchRef.child(matchID).child("player").push().setValue(MainActivity.firebaseIO.getCurrentUser());

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, GameFragment.newInstance(matchID)).commit();
    }
}
