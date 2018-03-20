package myapplication2.com.ront;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Friday extends Fragment
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
        View rootView = inflater.inflate(R.layout.fragment_monday, container, false);

        return rootView;
    }





}