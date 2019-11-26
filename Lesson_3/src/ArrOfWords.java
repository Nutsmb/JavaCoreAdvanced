import java.util.HashMap;
import java.util.Map;

public class ArrOfWords {
    public static void main(String[] args) {
        String[] words = {"арбуз", "дыня", "огурец", "лук",
                            "кокос", "томат", "яблоко", "слива",
                            "авокадо", "апельсин", "дыня", "томат",
                            "томат", "апельсин", "дыня", "дыня",
                            "огурец", "арбуз", "арбуз", "арбуз"
        };
        Map<String, Integer> hm = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            Integer res = hm.get(words[i]);
            hm.put(words[i], res == null ? 1 : res + 1);
        }
        System.out.println(hm.keySet());
        for (Map.Entry entry : hm.entrySet()) {
            System.out.println(entry.getKey() + " встречается " + entry.getValue() + "-жды");
        }
    }
}
