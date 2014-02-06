package controler;

import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by krzysztof on 06/02/14.
 */
public class Persons {

    /**
     *
     * get students
     *
     */
    public static Vector<Vector<Object>> getStudents(String... columns) {
        StringBuilder query = new StringBuilder("SELECT ");
        for(String s: columns) query.append(s).append(',');
        query.deleteCharAt(query.length()-1).append(" FROM students");
        return Utility.getData(query.toString());
    }

    /**
     *
     * get staff
     *
     */
    public static Vector<Vector<Object>> getStaff(String... columns) {
        StringBuilder query = new StringBuilder("SELECT ");
        for(String s: columns) query.append(s).append(',');
        query.deleteCharAt(query.length()-1).append(" FROM staff");
        return Utility.getData(query.toString());
    }

    /**
     *
     * add student
     *
     */
    public static void addStudent(int studentBook,
                                  String fName,
                                  String sName,
                                  String lName,
                                  String pesel,
                                  String dateOfBirth,
                                  String placeOfBirth,
                                  String address,
                                  String phone,
                                  String mail) throws SQLException {
        StringBuilder insert = new StringBuilder("INSERT INTO students(student_book,first_name,second_name,last_name,pesel,date_of_birth,place_of_birth,address,phone,mail) VALUES('");

        insert.append(Integer.toString(studentBook)).append('\'').append(',')
                .append('\'').append(fName).append('\'').append(',')
                .append((sName != null ? "'"+sName+"'" : "null")).append(',')
                .append('\'').append(lName).append('\'').append(',')
                .append('\'').append(pesel).append('\'').append(',')
                .append('\'').append(dateOfBirth).append('\'').append(',')
                .append('\'').append(placeOfBirth).append('\'').append(',')
                .append((address != null ? "'"+address+"'" : "null")).append(',')
                .append((phone != null ? "'"+phone+"'" : "null")).append(',')
                .append((mail != null ? "'"+mail+"'" : "null"))
                .append(")");

        try {
            Utility.updateData(insert.toString());
        } catch (Exception e) {
            //TODO
        }

    }

    /**
     *
     * add staff
     *
     */
    public static void addStaff(String staffCode,
                                String fName,
                                String sName,
                                String lName,
                                String pesel,
                                String dateOfBirth,
                                String placeOfBirth,
                                String address,
                                String phone,
                                String mail,
                                String academic_title,
                                Integer room,
                                String post,
                                Integer cathedral_id) throws Throwable {
        StringBuilder insert = new StringBuilder("INSERT INTO staff(staff_code,first_name,second_name,last_name,pesel,date_of_birth,place_of_birth,address,phone,mail,academic_title,room,post,cathedral_id) VALUES('");
        insert.append(staffCode).append('\'').append(',')
                .append('\'').append(fName).append('\'').append(',')
                .append((sName != null ? "'"+sName+"'" : "null")).append(',')
                .append('\'').append(lName).append('\'').append(',')
                .append('\'').append(pesel).append('\'').append(',')
                .append('\'').append(dateOfBirth).append('\'').append(',')
                .append('\'').append(placeOfBirth).append('\'').append(',')
                .append((address != null ? "'"+address+"'" : "null")).append(',')
                .append((phone != null ? "'"+phone+"'" : "null")).append(',')
                .append((mail != null ? "'"+mail+"'" : "null")).append(',')
                .append((academic_title != null ? "'"+academic_title+"'" : "null")).append(',')
                .append((room != null ? room : "null")).append(',')
                .append('\'').append(post).append('\'').append(',')
                .append((cathedral_id != null ? cathedral_id : "null"))
                .append(")");

        try {
            Utility.updateData(insert.toString());
        } catch (Exception e) {
            //TODO
        }

    }

    /**
     *
     * change student
     *
     * @param changes: "column1ToChange","newValue1","column2ToChange","newValue2",...
     */
    public static void changeStudent(int studentBook, Object... changes) throws SQLException {
        StringBuilder update = new StringBuilder("UPDATE students SET ");

        for(int i = 0; i < changes.length; i += 2) {
            update.append(changes[i])
                    .append("=")
                    .append(changes[i+1].getClass() == String.class ? "'"+changes[i+1]+"'" : changes[i+1].toString());
            if(i < changes.length - 2) update.append(",");
        }

        update.append(" WHERE student_book=").append(studentBook);
        try {
            Utility.updateData(update.toString());
        } catch (Exception e) {
            //TODO
        }
    }

    /**
     *
     * change staff
     *
     */
    public static void changeStaff(String staffCode, Object... changes) throws SQLException {
        StringBuilder update = new StringBuilder("UPDATE staff SET ");

        for(int i = 0; i < changes.length; i += 2) {
            update.append(changes[i])
                    .append("=")
                    .append(changes[i+1].getClass() == String.class ? "'"+changes[i+1]+"'" : changes[i+1].toString());
            if(i < changes.length - 2) update.append(",");
        }

        update.append(" WHERE staff_code=").append(staffCode);
        try {
            Utility.updateData(update.toString());
        } catch (Exception e) {
            //TODO
        }
    }


    /**
     * For a student or one-time student the function shall fill app.User.studentsBranchesIds with all id-s of this student
     * on different branches and make a corresponding string in app.User.studentsBranchesIdsSQLSet.
     * For a staff member not being a one-time student no results shall be visible and mentioned string shall remain null.
     */
    public static void fillInStudentsBranchesIdsList() {
        Vector<Vector<Object>> vecRes = Utility.getData("select id from students_branches "
                + "where student_id = " + app.User.person_id);
        for (Vector<Object> v : vecRes)
            app.User.studentsBranchesIds.add((Long)v.get(0));
        if(app.User.studentsBranchesIds.isEmpty()) return;
        boolean start = true;
        StringBuilder sb = new StringBuilder();
        for (Long i : app.User.studentsBranchesIds)
        {
            if(start) start = false;
            else sb.append(", ");
            sb.append(i);
        }

        app.User.studentsBranchesIdsAsSQLSet = sb.toString();
        System.out.println(sb);
    }

    /**
     *
     * change person
     *
     */
    public static void changePerson(long person_id, Object... changes) throws SQLException {
        StringBuilder update = new StringBuilder("UPDATE persons SET ");

        for(int i = 0; i < changes.length; i += 2) {
            update.append(changes[i])
                    .append("=")
                    .append(changes[i+1].getClass() == String.class ? "'"+changes[i+1]+"'" : changes[i+1].toString());
            if(i < changes.length - 2) update.append(",");
        }

        update.append(" WHERE id=").append(person_id);

        try {
            Utility.updateData(update.toString());
        } catch (Exception e) {
            //TODO
        }
    }

}