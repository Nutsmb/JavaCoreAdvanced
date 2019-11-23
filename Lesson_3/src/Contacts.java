import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Contacts {
    private static Map<String, ArrayList<String>> contacts = new HashMap<>();

    public static void main(String[] args) {
        addContact("Rufus", "89999999999");
        addContact("Arus", "89992134999");
        addContact("Gerios", "89569999922");
        addContact("Rufus", "89991111111");
        addContact("Awers", "89999966399");
        addContact("Horius", "89999432099");

        String c1 = "Rufus";
        String c2 = "Awers";

        getContact(c1);
        getContact(c2);

        for(Map.Entry<String, ArrayList<String>> item : contacts.entrySet()){
            getContact(item.getKey());
        }
    }

    public static void addContact(String name, String tel){
        if(!contacts.containsKey(name)){
            contacts.put(name,(new ArrayList()));
        }
        contacts.get(name).add(tel);
    }

    public static void getContact(String name){
        System.out.println(name + " tel: " + contacts.get(name));
    }
}
