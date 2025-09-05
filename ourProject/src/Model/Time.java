package Model;

public class Time {
    public int hour;
    public int minute;
    public int second;

    // Constructor set time 0 0 0
    public Time(){
        hour = 0;
        minute = 0;
        second = 0;
    }

    // Setter
    public Time setHour(int hour){
        this.hour = (( hour > 0 && hour < 24) ? hour : 0);
        return this;
    }

    public Time setMinute(int minute){
        this.minute = ((minute > 0 && minute <60) ? minute : 0);
        return this;
    }

    public Time setSecond (int second){
        this.second = ((second > 0 && second < 60) ? second : 0);
        return this;
    }

    public Time setTime (int hour, int minute, int second){
        setHour(hour);
        setMinute(minute);
        setSecond(second);
        return this;
    }

    // Getter
    public int getHour(){
        return hour;
    }

    public int getMinute(){
        return minute;
    }

    public int getSecond(){
        return second;
    }

    public void printTime(){
        System.out.println("Time:" + hour + ":" + (minute < 10 ? "0" : "") + minute + ":" + (second < 10 ? "0" : "") + second + (hour < 12 ? "AM" : "PM"));
    }
}
