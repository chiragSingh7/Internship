package checkAndValidate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class checkName {

    public static boolean validName(String name){
        Pattern pattern = Pattern.compile("^[A-Za-z]+(\\s([A-Za-z]+))*$");
        Matcher matcher = pattern.matcher(name);

        return matcher.matches();
    }
}
