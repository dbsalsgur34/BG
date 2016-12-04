package undev.bg.rank;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import undev.bg.R;

public class RankRecyclerViewAdapter extends RecyclerView.Adapter<RankRecyclerViewAdapter.ViewHolder> {

    private final List<Rank> mValues;


    public RankRecyclerViewAdapter(List<Rank> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_rank, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public Rank mItem;
        public int rank;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            TextView rank = (TextView) mView.findViewById(R.id.rank);
            TextView userName = (TextView) mView.findViewById(R.id.rank_username);
            TextView score = (TextView) mView.findViewById(R.id.rank_score);

            rank.setText(this.rank);
            userName.setText(mItem.userName);
            score.setText(mItem.score);
        }

        @Override
        public String toString() {
            return mItem.toString();
        }
    }
}
