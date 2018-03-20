package myapplication2.com.ront;

/**
 * Created by user on 7/3/18.
 */
public class Task {
    private String name,time,date;

    public Task(){


    }

    public Task(String name,String time,String date){

        this.name=name;
        this.date=date;
        this.time=time;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }
}
