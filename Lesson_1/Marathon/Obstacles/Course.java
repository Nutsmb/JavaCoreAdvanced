package Marathon.Obstacles;
import Marathon.Competitors.Competitor;
import Marathon.Competitors.Team;

public class Course {
    Obstacle[] course;

    public Course() {
        course = new Obstacle[]{new Cross(80), new Water(2), new Wall(1)};
    }

    public void doIt(Team _team){
        for (Competitor c : _team.getCompetitorsArray()) {
            for (Obstacle o : course) {
                o.doIt(c);
                if (!c.isOnDistance()) break;
            }
            System.out.println();
        }

    }
}
