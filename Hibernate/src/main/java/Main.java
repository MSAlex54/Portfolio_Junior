import models.Auto;
import models.User;
import services.UserService;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserService();
        User userBob = new User("Bob", 30);
        service.saveUser(userBob);
        User userAlex = new User("Alex", 27);
        Auto ferrari = new Auto("Ferrari", "red");
        ferrari.setUser(userBob);
        userBob.addAuto(ferrari);
        Auto ford = new Auto("Ford", "white");
        ford.setUser(userBob);
        userBob.addAuto(ford);
        service.updateUser(userBob);
        Auto merc = new Auto("Mercedes", "yellow");
        merc.setUser(userAlex);
        userAlex.addAuto(merc);
        service.saveUser(userAlex);
        printList(service);

        userAlex.setAge(31);
        service.updateUser(userAlex);
        printList(service);

        service.deleteUser(userBob);
        printList(service);
    }

    public static void printList(UserService service){
        for (User u: service.findAllUsers()) {
            System.out.println(u.toString());
            for (Auto a: u.getAutos()) {
                System.out.println(a.toString());
            }
        }
    }
}
