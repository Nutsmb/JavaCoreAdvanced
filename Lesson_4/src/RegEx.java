import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegEx {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите пароль:");
        String password = in.next();
        //System.out.print(password);
        System.out.println(checkPassword(password));
    }

    public static boolean checkPassword(String pass){
        boolean result = false;
        // не сладил с отлавливанием одного числа =( пропускает более одного
        /*Pattern p = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}");
        Matcher m = p.matcher(pass);
        return m.matches();*/

        Pattern numberSymbolsPattern = Pattern.compile("[0-9A-Za-z]{8,}");
        Pattern numberPattern = Pattern.compile("[A-Za-z]*[0-9][A-Za-z]*");
        Pattern lowerCasePattern = Pattern.compile("\\w+[a-z]+\\w+");
        Pattern upperCasePattern = Pattern.compile("\\w+[A-Z]+\\w+");
        boolean num = (numberPattern.matcher(pass)).matches();
        boolean lower = (lowerCasePattern.matcher(pass)).matches();
        boolean upper = (upperCasePattern.matcher(pass)).matches();
        boolean symbols = (numberSymbolsPattern.matcher(pass)).matches();
        if(num && lower && upper && symbols){
            result = true;
        }
        return result;
    }
}
