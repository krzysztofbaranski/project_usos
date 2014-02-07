 drop function if exists is_correct_pesel(pesel char(11)) cascade;
 drop function if exists is_correct_mail(mail text) cascade;
 drop function if exists pesel_to_date(pesel char(11)) cascade;
 drop function if exists pesel_to_date(pesel char(11)) cascade;
 drop function if exists hours_per_semester(gr_id bigint) cascade;
 drop function if exists get_final_mark(st_id bigint, cou_id bigint) cascade;
 drop function if exists get_etcs(stud_id bigint, branc_id bigint, _year int, sem_nr int) cascade;
 drop function if exists average(stud_id bigint, branc_id bigint, _year int, sem_nr int) cascade;
 drop function if exists change_status_to_student(_person_id student_books.person_id%TYPE, _student_book student_books.student_book%TYPE) cascade;
 drop function if exists change_status_to_teacher(_person_id staff_details.person_id%TYPE, 
                          _staff_code staff_details.staff_code%TYPE,
                          _academic_title staff_details.academic_title%TYPE,
                          _room staff_details.room%TYPE,
                          _post staff_details.post%TYPE,
                          _cathedral_id staff_details.cathedral_id%TYPE) cascade;
 drop function if exists is_it_my_branch(group_id bigint, branc_id bigint) cascade;
 drop function if exists change_student() cascade;
 drop function if exists change_staff() cascade;
 drop function if exists change_staff_details() cascade;
 drop function if exists change_student_books() cascade;
 drop function if exists no_duplicated_groups() cascade;
 drop function if exists no_marks_from_outside_teacher() cascade;
 drop function if exists set_final_mark() cascade;
 drop function if exists semesters_check() cascade;
 
 
 

 drop table if exists "research" cascade;
 drop table if exists "branches" cascade;
 drop table if exists "cathedrals" cascade;
 drop table if exists "courses" cascade;
 drop table if exists "students_associations" cascade;
 drop table if exists "persons" cascade;
 drop table if exists "group_types" cascade;
 drop table if exists "groups" cascade;
 drop table if exists "associations" cascade;
 drop table if exists "departments" cascade;
 drop table if exists "staff_groups" cascade;
 drop table if exists "students_branches" cascade;
 drop table if exists "staff_research" cascade;
 drop table if exists "branches_courses" cascade;
 drop table if exists "institutes" cascade;
 drop table if exists "classes" cascade;
 drop table if exists "student_books" cascade;
 drop table if exists "students_branches__groups" cascade;
 drop table if exists "marks" cascade;
 drop table if exists "staff_details" cascade;
 drop table if exists "statuses" cascade;
 drop table if exists "passwords" cascade;

 drop sequence if exists"departments_id_seq" cascade;
 drop sequence if exists"group_types_id_seq" cascade;
 drop sequence if exists"institutes_id_seq" cascade;
 drop sequence if exists"research_id_seq" cascade;
 drop sequence if exists"statuses_id_seq" cascade;
 drop sequence if exists"associations_id_seq" cascade;
 drop sequence if exists"branches_id_seq" cascade;
 drop sequence if exists"cathedrals_id_seq" cascade;
 drop sequence if exists"courses_id_seq" cascade;
 drop sequence if exists"groups_id_seq" cascade;
 drop sequence if exists"persons_id_seq" cascade;
 drop sequence if exists"staff_groups_staff_id_seq" cascade;
 drop sequence if exists"staff_groups_group_id_seq" cascade;
 drop sequence if exists"students_branches_id_seq" cascade;
 drop sequence if exists"classes_id_seq" cascade;
 drop sequence if exists"marks_id_seq" cascade;

 
