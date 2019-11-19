package Lesson_1.Marathon.Competitors;

public class Team{
    String name;
    Competitor[] competitors;


    public Team(String _name) {
        this.name = _name;
        competitors = new Competitor[]{new Human("ДядяФёдор"), new Human("ВераПавловна"), new Cat("Матроскин"), new Dog("Шарик")};
    }

    public void info() {
        System.out.println("Команда "+this.name);
        for (Competitor c : competitors) {
            c.info();
        }
        System.out.println();
    }

    public void showResults() {
        System.out.println("В команде " + this.name + " успешно прошли все испытания:");
        for (Competitor c : this.getCompetitorsArray()) {
            if(c.isOnDistance())
                c.info();
        }
    }

    public Competitor[] getCompetitorsArray() {
        return competitors;
    }
}


