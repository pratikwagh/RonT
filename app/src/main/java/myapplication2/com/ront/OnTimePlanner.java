package myapplication2.com.ront;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

import java.util.Calendar;

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

                //making date format
                String date = i2+"/"+(i1+1)+"/"+i;
                String dayName="error";

                //grtting day of week
                Calendar calendar = Calendar.getInstance();

                calendar.set(2018, 4, 15);
                calendar.set(i, i1, i2);
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

                switch (dayOfWeek){
                    case Calendar.SUNDAY:
                        dayName="Sunday";

                        break;
                    case Calendar.MONDAY:
                        dayName="Monday";

                        break;
                    case Calendar.TUESDAY:
                        dayName="Tuesday";

                        break;
                    case Calendar.WEDNESDAY:
                        dayName="Wednesday";

                        break;
                    case Calendar.THURSDAY:
                        dayName="Thursday";

                        break;
                    case Calendar.FRIDAY:
                        dayName="Friday";

                        break;
                    case Calendar.SATURDAY:
                        dayName="Saturday";

                        break;
                }


                Intent intent=new Intent();

                Log.d("msg", "reached");

                intent.setClass(OnTimePlanner.this,onPlannerDate.class);
                intent.putExtra("Date",date);
                intent.putExtra("Day",dayOfWeek);
                intent.putExtra("DayName",dayName);
                OnTimePlanner.this.startActivity(intent);
            }
        });


    }
}
