package Marathon;
import Marathon.Competitors.Team;
import Marathon.Obstacles.Course;

public class Main {
    public static void main(String[] args) {
        Team team = new Team("Prostokvashino");
        Course c = new Course();
        c.doIt(team); // Просим команду пройти полосу
        team.info(); // Показываем инфо о команде
        team.showResults(); // Показываем инфо об успехах
    }
}