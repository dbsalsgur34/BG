package undev.bg.wait;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import undev.bg.R;
import undev.bg.rank.Rank;

public class MatchRecyclerViewAdapter extends RecyclerView.Adapter<MatchRecyclerViewAdapter.ViewHolder> {

    private final List<Match> mValues;


    public MatchRecyclerViewAdapter(List<Match> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_match, parent, false);

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
        public Match mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            TextView title = (TextView) view.findViewById(R.id.textView_match_title);
            title.setText(view.toString());
        }

        @Override
        public String toString() {
            return mItem.toString();
        }
    }
}
