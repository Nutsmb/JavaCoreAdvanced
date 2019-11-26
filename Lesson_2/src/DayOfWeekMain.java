import java.time.DayOfWeek;

public class DayOfWeekMain {

    public static void main(final String[] args) {
        System.out.println(getWorkingHours(DayOfWeek.MONDAY));
    }

    public static String getWorkingHours (DayOfWeek day) {
        if (day.ordinal() < 5) {
            return ("Осталось " + (5 - day.ordinal()) * 8 + " рабочих часов");
        }
        else {
            return ("Сегодня выходной");
        }
    }
}
