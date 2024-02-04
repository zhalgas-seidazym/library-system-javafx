package g.classes;

import java.util.ArrayList;
import java.util.List;

public class Subscribers {
    private List<Student> subscribedStudents;

    public Subscribers() {
        subscribedStudents = new ArrayList<>();
    }

    public Subscribers(List<Student> subscribedStudents) {
        this.subscribedStudents = subscribedStudents;
    }

    public List<Student> getSubscribedStudents() {
        return subscribedStudents;
    }

    public void addStudent(Student observer) {
        subscribedStudents.add(observer);
    }

    public void removeStudent(Student observer) {
        subscribedStudents.remove(observer);
    }

    public void notifyStudents(String title, String message) {
        for (Student observer : subscribedStudents) {
            Instruments.sendMessage(observer.getEmail(), title, message);
        }
    }
}
