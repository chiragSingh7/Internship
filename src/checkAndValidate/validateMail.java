package checkAndValidate;

public class validateMail {

    //validate the email before feeding in the database
    public static boolean validMail(String mail){
        int n = mail.length();

        //check whether the mail isn't just @gmail.com or something like that
        if(mail.startsWith("@")){
            return false;
        }

        if(!mail.contains("@")){
            return false;
        }

        if(mail.indexOf("@") != mail.lastIndexOf("@")){
            return false;
        }

        //array of allowed and not allowed Strings and characters
        String[] domain = {"@gmail.com" , "@yahoo.com" , "@hotmail.com" , "@icloud.com"};
        char[] flag = {' ', ',', '<', '>', ':', ';', '{', '}', '[', ']', '(', ')'};
        char[] notAllowed = {'_', '!', '#', '$', '%', '&', '*', '^', '(', ')', ' ', ',', '<', '>', ':', ';', '{', '}', '[', ']'};

        //splitting the mail at the '@' index to check both the parts separately

        String pre = mail.substring(0, mail.indexOf("@"));
        String post = mail.substring(mail.indexOf("@"));

        //creating two booleans for pre(w1) and post(w2)
        boolean w1 = true;
        boolean w2 = false;

        //checking for pre acc to flag array
        for(char c : flag){
            if(pre.indexOf(c) != -1){
                w1 = false;
                break;
            }
        }

        //checking for post acc to domain array
        for(String temp : domain){
            if(post.equals(temp)){
                w2 = true;
                break;
            }
        }

        //again checking for post acc to notAllowed array
        for(char c : notAllowed){
            if(post.indexOf(c) != -1){
                w2 = false;
                break;
            }
        }

        // if both are true only then valid mail
        return w1 && w2;
    }
}


