package undev.bg.rank;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import undev.bg.BaseFragment;
import undev.bg.FirebaseIO;
import undev.bg.LoginFragment;
import undev.bg.MainActivity;
import undev.bg.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class RankFragment extends BaseFragment {

    private FirebaseIO firebaseIO;

    private RecyclerView myTopRank;
    private RecyclerView dayTopRank;
    private RecyclerView weekTopRank;
    private RecyclerView monthTopRank;

    private Content myRankContent;
    private Content dayTopRankContent;
    private Content weekTopRankContent;
    private Content monthTopRankContent;

    private Content[] contents;
    private RecyclerView[] recyclerViews;


    Button signOut;
    public RankFragment() {
        // Required empty public constructor
    }


    public static RankFragment newInstance() {
        RankFragment fragment = new RankFragment();
        ((MainActivity)fragment.getActivity()).fragmentStack.add(fragment);
        fragment.firebaseIO = ((MainActivity)fragment.getActivity()).firebaseIO;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_rank, container, false);
        /////////////////////////////////////////////////////////////////////////
        contents = new Content[4];
        myRankContent = new Content();
        dayTopRankContent = new Content();
        weekTopRankContent = new Content();
        monthTopRankContent = new Content();
        contents[0] = myRankContent;
        contents[1] = dayTopRankContent;
        contents[2] = weekTopRankContent;
        contents[3] = monthTopRankContent;
        getRankList();
        /////////////////////////////////////////////////////////////////////////
        recyclerViews = new RecyclerView[4];
        myTopRank = (RecyclerView) view.findViewById(R.id.user_rank);
        dayTopRank = (RecyclerView) view.findViewById(R.id.day_top_rank_list);
        weekTopRank = (RecyclerView) view.findViewById(R.id.week_top_rank_list);
        monthTopRank = (RecyclerView) view.findViewById(R.id.month_top_rank_list);
        recyclerViews[0] = myTopRank;
        recyclerViews[1] = dayTopRank;
        recyclerViews[2] = weekTopRank;
        recyclerViews[3] = monthTopRank;
        /////////////////////////////////////////////////////////////////////////
        for(int index = 0; index < 4; index++){
            recyclerViews[index].setLayoutManager(new LinearLayoutManager(view.getContext()));
            recyclerViews[index].setAdapter(new RankRecyclerViewAdapter(contents[index].ITEMS));
        }

        signOut = (Button) view.findViewById(R.id.rank_sign_out);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();

            }
        });

        return view;
    }


    public boolean signOut(){
        firebaseIO.getFirebaseAuth().signOut();
        Toast.makeText(getContext(), "sign out", Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, LoginFragment.newInstance()).commit();
        return true;
    }

    private void getRankList(){

    }
}
