package Model;

public class Time {
    private int hour;
    private int minute;
    private int second;

    public Time() {
        setTime(0, 0, 0);
    }

    public Time(int h, int m, int s) {
        setTime(h, m, s);
    }

    public Time setTime(int h, int m, int s) {
        setHour(h);
        setMinute(m);
        setSecond(s);
        return this;
    }

    public int getHour() {
        return hour;
    }

    public Time setHour(int hour) {
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("Hour must be 0-23");
        }
        this.hour = hour;
        return this;
    }

    public int getMinute() {
        return minute;
    }

    public Time setMinute(int minute) {
        if (minute < 0 || minute > 59) {
            throw new IllegalArgumentException("Minute must be 0-59");
        }
        this.minute = minute;
        return this;
    }

    public int getSecond() {
        return second;
    }

    public Time setSecond(int second) {
        if (second < 0 || second > 59) {
            throw new IllegalArgumentException("Second must be 0-59");
        }
        this.second = second;
        return this;
    }

    public String toString() {
        int displayHour = (hour == 12 || hour == 0) ? 12 : (hour % 12);
        String ampm = (hour < 12) ? "AM" : "PM";
        return String.format("%d:%02d:%02d %s", displayHour, minute, second, ampm);
    }
}
