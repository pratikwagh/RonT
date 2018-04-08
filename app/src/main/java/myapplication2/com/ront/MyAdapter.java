package myapplication2.com.ront;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Stack;

/**
 * Created by user on 8/4/18.
 */


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Stack<String> mDataset;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView Title,StartTime,EndTime;
        public ViewHolder(TextView v) {
            super(v);

            Title=(TextView) v.findViewById(R.id.title);
            StartTime=(TextView) v.findViewById(R.id.startTime);
            EndTime=(TextView) v.findViewById(R.id.endTime);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Stack<String> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_listrow, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String event = mDataset.get(position);

        String[] Array = event.split(",");


            String name = Array[0];
            String stime = Array[1];
            String etime = Array[2];

        holder.Title.setText(name);
        holder.StartTime.setText(stime);
        holder.EndTime.setText(etime);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
