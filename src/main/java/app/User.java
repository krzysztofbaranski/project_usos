package app;

import java.awt.*;
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
    public static Image photo;
    public static LinkedList<Long> studentsBranchesIds = new LinkedList<>();
    public static String studentsBranchesIdsAsSQLSet = null;
}
