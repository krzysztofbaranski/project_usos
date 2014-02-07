package controler;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * Created by krzysztof on 03/02/14.
 */
public class Learning {

    public static String getGroupSQL(long groupId) {
        return "SELECT groups.id, groups.name,"
                + "group_types.type_name, year, semester, course_id, "
                + "courses.name, (select hours_per_semester(" + groupId +
                ")) from (groups join group_types on "
                + "(groups.type = group_types.id)) join courses on "
                + "(course_id = courses.id) where groups.id = " + groupId;
    }


    public static String getGroupTeachersSQL(long groupId) {
        return "SELECT persons.id, fname, lname, academic_title "
                + "from persons join staff_groups on (staff_id = persons.id) "
                + "join groups on (group_id = groups.id) left join staff_details on staff_details.person_id = persons.id "
                + "where groups.id = " + groupId;
    }


    public static String getGroupStudentsSQL(long groupId, boolean branchInfoToo) {
        return "select persons.id, fname, lname "
                + ((branchInfoToo) ? ("branch_id, branches.name, students_branches.semester ") : "")
                + "from groups join students_branches__groups "
                + "on group_id = id join students_branches on student_branch_id "
                + " = students_branches.id join persons on student_id = persons.id join branches on branch_id = branches.id "
                + "where group_id = " + groupId;

    }

    public static List<Long> getStudentIds(long id)
    {
        Vector<Vector<Object>> resVect = Utility.getData("select id from "
                + "students_branches where student_id = " +id);
        LinkedList<Long> l = new LinkedList<Long>();
        for (Vector v : resVect)
            l.add((Long)v.get(0));
        return l;
    }


    public static String getStudentIdFromStudentBranchId(long SBid)
    {
        return "select student_id from students_branches where id = " +  SBid;
    }


    public static boolean groupDetailsPermissionTeacher (long groupId) {
        Vector<Vector<Object>> vres;
        vres = Utility.getData("SELECT (select count(*) from "
                + "staff_groups join groups on (group_id = groups.id) "
                + "where groups.id = " + groupId + "and staff_id = " + app.User.person_id +
                ") > 0");
        return (boolean)vres.get(0).get(0);
    }

    public static boolean groupDetailsPermissionStudent (long groupId) {

        if(app.User.studentsBranchesIds.isEmpty()) return false;
        Vector<Vector<Object>> vres =
                Utility.getData("SELECT (select count(*) from "
                        + "students_branches__groups join groups on "
                        + "(group_id = groups.id) "
                        + "where groups.id = " + groupId +
                        " and student_branch_id in (" + app.User.studentsBranchesIdsAsSQLSet + ")) > 0");
        return (boolean)vres.get(0).get(0);
    }

    public static String getClasses (long groupId) {
        return "select day_of_week, start, stop, classroom from classes where group_id = " + groupId;
    }

    /**
     *
     * @param groupId value -1 means all the courses and groups
     * Ignored if allCourses is true
     * @param onlyFinal if set, only final/all the final marks shall be returned
     * @return
     */

    public static String getMarks (long groupId, long studentId, boolean onlyFinal) {
        boolean allCourses = (groupId == -1);
        boolean allStudents = (studentId == -1);
        String res =  "select " +
                (allStudents ? "marks.student_id, STU.fname, STU.lname,": " staff_id, TEA.fname, TEA.lname, ") +
                (allCourses ? "group_id, groups.name," : "") +
                "mark, description, notice, date " +
                (!onlyFinal ? ", is_final_mark ": "") +
                "from marks join students_branches on marks.student_id = "
                + "students_branches.id join persons STU on "
                + "students_branches.student_id = STU.id " +
                "join persons TEA on staff_id = TEA.id " +
                (allCourses ? "join groups on group_id = groups.id ": "") +
                "where true" + (!allCourses ? " and group_id=" + groupId : "") +
                (!allStudents ? " and STU.id = " + studentId : "") +
                (onlyFinal ? " and is_final_mark " : "") +
                " order by date desc";
        return res;
    }

    /**
     * @param semNr must be 1 or 2
     */
    public static String getAverage (long studentId, long branchId, long year,
                                     long semNr) {
        return "select average(" + studentId + ", " + branchId + ", " +
                year + ", " + semNr + ")";
    }

    /**
     * This function can be called during registration to warn someone who wants
     * to sign up to other-branch group.
     */
    public static boolean isItMyBranch(long groupId, long branchId){
        return (boolean)Utility.getData("select is_it_my_branch(" + groupId +
                ", " + branchId + ")").get(0).get(0);
    }

    public static String getBranch (long branchId)
    {
        return "select branches.id, branches.name, institute, institutes.name,"
                + "semesters_amt from branches join institutes "
                + "on institute = institutes.id";
    }

    /**
     * @param semester -1 means all the courses
     */
    public static String getCoursesOnBranch (long branchId, long semester,
                                             boolean dontPrlongOptional, boolean dontPrlongObligatory)
    {
        return "select courses.id, courses.name, courses.ects, semester_of_branch, "
                + "obligatory from courses join branches_courses "
                + "on course_id = courses.id where branch_id = " + branchId +
                (semester != -1 ? " and semester_of_branch = " + semester : "") +
                (dontPrlongObligatory ? " and not obligatory " : "") +
                (dontPrlongOptional ? " and obligatory " : "");
    }

    public static String getCourse(long courseId)
    {
        return "select name, ects from courses where id = " + courseId;
    }

    /**
     * @param year -1 means all groups. Argument semester shall be ignored
     * in this case
     */
    public static String getCourseGroups(long courseId, long year, long semester)
    {
        return "select groups.id, name, type_name " +
                (year == -1 ? ", year, semester " : "") +
                " from groups join group_types on group_types.id = groups.type " +
                " where course_id = " + courseId +
                (year != -1 ? "and year = " + year + " and semester = " + semester : "");
    }

    public static String getFinalMarks (long studentBranchId)
    {
        return "select courses.id, courses.name, "
                + "(select get_final_mark( "+studentBranchId + ", course_id))"
                + " from courses join groups on course_id = courses.id"
                + " join students_branches__groups on group_id = groups.id"
                + " where student_branch_id = " + studentBranchId;
    }
}
