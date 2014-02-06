package app;

import java.util.LinkedList;

/**
 * Created by krzysztof on 05/02/14.
 */
public class User {
    public static long person_id;
    public static String fName;
    public static String sName;
    public static String lName;
    public static String status;
    public static String address;
    public static String mail;
    public static String phone;
    public static LinkedList<Long> studentsBranchesIds = new LinkedList<>();
    public static String studentsBranchesIdsAsSQLSet = null;
}
