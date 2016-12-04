package undev.bg;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;


public class GameFragment extends Fragment {

    private static int turn;

    private int boardColumn = 5;
    private int boardRow = 5;
    private int displayWidth;
    private int MaxNumber = 50;



    private Button[] buttons;
    private int[] boardNumber;
    private ArrayList<Integer> bingoOrder;


    private  Random random;

    private GridLayout gridLayout;
    private Button next;
    private Button start;
    private Button bingo;
    private Button metch;
    private TextView turnText;
    private AlertDialog.Builder scoreBuilder;

    public GameFragment() {
        // Required empty public constructor
    }


    public static GameFragment newInstance() {
        GameFragment fragment = new GameFragment();
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
        //////////////////////////////////////////////////////////////////
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next.setEnabled(true);
                start.setEnabled(false);
                turn = 0;
                startGame();
            }
        });
        //////////////////////////////////////////////////////////////////
        next.setEnabled(false);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextTurn();

                setTurnText();
            }
        });
        //////////////////////////////////////////////////////////////////
        bingo.setEnabled(false);
        bingo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBingo() > 0){
                    endGame();
                }
                endGame();
            }
        });
        //////////////////////////////////////////////////////////////////
        setTurnText();
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

        gridLayout = (GridLayout) view.findViewById(R.id.bingo_board_layout);
        next = (Button) view.findViewById(R.id.next_bingo);
        start = (Button) view.findViewById(R.id.start);
        turnText = (TextView) view.findViewById(R.id.turn_text);
        bingo = (Button) view.findViewById(R.id.bingo);

    }

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
    private int searchNumber(int num, ArrayList<Integer> arrayList){
        if(arrayList.contains(num))
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
        int number = bingoOrder.get(bingoOrder.size()-1);
        for(int index =0; index < boardNumber.length; index++){
            if(boardNumber[index] == number){
                buttons[index].setBackgroundColor(Color.CYAN);
                buttons[index].invalidate();
            }

        }

        checkBingo();
    }
    private void setTurnText(){
        turnText.setText(String.valueOf(turn));
    }
    private int checkBingo(){
        return 0;
    }
    private void endGame(){
        start.setEnabled(true);
        next.setEnabled(false);
        //scoreBuilder.create().show();
    }
    private void startGame(){
        setBoardNumber();
        setTurnText();
        this.bingoOrder = new ArrayList<>();
        bingo.setEnabled(true);
    }

    private void nextTurn(){
        bingoOrder.add(nextBingoNumber());
        checkBoardNumber();
        turn++;
        bingo.setText("BINGO["+String.valueOf(checkBingo())+"]");


    }
}