package Model;

public class Time {
    private int hour;
    private int minute;
    private int second;

    public Time() {
    }

    public Time(int hour, int minute, int second) {
        setTime(hour, minute, second);
    }

    public static Time now() {
        java.time.LocalTime t = java.time.LocalTime.now();
        return new Time(t.getHour(), t.getMinute(), t.getSecond());
    }

    public int getHour() {
        return hour;
    }

    public Time setHour(int h) {
        this.hour = ((h >= 0 && h < 24) ? h : 0);
        return this;
    }

    public int getMinute() {
        return minute;
    }

    public Time setMinute(int m) {
        this.minute = ((m >= 0 && m < 60) ? m : 0);
        return this;
    }

    public int getSecond() {
        return second;
    }

    public Time setSecond(int s) {
        this.second = ((s >= 0 && s < 60) ? s : 0);
        return this;
    }

    public Time setTime(int h, int m, int s) {
        setHour(h);
        setMinute(m);
        setSecond(s);
        return this;
    }

    public void display() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        int displayHour = (hour == 12 || hour == 0) ? 12 : (hour % 12);
        String h = (hour < 12) ? " AM" : " PM";
        String m = (minute < 10 ? "0" : "") + minute;
        String s = (second < 10 ? "0" : "") + second;
        return displayHour + ":" + m + ":" + s + h;
    }
}
