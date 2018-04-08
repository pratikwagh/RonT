package myapplication2.com.ront;

/**
 * Created by user on 8/4/18.
 */

public class EachScheduleEvent {

    private String title, starttime, endtime;

    public EachScheduleEvent() {
    }

    public EachScheduleEvent(String title, String starttime, String endtime) {
        this.title = title;
        this.starttime = starttime;
        this.endtime = endtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
}

