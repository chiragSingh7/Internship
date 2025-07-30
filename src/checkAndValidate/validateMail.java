package checkAndValidate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validateMail {

    public static boolean isValidMail(String mail){
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z+.]+$");
        Matcher matcher = pattern.matcher(mail);

        return matcher.matches();
    }

    public static boolean validDomain(String mail){
        String[] domain = {"@gmail.com" , "@yahoo.com" , "@hotmail.com" , "@icloud.com"};

        boolean w2 = false;

        if(mail.contains("@")){
            String post = mail.substring(mail.indexOf("@"));

            for(String temp : domain){
                if(post.equals(temp)){
                    w2 = true;
                    break;
                }
            }
        }else{
            return false;
        }

        return w2;
    }
}


