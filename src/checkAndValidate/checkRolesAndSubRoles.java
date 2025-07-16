package checkAndValidate;

import models.User;

public class checkRolesAndSubRoles {
    public static boolean checkNewRole(String newRole){
        String[] temp = User.getUserRoles();
        for (String role : temp) {
            if (role.equalsIgnoreCase(newRole)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkSubRoles(String newSubRole){
        String[] eTemp = User.getEmpSubRoles();
        String[] aTemp = User.getAdminSubRoles();

        String[] temp = new String[eTemp.length + aTemp.length];
        System.arraycopy(eTemp, 0, temp, 0, eTemp.length);
        System.arraycopy(aTemp, 0, temp, eTemp.length, aTemp.length);

        for(String s : temp){
            if(s.equalsIgnoreCase(newSubRole)){
                return true;
            }
        }
        return false;
    }

    public static boolean checkAdminSubRoles(String newSubRole){
        String[] temp = User.getAdminSubRoles();
        for(String s : temp){
            if(s.equalsIgnoreCase(newSubRole)){
                return true;
            }
        }
        return false;
    }

    public static boolean checkEmpSubRoles(String newSubRole){
        String[] temp = User.getEmpSubRoles();
        for(String s : temp){
            if(s.equalsIgnoreCase(newSubRole)){
                return true;
            }
        }
        return false;
    }


}
