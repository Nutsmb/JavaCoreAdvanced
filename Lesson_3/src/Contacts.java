import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Contacts {
    private static Map<String, ArrayList<String>> contacts = new HashMap<>();

    public static void main(String[] args) {
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
