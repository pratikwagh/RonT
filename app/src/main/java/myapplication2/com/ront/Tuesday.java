package myapplication2.com.ront;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Tuesday extends Fragment
{

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        // keep the fragment and all its data across screen rotation
        setRetainInstance(true);
        View rootView = inflater.inflate(R.layout.fragment_tuesday, container, false);

        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent a = new Intent(getActivity(), RoutineCreate.class);
                a.putExtra("value", 1);
                startActivity(a);

            }
        });

        return rootView;
    }





}