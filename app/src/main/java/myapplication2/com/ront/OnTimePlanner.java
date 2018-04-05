package myapplication2.com.ront;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

public class OnTimePlanner extends AppCompatActivity {

    CalendarView calendarView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_time_planner);


        calendarView=(CalendarView)findViewById(R.id.cal);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = i2+"-"+(i1+1)+"-"+i;

                Intent intent=new Intent();

                intent.setClass(OnTimePlanner.this,onPlannerDate.class);
                intent.putExtra("Date",date);
                OnTimePlanner.this.startActivity(intent);
            }
        });


    }
}
