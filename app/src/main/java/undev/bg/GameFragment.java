package undev.bg;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import undev.bg.rank.RankFragment;


public class GameFragment extends BaseFragment {

    private int turn;
    private final int maxUser = 2;
    private FirebaseIO firebaseIO;
    private int boardColumn = 5;
    private int boardRow = 5;
    private int displayWidth;
    private int MaxNumber = 50;
    private String matchID;
    private String[] startMode = {"Make Room", "Choose Room"};

    private ArrayList<String> matchPlayers;
    private ArrayList<DatabaseReference> dbRefs;
    private DatabaseReference matchRef;
    private Button[] buttons;
    private int[] boardNumber;
    private ArrayList<String> bingoOrder;
    private int[] bingoLines;
    private ArrayList<String> matchList;
    private  Random random;

    private GridLayout gridLayout;
    private Button ready;
    private Button start;
    private Button bingo;
    private Button rank;
    private TextView turnText;

    private AlertDialog.Builder scoreBuilder;
    private AlertDialog.Builder startBuilder;
    private AlertDialog.Builder matchBuilder;
    private AlertDialog.Builder chooseBuilder;

    private AlertDialog matchDialog;
    private AlertDialog startDialog;
    private AlertDialog chooseDialog;

    private EditText title;
    private EditText password;
    private EditText message;

    public GameFragment() {
        // Required empty public constructor
    }


    public static GameFragment newInstance(String matchID) {
        GameFragment fragment = new GameFragment();
        ((MainActivity)fragment.getActivity()).fragmentStack.add(fragment);
        fragment.firebaseIO = MainActivity.firebaseIO;
        fragment.matchRef = MainActivity.firebaseIO.getDatabaseReference().child("matches").child(matchID);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        init(view);
        //////////////////////////////////////////////////////////////////
        gridLayout.setBackgroundColor(Color.BLACK);
        gridLayout.setColumnCount(boardColumn);
        gridLayout.setRowCount(boardRow);

        for(int index =0; index<boardColumn*boardRow; index++){
            BingoBoardButton boardButton = new BingoBoardButton(getActivity(),displayWidth/boardColumn,displayWidth/boardColumn);
            gridLayout.addView(boardButton);
            buttons[index] = boardButton;
        }
        /*/////////////////////////////////////////////////////////////////
        bingo.setEnabled(false);
        bingo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBingo() > 0){
                    bingo();
                }
            }
        });
        //////////////////////////////////////////////////////////////////

        //*/
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void init(View view){
        this.displayWidth = getActivity().getResources().getDisplayMetrics().widthPixels;
        this.boardNumber = new int[boardColumn*boardRow];
        buttons = new Button[boardColumn*boardRow];
        bingoLines = new int[boardColumn*boardRow+2];
        matchPlayers = new ArrayList<>();
        dbRefs = new ArrayList<>();

        gridLayout = (GridLayout) view.findViewById(R.id.bingo_board_layout);
        bingo = (Button) view.findViewById(R.id.button_bingo);
        turnText = (TextView) view.findViewById(R.id.turn_text);
        ready = (Button) view.findViewById(R.id.button_ready);

        View v = getActivity().getLayoutInflater().inflate(R.layout.make_match_layout,null);
        title = (EditText) v.findViewById(R.id.text_title);
        password = (EditText) v.findViewById(R.id.text_password);
        message = (EditText) v.findViewById(R.id.text_message);

    }




/*
    private void setBoardNumber(){
        random = new Random(Calendar.getInstance().getTimeInMillis());
        for(int index =0; index < boardColumn*boardRow; index++){
            boardNumber[index] = -1;
            boardNumber[index] = searchNumber(randomInt(), this.boardNumber);
            buttons[index].setText(String.valueOf(boardNumber[index]));
            buttons[index].setBackgroundColor(Color.GRAY);
            buttons[index].invalidate();
        }
    }
    private int randomInt(){
        return random.nextInt(MaxNumber);
    }
    private int searchNumber(int num, ArrayList<String> arrayList){
        if(arrayList.contains(String.valueOf(num)))
            return nextBingoNumber();
        else
            return num;

    }
    private int searchNumber(int num, int[] array){
        int index = 0;
        while(array[index] != -1){
            if(array[index] == num)
                return searchNumber(randomInt(), array);
            index++;
        }
        return num;
    }
    private int nextBingoNumber(){
        return searchNumber(randomInt(),bingoOrder);

    }
    private void checkBoardNumber(){
        int number = Integer.parseInt(bingoOrder.get(bingoOrder.size()-1));

        for(int index =0; index < boardNumber.length; index++){
            if(boardNumber[index] == number){
                buttons[index].setBackgroundColor(Color.CYAN);
                buttons[index].invalidate();
                addToBingoLine(index);
            }
        }
    }
    private void setTurnText(){
        turnText.setText(String.valueOf(turn));
    }
    private int checkBingo(){
        int bingo =0;
        for(int index=0; index < bingoLines.length; index++){
            if(bingoLines[index] == boardColumn){
                bingo++;
            }
        }
        return bingo;
    }
    private void initBingoLines(){
        bingoLines = new int[boardColumn*boardRow+2];
    }
    private void endGame(){
        start.setEnabled(true);
        next.setEnabled(false);
        rank.setEnabled(true);
        initBingoLines();

        //scoreBuilder.create().show();
    }
    private void bingo(){
        matchRef.child("bingo").child("bingoNum").setValue(checkBingo());
        matchRef.child("bingo").child("win").setValue(firebaseIO.getUser().getUid());
        firebaseIO.userSetScore(checkBingo());
    }

    private void startGame(){
        setBoardNumber();
        setTurnText();
        this.bingoOrder = new ArrayList<>();
        rank.setEnabled(false);
        bingo.setEnabled(true);
        next.setEnabled(true);
    }

    private void nextTurn(){

        checkBoardNumber();
        turn++;
        bingo.setText("BINGO["+String.valueOf(checkBingo())+"]");
        if(checkBingo() == 12)
            endGame();
    }

    private void addToBingoLine(int index){
        int column = index%boardColumn;
        int row = index/boardRow;

        bingoLines[column]++;
        bingoLines[boardColumn+row]++;
        if(column == row){
            bingoLines[boardColumn*boardRow]++;
        }
        if(column+row == 4){
            bingoLines[boardColumn*boardRow+1]++;
        }
    }

    private void getInMatch(String matchID){

        if(this.matchID != matchID) {
            if(this.matchID == null)
                firebaseIO.getDatabaseReference().child("matches").child(this.matchID)
                        .child(firebaseIO.getUser().getUid()).removeValue();

            firebaseIO.getDatabaseReference().child("matches").child(matchID)
                .child(firebaseIO.getUser().getUid()).setValue(firebaseIO.getUser().getUid())
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG);
                        }
                    });
            this.matchID = matchID;
            matchRef = firebaseIO.getDatabaseReference().child("matches").child(matchID);
        }
        else{
            firebaseIO.getDatabaseReference().child("matches").child(this.matchID)
                    .child(firebaseIO.getUser().getUid()).removeValue();
            this.matchID = null;
            matchRef = null;
        }

    }

    private void makeMatch(final Match Match){

        Match.matchID = firebaseIO.getDatabaseReference().child("matches").push().getKey();
        firebaseIO.getDatabaseReference().child("matches").child(Match.matchID).setValue(Match.title);
        firebaseIO.getDatabaseReference().child("matches").child(Match.matchID).child("message").setValue(Match.message);
        firebaseIO.getDatabaseReference().child("matches").child(Match.matchID).child("maxPlayer").setValue(Match.maxPlayer);
        firebaseIO.getDatabaseReference().child("matches").child(Match.matchID)
                .child("players").push().setValue(firebaseIO.getCurrentUser().getUid());
        firebaseIO.getDatabaseReference().child("matches").child(Match.matchID)
                .child("players").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                matchPlayers.add(dataSnapshot.getValue().toString());
                if(matchPlayers.size() == 1){
                    //startGame();
                }
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

    class BoardButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Button button = (Button)view;

            bingoOrder.add(button.getText().toString());
            matchRef.child("bingoOrder").push().setValue(bingoOrder.get(bingoOrder.size()-1));
            matchRef.child("nextNumber").setValue(bingoOrder.get(bingoOrder.size()-1));
            nextTurn();
        }
    }

    class MatchButtonClickListener implements View.OnClickListener{
        private String matchID;
        MatchButtonClickListener(String matchID){
            this.matchID = matchID;
        }
        @Override
        public void onClick(View view) {
            getInMatch(matchID);
        }
    }
    //*/
}
