
CREATE TABLE public.associations ( 
	id                   bigserial  NOT NULL,
	name                 varchar(100)  NOT NULL,
	CONSTRAINT pk_student_associations PRIMARY KEY ( id ),
	CONSTRAINT idx_associations UNIQUE ( name ) 
 ) ;

CREATE TABLE public.departments ( 
	id                   bigserial  NOT NULL,
	name                 varchar(100)  NOT NULL,
	address              varchar(100)  NOT NULL,
	CONSTRAINT pk_departments PRIMARY KEY ( id )
 ) ;

CREATE TABLE public.group_types ( 
	id                   serial  NOT NULL,
	type_name            varchar(20)  ,
	CONSTRAINT pk_group_types PRIMARY KEY ( id )
 ) ;

CREATE TABLE public.institutes ( 
	id                   bigserial  NOT NULL,
	department_id        bigint  NOT NULL,
	name                 varchar(100)  NOT NULL,
	address              varchar(100)  NOT NULL,
	CONSTRAINT pk_institutes PRIMARY KEY ( id )
 ) ;

CREATE INDEX idx_institutes ON public.institutes ( department_id ) ;

CREATE TABLE public.research ( 
	id                   bigserial  NOT NULL,
	name                 varchar(100)  NOT NULL,
	CONSTRAINT pk_student_associations_0 PRIMARY KEY ( id ),
	CONSTRAINT idx_research UNIQUE ( name ) 
 ) ;

CREATE TABLE public.statuses ( 
	id                   bigserial  NOT NULL,
	name                 varchar(100)  NOT NULL,
	CONSTRAINT pk_status PRIMARY KEY ( id ),
	CONSTRAINT idx_statuses UNIQUE ( name ) 
 ) ;

CREATE TABLE public.branches ( 
	id                   bigserial  NOT NULL,
	name                 varchar(100)  NOT NULL,
	institute            bigint  ,
	semesters_amt        smallint DEFAULT 10 NOT NULL,
	CONSTRAINT pk_branches PRIMARY KEY ( id )
 ) ;

CREATE INDEX idx_branches ON public.branches ( institute ) ;

CREATE TABLE public.cathedrals ( 
	id                   bigserial  NOT NULL,
	institute_id         bigint  NOT NULL,
	name                 varchar(100)  NOT NULL,
	address              varchar(100)  NOT NULL,
	CONSTRAINT pk_cathedrals PRIMARY KEY ( id )
 ) ;

CREATE INDEX idx_cathedrals ON public.cathedrals ( institute_id ) ;

CREATE TABLE public.courses ( 
	id                   bigserial  NOT NULL,
	name                 varchar(100)  NOT NULL,
	ects                 smallint DEFAULT 0 NOT NULL,
	final_mark_group_type integer  NOT NULL,
	CONSTRAINT pk_courses PRIMARY KEY ( id )
 ) ;

CREATE INDEX idx_courses ON public.courses ( final_mark_group_type ) ;

CREATE TABLE public.groups ( 
	id                   bigserial  NOT NULL,
	name				 varchar(100),
	course_id            bigint  NOT NULL,
	type                 bigint  NOT NULL,
	year                 integer  NOT NULL,
	semester             smallint  NOT NULL,
	CONSTRAINT pk_groups PRIMARY KEY ( id )
 ) ;



ALTER TABLE public.groups ADD CONSTRAINT ck_2 CHECK ( semester in (1, 2) ) ;

CREATE INDEX idx_groups_1 ON public.groups ( course_id ) ;

COMMENT ON COLUMN public.groups.semester IS '1 or 2';

CREATE TABLE public.persons ( 
	id                   bigserial  NOT NULL,
	fname                varchar(100)  NOT NULL,
	sname                varchar(100)  ,
	lname                varchar(100)  NOT NULL,
	pesel                char(11)  NOT NULL UNIQUE,
	date_of_birth        date  NOT NULL,
	place_of_birth       varchar(100)  NOT NULL,
	address              varchar(100)  ,
	phone                char(9)  ,
	mail                 varchar(100)  ,
	photo                bytea  ,
	status_id            bigint  NOT NULL,
	CONSTRAINT pk_person PRIMARY KEY ( id )
 ) ;



COMMENT ON COLUMN public.persons.fname IS 'first name';

COMMENT ON COLUMN public.persons.sname IS 'second name';

COMMENT ON COLUMN public.persons.lname IS 'last name';

COMMENT ON COLUMN public.persons.phone IS 'contact phone number';

COMMENT ON COLUMN public.persons.mail IS 'e-mail address';

COMMENT ON COLUMN public.persons.status_id IS 'status of person';

CREATE TABLE public.staff_details ( 
	staff_code           varchar(12)  NOT NULL,
	person_id            bigint  NOT NULL,
	academic_title       varchar(100)  ,
	room                 numeric(4)  ,
	post                 varchar(30)  NOT NULL,
	cathedral_id         bigint  ,
	CONSTRAINT pk_staff_details PRIMARY KEY ( staff_code ),
	CONSTRAINT idx_staff_details UNIQUE ( person_id ) 
 ) ;

CREATE INDEX idx_staff ON public.staff_details ( cathedral_id ) ;

CREATE TABLE public.staff_groups ( 
	staff_id             bigserial  NOT NULL,
	group_id             bigserial  NOT NULL,
	CONSTRAINT idx_staff_groups_1 UNIQUE ( staff_id, group_id ) 
 ) ;

CREATE INDEX idx_staff_groups ON public.staff_groups ( group_id ) ;

CREATE INDEX idx_staff_groups_0 ON public.staff_groups ( staff_id ) ;

CREATE TABLE public.staff_research ( 
	staff_id             bigint  NOT NULL,
	research_id          bigint  NOT NULL,
	CONSTRAINT idx_staff_research_1 UNIQUE ( staff_id, research_id ) 
 ) ;

CREATE INDEX idx_staff_research ON public.staff_research ( staff_id ) ;

CREATE INDEX idx_staff_research_0 ON public.staff_research ( research_id ) ;

CREATE TABLE public.student_books ( 
	student_book         numeric(8)  NOT NULL,
	person_id            bigint  NOT NULL,
	CONSTRAINT primary_key_students PRIMARY KEY ( student_book ),
	CONSTRAINT idx_student_books UNIQUE ( person_id ) 
 ) ;

CREATE TABLE public.students_associations ( 
	student_id           bigint  NOT NULL,
	association_id       bigint  NOT NULL,
	CONSTRAINT idx_students_associations_1 UNIQUE ( student_id, association_id ) 
 ) ;

CREATE INDEX idx_students_associations ON public.students_associations ( student_id ) ;

CREATE INDEX idx_students_associations_0 ON public.students_associations ( association_id ) ;

CREATE TABLE public.students_branches ( 
	id                   bigserial  NOT NULL,
	student_id           bigint  NOT NULL,
	branch_id            bigint  NOT NULL,
	semester             smallint DEFAULT 1 NOT NULL,
	CONSTRAINT pk_students_branches PRIMARY KEY ( id )
 ) ;

CREATE INDEX idx_students_branches ON public.students_branches ( student_id ) ;

CREATE INDEX idx_students_branches_0 ON public.students_branches ( branch_id ) ;

CREATE TABLE public.students_branches__groups ( 
	student_branch_id    bigint  NOT NULL,
	group_id             bigint  NOT NULL,
	CONSTRAINT pk_students_groups PRIMARY KEY ( student_branch_id, group_id )
 ) ;

CREATE TABLE public.branches_courses ( 
	branch_id            bigint  NOT NULL,
	course_id            bigint  NOT NULL,
	semester_of_branch   smallint  ,
	obligatory           bool  NOT NULL,
	CONSTRAINT idx_branches_courses PRIMARY KEY ( branch_id, course_id )
 ) ;

CREATE INDEX idx_branches_courses_0 ON public.branches_courses ( branch_id ) ;

CREATE INDEX idx_branches_courses_1 ON public.branches_courses ( course_id ) ;

CREATE TABLE public.classes ( 
	id                   bigserial  NOT NULL,
	group_id             bigint  NOT NULL,
	day_of_week          varchar(10)  ,
	start                time ,
	stop                 time ,
	classroom            integer  ,
	CONSTRAINT pk_classes PRIMARY KEY ( id )
 ) ;

ALTER TABLE public.classes ADD CONSTRAINT name_of_day CHECK ( day_of_week = ANY (ARRAY['monday'::bpchar, 'tuesday'::bpchar, 'wednesday'::bpchar, 'thursday'::bpchar, 'friday'::bpchar, 'saturday'::bpchar, 'sunday'::bpchar]) ) ;

ALTER TABLE public.classes ADD CONSTRAINT start_stop CHECK ( start < stop ) ;

CREATE INDEX idx_classes ON public.classes ( group_id ) ;

CREATE TABLE public.marks ( 
	id                   bigserial  NOT NULL,
	student_id           bigint  NOT NULL,
	staff_id             bigint  NOT NULL,
	group_id             bigint  NOT NULL,
	mark                 numeric(2,1)  NOT NULL,
	description          varchar(100)  NOT NULL,
	notice               varchar(1000)  ,
	"date"               timestamp  ,
	is_final_mark        bool DEFAULT 'false' NOT NULL,
	CONSTRAINT pk_mark PRIMARY KEY ( id )
 ) ;


  CREATE TABLE public.passwords (
  	person_id bigint NOT NULL,
  	passwd bytea NOT NULL
  );

insert into statuses values(1,'admin');
insert into persons(fname,lname,pesel,date_of_birth,place_of_birth,status_id,mail) values('admin','admin','-',current_date,'-',1,'admin');
insert into passwords values(1,decode(md5('0'), 'hex'));

ALTER TABLE public.marks ADD CONSTRAINT ck_0 CHECK ( mark = ANY (ARRAY[2.0, 3.0, 3.5, 4.0, 4.5, 5.0]) ) ;

CREATE INDEX idx_marks ON public.marks ( student_id ) ;

CREATE INDEX idx_marks_0 ON public.marks ( group_id ) ;

CREATE INDEX idx_marks_1 ON public.marks ( staff_id ) ;

ALTER TABLE public.passwords ADD CONSTRAINT fk_passwords_persons FOREIGN KEY ( person_id ) REFERENCES public.persons( id )    ;

ALTER TABLE public.institutes ADD CONSTRAINT fk_institutes_departments FOREIGN KEY ( department_id ) REFERENCES public.departments( id )    ;

ALTER TABLE public.branches ADD CONSTRAINT fk_branches_institutes FOREIGN KEY ( institute ) REFERENCES public.institutes( id )    ;

ALTER TABLE public.cathedrals ADD CONSTRAINT fk_cathedrals_institutes FOREIGN KEY ( institute_id ) REFERENCES public.institutes( id )    ;

ALTER TABLE public.courses ADD CONSTRAINT fk_courses_group_types FOREIGN KEY ( final_mark_group_type ) REFERENCES public.group_types( id )    ;


ALTER TABLE public.groups ADD CONSTRAINT fk_groups_group_types FOREIGN KEY ( type ) REFERENCES public.group_types( id )    ;

ALTER TABLE public.persons ADD CONSTRAINT fk_person_status FOREIGN KEY ( status_id ) REFERENCES public.statuses( id )    ;

ALTER TABLE public.staff_details ADD CONSTRAINT fk_staff_persons FOREIGN KEY ( person_id ) REFERENCES public.persons( id )    ;

ALTER TABLE public.staff_details ADD CONSTRAINT fk_staff_cathedrals FOREIGN KEY ( cathedral_id ) REFERENCES public.cathedrals( id )    ;

ALTER TABLE public.staff_groups ADD CONSTRAINT fk_staff_groups_groups FOREIGN KEY ( group_id ) REFERENCES public.groups( id )    ;

ALTER TABLE public.staff_groups ADD CONSTRAINT fk_staff_groups_staff FOREIGN KEY ( staff_id ) REFERENCES public.persons( id )    ;

ALTER TABLE public.staff_research ADD CONSTRAINT fk_staff_research_research FOREIGN KEY ( research_id ) REFERENCES public.research( id )    ;

ALTER TABLE public.staff_research ADD CONSTRAINT fk_staff_research_staff FOREIGN KEY ( staff_id ) REFERENCES public.persons( id )    ;

ALTER TABLE public.student_books ADD CONSTRAINT fk_student_books_persons FOREIGN KEY ( person_id ) REFERENCES public.persons( id )    ;

ALTER TABLE public.students_associations ADD CONSTRAINT fk_students_associations__associations FOREIGN KEY ( association_id ) REFERENCES public.associations( id )    ;

ALTER TABLE public.students_associations ADD CONSTRAINT fk_students_associations__students FOREIGN KEY ( student_id ) REFERENCES public.persons( id )    ;

ALTER TABLE public.students_branches ADD CONSTRAINT fk_students_branches_branches FOREIGN KEY ( branch_id ) REFERENCES public.branches( id )    ;

ALTER TABLE public.students_branches ADD CONSTRAINT fk_students_branches_students FOREIGN KEY ( student_id ) REFERENCES public.persons( id )    ;

ALTER TABLE public.students_branches__groups ADD CONSTRAINT fk_students_groups_groups FOREIGN KEY ( group_id ) REFERENCES public.groups( id )    ;

ALTER TABLE public.students_branches__groups ADD CONSTRAINT fk_students_groups_students FOREIGN KEY ( student_branch_id ) REFERENCES public.students_branches( id )    ;

ALTER TABLE public.branches_courses ADD CONSTRAINT fk_branches_courses_branches FOREIGN KEY ( branch_id ) REFERENCES public.branches( id )    ;

ALTER TABLE public.branches_courses ADD CONSTRAINT fk_branches_courses_courses FOREIGN KEY ( course_id ) REFERENCES public.courses( id )    ;

ALTER TABLE public.classes ADD CONSTRAINT fk_classes_groups FOREIGN KEY ( group_id ) REFERENCES public.groups( id )    ;

ALTER TABLE public.marks ADD CONSTRAINT fk_marks_groups FOREIGN KEY ( group_id ) REFERENCES public.groups( id )    ;

ALTER TABLE public.marks ADD CONSTRAINT fk_marks_students FOREIGN KEY ( student_id, group_id ) REFERENCES public.students_branches__groups( student_branch_id, group_id )    ;

ALTER TABLE public.marks ADD CONSTRAINT fk_marks_staff FOREIGN KEY ( staff_id ) REFERENCES public.persons( id )    ;


CREATE VIEW public.staff AS SELECT staff_code,staff_details.person_id, persons.fname AS first_name, persons.sname AS second_name, persons.lname AS last_name, persons.pesel, persons.date_of_birth, persons.place_of_birth, persons.address, persons.phone, persons.mail, staff_details.academic_title, staff_details.room, post, staff_details.cathedral_id FROM persons, staff_details WHERE (persons.id = staff_details.person_id);;

CREATE VIEW public.students AS SELECT student_books.person_id, student_books.student_book, persons.fname AS first_name, persons.sname AS second_name, persons.lname AS last_name, persons.pesel, persons.date_of_birth, persons.place_of_birth, persons.address, persons.phone, persons.mail FROM persons, student_books WHERE (persons.id = student_books.person_id);;

--------------------------------------------------------------------
--                      pesel VALIDATOR                           --
--------------------------------------------------------------------
create or replace function is_correct_pesel(pesel char(11)) 
returns bool as $$
declare
      p int;
begin
      p := (1 * substr(pesel,1,1)::int +
            3 * substr(pesel,2,1)::int +
            7 * substr(pesel,3,1)::int +
            9 * substr(pesel,4,1)::int +
            1 * substr(pesel,5,1)::int +
            3 * substr(pesel,6,1)::int +
            7 * substr(pesel,7,1)::int +
            9 * substr(pesel,8,1)::int +
            1 * substr(pesel,9,1)::int +
            3 * substr(pesel,10,1)::int +
            1 * substr(pesel,11,1)::int) % 10;

      if p = 0 then return true; else return false; end if;
exception
      when invalid_text_representation then return false;
end;
$$ language plpgsql;


--------------------------------------------------------------------
--                     E-MAIL VALIDATOR                           --
--------------------------------------------------------------------
create or replace function is_correct_mail(mail text) 
returns bool as $$
begin
    if mail !~ '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$' then
        return false;
    end if;
    return true;
end;
$$ language plpgsql;


--------------------------------------------------------------------
--                 DATE OF BIRTH FROM pesel                       --
--------------------------------------------------------------------
create or replace function pesel_to_date(pesel char(11)) 
returns date as $$
declare
    day char(2);
    month char(2);
    year char(4);
    m int;
    date_of_birth date;
begin
    if pesel is null or not is_correct_pesel(pesel) then raise exception 'invalid pesel'; end if;

    day = substr(pesel,5,2);
    month = substr(pesel,3,2);
    m = month::int;
    
    if(m > 0 and m <= 12) then year = '19' || substr(pesel,1,2); end if;
    if(m > 20 and m <= 32) then year = '20' || substr(pesel,1,2); month = ((m - 20) / 10)::char || (m % 10)::char; end if;
    if(m > 40 and m <= 52) then year = '21' || substr(pesel,1,2); month = ((m - 40) / 10)::char || (m % 10)::char; end if;
    if(m > 60 and m <= 72) then year = '22' || substr(pesel,1,2); month = ((m - 60) / 10)::char || (m % 10)::char; end if;
    if(m > 80 and m <= 92) then year = '18' || substr(pesel,1,2); month = ((m - 80) / 10)::char || (m % 10)::char; end if;
        
    date_of_birth = (year || '-' || month || '-' || day)::date;
    return date_of_birth;
end;
$$ language plpgsql;


--------------------------------------------------------------------
--                 Hours per semester                             --
--------------------------------------------------------------------

CREATE OR REPLACE FUNCTION public.hours_per_semester(gr_id bigint)
 RETURNS int
 LANGUAGE plpgsql
AS $function$
begin
      return EXTRACT(hours from (select sum(stop - start)*20 from classes where classes.group_id = gr_id));
end;
$function$
;


------------------------------------------------------------------------------
--                     Final mark of whole subject.                         --
------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION get_final_mark(st_id bigint, cou_id bigint)
 RETURNS numeric(2,1)
AS $$
declare
	res numeric(2, 1);
begin
--only the best mark, because the subject could have been repeated
        res = (select max(mark) from ((groups join courses on groups.course_id = courses.id) join marks on groups.id = marks.group_id)
            where cou_id = courses.id and courses.final_mark_group_type = groups.type and marks.is_final_mark and student_id = st_id);
        if res is null then
        	raise exception 'Brak oceny';
       	else return res;
       	end if;
end;
$$
LANGUAGE plpgsql;




------------------------------------------------------------------------------
--                     Counts ECTS from a semester.                         --
------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION public.get_etcs(stud_id bigint, branc_id bigint, _year int, sem_nr int)
 RETURNS int
AS $function$
declare
    stud_branch_id bigint;
begin
    if sem_nr not in (1, 2) then raise exception 'Number od semester must be 1 or 2';
    end if;
    
    perform * from students_branches where student_id = stud_id and branch_id = branc_id;
    if not found then 
        raise exception 'This student does not attend this branch.';
    end if;
    
    stud_branch_id = (select id from students_branches where student_id = stud_id and branch_id = branc_id);
    return 
    	coalesce(
    		(select sum(ects) from (groups join students_branches__groups on group_id = groups.id)
                    join (courses join branches_courses on course_id = courses.id) on groups.course_id = courses.id
                     where student_branch_id = stud_branch_id
                        and _year = year and sem_nr = semester
                        and groups.type = courses.final_mark_group_type),
         0);
end;
$function$
LANGUAGE plpgsql;


------------------------------------------------------------------------------
--                     Average mark from a semester.                        --
------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION public.average(stud_id bigint, branc_id bigint, _year int, sem_nr int)
 RETURNS numeric(3, 2)
AS $function$
declare
    stud_branch_id bigint;
begin
    if sem_nr not in (1, 2) then raise exception 'Number od semester must be 1 or 2';
    end if;

    perform * from students_branches where student_id = stud_id and branch_id = branc_id;
    if not found then 
        raise exception 'This student does not attend this branch.';
    end if;
    
    stud_branch_id = (select id from students_branches where student_id = stud_id and branch_id = branc_id);
    return (select round(sum(ects*get_final_mark(stud_id, courses.id))/sum(ects), 2) from (groups join students_branches__groups on group_id = groups.id)
                   join (courses join branches_courses on course_id = courses.id) on groups.course_id = courses.id
                            where student_branch_id = stud_branch_id and year = _year and semester = sem_nr);
end;
$function$
LANGUAGE plpgsql;


  
  
--------------------------------------------------------------------
--                 CHANGE STATUS TO STUDENT                       --
--------------------------------------------------------------------
create or replace function change_status_to_student(_person_id student_books.person_id%TYPE, _student_book student_books.student_book%TYPE) 
returns void as $$
declare
    student_st persons.status_id%TYPE := (SELECT id FROM statuses WHERE name='student');
    teacher_st persons.status_id%TYPE := (SELECT id FROM statuses WHERE name='teacher');
begin
    PERFORM status_id FROM persons WHERE id=_person_id;
    if not found then raise exception 'the person does not exist'; end if;

    if (SELECT status_id FROM persons WHERE id=_person_id)=student_st then return;
    elseif (SELECT status_id FROM persons WHERE id=_person_id)=teacher_st then 
        DELETE FROM staff_details WHERE person_id = _person_id;
        INSERT INTO student_books(student_book,person_id) VALUES(_student_book,_person_id);
        UPDATE persons SET status_id=student_st WHERE id=_person_id;
    else
        INSERT INTO student_books(student_book,person_id) VALUES(_student_book,_person_id);
        UPDATE persons SET status_id=student_st WHERE id=_person_id;
    end if;
end;
$$ language plpgsql;


--------------------------------------------------------------------
--                 CHANGE STATUS TO TEACHER                       --
--------------------------------------------------------------------
create or replace function change_status_to_teacher(_person_id staff_details.person_id%TYPE, 
                                                    _staff_code staff_details.staff_code%TYPE,
                                                    _academic_title staff_details.academic_title%TYPE,
                                                    _room staff_details.room%TYPE,
                                                    _post staff_details.post%TYPE,
                                                    _cathedral_id staff_details.cathedral_id%TYPE) 
returns void as $$
declare
    student_st persons.status_id%TYPE := (SELECT id FROM statuses WHERE name='student');
    teacher_st persons.status_id%TYPE := (SELECT id FROM statuses WHERE name='teacher');
begin
    PERFORM status_id FROM persons WHERE id=_person_id;
    if not found then raise exception 'the person does not exist'; end if;

    if (SELECT status_id FROM persons WHERE id=_person_id)=teacher_st then return;
    elseif (SELECT status_id FROM persons WHERE id=_person_id)=student_st then 
        DELETE FROM student_books WHERE person_id = _person_id;
        INSERT INTO staff_details(staff_code,person_id,academic_title,room,post,cathedral_id) VALUES(_staff_code,_person_id,_academic_title,_room,_post,_cathedral_id);
        UPDATE persons SET status_id=teacher_st WHERE id=_person_id;
    else
        INSERT INTO staff_details(staff_code,person_id,academic_title,room,post,cathedral_id) VALUES(_staff_code,_person_id,_academic_title,_room,_post,_cathedral_id);
        UPDATE persons SET status_id=teacher_st WHERE id=_person_id;
    end if;
end;
$$ language plpgsql;



--------------------------------------------------------------------------------------
--                Returns true if given group referes to given branch               --
--------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION public.is_it_my_branch(group_id bigint, branc_id bigint)
 RETURNS bool
AS $function$
begin
    perform * from branches_courses where branch_id = branc_id and course_id = 
        (select course_id from groups where id = group_id);
    if found then 
        return true;
    else
        return false;
    end if;
        
end;
$function$
LANGUAGE plpgsql;


--------------------------------------------------------------------
--                 DELETE PERSONS FORBIDDEN                       --
--------------------------------------------------------------------
create or replace rule persons_rule as
	on delete to persons
	do instead nothing;


           

--------------------------------------------------------------------
--                     CHANGE STUDENTS                            --
--------------------------------------------------------------------
create or replace function change_student() 
returns trigger as $$
declare
	_id persons.id%TYPE;
begin
    ----------------------------------------------------------- INSERT
    if tg_op='INSERT' then
        if new.date_of_birth is null or new.date_of_birth != pesel_to_date(new.pesel) then
            raise exception 'invalid date of birth or pesel';
        end if;
            
        if new.first_name is null or new.first_name !~ '^[A-Z][a-z]+$' then raise exception 'invalid first name'; end if;
        if new.second_name is not null and new.second_name !~ '^[A-Z][a-z]+$' then raise exception 'invalid second name'; end if;
        if new.last_name is null or new.last_name !~ '^[A-Z][a-z]+$' then raise exception 'invalid last name'; end if;


        if new.mail is null then
            new.mail = (select new.first_name || '.' || new.last_name || '@uj.edu.pl');
        end if;
        
        if not is_correct_mail(new.mail) then
            raise exception 'e-mail is invalid';
        end if;

        
        perform * from student_books where student_book = new.student_book;
        if found then raise exception 'student book already exist'; end if;
                
        INSERT INTO persons(fname,sname,lname,pesel,date_of_birth,place_of_birth,address,phone,mail,status_id) 
            VALUES(new.first_name,new.second_name,new.last_name,new.pesel,new.date_of_birth,new.place_of_birth,new.address,new.phone,new.mail,(SELECT id FROM statuses WHERE name='student')) RETURNING id INTO _id;
        INSERT INTO student_books(student_book,person_id) VALUES(new.student_book,_id);
    ----------------------------------------------------------- UPDATE    
    elseif tg_op='UPDATE' then
        if new.student_book<>old.student_book or new.person_id<>old.person_id then raise exception 'update forbidden'; end if;
        if new.pesel is null or new.date_of_birth != pesel_to_date(new.pesel) then raise exception 'invalid date of birth or pesel'; end if;
        
        if new.mail is null then
          new.mail = (select new.first_name || '.' || new.last_name || '@uj.edu.pl');
        end if;
      
        if not is_correct_mail(new.mail) then raise exception 'e-mail is invalid'; end if;
        
        UPDATE persons SET  fname=new.first_name,
                            sname=new.second_name,
                            lname=new.last_name,
                            pesel=new.pesel,
                            date_of_birth=new.date_of_birth,
                            place_of_birth=new.place_of_birth,
                            address=new.address,
                            phone=new.phone,
                            mail=new.mail
                        WHERE id=new.person_id;
            
    ----------------------------------------------------------- DELETE
    elseif tg_op='DELETE' then
        DELETE FROM student_books WHERE person_id = old.person_id;
        UPDATE persons SET status_id = (SELECT id FROM statuses WHERE name='former student') WHERE id = old.person_id;
    end if;
    return new;
end;
$$ language plpgsql;

create trigger students_trigger
    instead of insert or update or delete on students
    for each row
    execute procedure change_student();
    
    
    
--------------------------------------------------------------------
--                       CHANGE STAFF                             --
--------------------------------------------------------------------
create or replace function change_staff() 
returns trigger as $$
declare
    _id persons.id%TYPE;
begin
    ----------------------------------------------------------- INSERT
    if tg_op='INSERT' then
        if new.date_of_birth is null or new.date_of_birth != pesel_to_date(new.pesel) then
            raise exception 'invalid date of birth or pesel';
        end if;
    
        if new.first_name is null or new.first_name !~ '^[A-Z][a-z]+$' then raise exception 'invalid first name'; end if;
        if new.second_name is not null and new.second_name !~ '^[A-Z][a-z]+$' then raise exception 'invalid second name'; end if;
        if new.last_name is null or new.last_name !~ '^[A-Z][a-z]+$' then raise exception 'invalid last name'; end if;
        

        if new.mail is null then
            new.mail = (select new.first_name || '.' || new.last_name || '@uj.edu.pl');
        end if;

        if new.mail is null or not is_correct_mail(new.mail) then
            raise exception 'e-mail is invalid';
        end if;
        
        perform * from staff_details where staff_code = new.staff_code;
        if found then raise exception 'staff code already exist'; end if;
        
        INSERT INTO persons(fname,sname,lname,pesel,date_of_birth,place_of_birth,address,phone,mail,status_id) 
            VALUES(new.first_name,new.second_name,new.last_name,new.pesel,new.date_of_birth,new.place_of_birth,new.address,new.phone,new.mail,(SELECT id FROM statuses WHERE name='teacher')) RETURNING id INTO _id;
        INSERT INTO staff_details(staff_code,person_id,academic_title,room,post,cathedral_id) VALUES(new.staff_code,_id,new.academic_title,new.room,new.post,new.cathedral_id);
    ----------------------------------------------------------- UPDATE    
    elseif tg_op='UPDATE' then
        if new.staff_code<>old.staff_code or new.person_id<>old.person_id then raise exception 'update forbidden'; end if;
        if new.pesel is null or new.date_of_birth != pesel_to_date(new.pesel) then raise exception 'invalid date of birth or pesel'; end if;
        
        if new.first_name is null or new.first_name !~ '^[A-Z][a-z]+$' then raise exception 'invalid first name'; end if;
        if new.second_name is not null and new.second_name !~ '^[A-Z][a-z]+$' then raise exception 'invalid second name'; end if;
        if new.last_name is null or new.last_name !~ '^[A-Z][a-z]+$' then raise exception 'invalid last name'; end if;
        
        if new.mail is null then
            new.mail = (select new.first_name || '.' || new.last_name || '@uj.edu.pl');
        end if;

        if not is_correct_mail(new.mail) then raise exception 'e-mail is invalid'; end if;
        
        UPDATE persons SET  fname=new.first_name,
                            sname=new.second_name,
                            lname=new.last_name,
                            pesel=new.pesel,
                            date_of_birth=new.date_of_birth,
                            place_of_birth=new.place_of_birth,
                            address=new.address,
                            phone=new.phone,
                            mail=new.mail
                        WHERE id=new.person_id;

		UPDATE staff_details SET academic_title=new.academic_title,
                            room=new.room,
                            post=new.post,
                            cathedral_id=new.cathedral_id
                            WHERE person_id=new.person_id;
    ----------------------------------------------------------- DELETE
    elseif tg_op='DELETE' then
        DELETE FROM staff_details WHERE person_id = old.person_id;
        UPDATE persons SET status_id = (SELECT id FROM statuses WHERE name='former teacher') WHERE id = old.person_id;
    end if;
    return new;
end;
$$ language plpgsql;

create trigger staff_trigger
    instead of insert or update or delete on staff
    for each row
    execute procedure change_staff();


--------------------------------------------------------------------
--                      INSERT PERSON                             --
--------------------------------------------------------------------
create or replace function change_person()
returns trigger as $$
begin
	insert into passwords(person_id,passwd) values(new.id,decode(md5(new.mail), 'hex'));
    return new;
end;
$$ language plpgsql;

create trigger person_trigger
    after insert on persons
    for each row
    execute procedure change_person();


--------------------------------------------------------------------
--                      UPDATE PERSON                             --
--------------------------------------------------------------------
create or replace function update_person()
returns trigger as $$
begin
	if old.id != new.id then
		raise exception 'update id forbidden';
	end if;

	if new.mail is null or not is_correct_mail(new.mail) then
		raise exception 'e-mail is invalid';
	end if;
	return new;
end;
$$ language plpgsql;

create trigger person_trigger
    before update on persons
    for each row
    execute procedure update_person();


--------------------------------------------------------------------
--                      CHANGE PASSWD                             --
--------------------------------------------------------------------
create or replace function change_passwd(_person_id bigint, _passwd text)
returns void as $$
begin
	update passwords set passwd = decode(md5(_passwd), 'hex') where person_id = _id;
end;
$$ language plpgsql;



--------------------------------------------------------------------
--          STAFF DETAILS trigger - not recommended               --
-------------------------------------------------------------------- 

CREATE OR REPLACE FUNCTION public.change_staff_details()
 RETURNS trigger
AS $function$
begin
    
    if tg_op='UPDATE' then
        if new.staff_code<>old.staff_code or new.person_id<>old.person_id then raise exception 'update forbidden'; end if;
    end if;
    
    perform * from student_books where person_id = NEW.person_id;
    if found then raise exception 'This is a student, not a staff member.';
    else
        return NEW;
    end if;
end;
$function$
LANGUAGE plpgsql;



create trigger staff_details_trigger
    before insert or update on staff_details
    for each row
    execute procedure change_staff_details();



--------------------------------------------------------------------
--          STUDENT BOOKS trigger - not recommended               --
-------------------------------------------------------------------- 

CREATE OR REPLACE FUNCTION public.change_student_books()
 RETURNS trigger
AS $function$
begin
    
    if tg_op='UPDATE' then
        if new.student_book<>old.student_book or new.person_id<>old.person_id then raise exception 'update forbidden'; end if;
    end if;
    
    perform * from staff_details where person_id = NEW.person_id;
    if found then raise exception 'This is a staff memeber, not a student.';
    else
        return NEW;
    end if;
end;
$function$
LANGUAGE plpgsql;



create trigger student_books_trigger
    before insert or update on student_books
    for each row
    execute procedure change_student_books();

----------------------------------------------------------------------------------------------
--A student can't attend more than one class of the same type of one subject in the semester--
----------------------------------------------------------------------------------------------


CREATE OR REPLACE FUNCTION public.no_duplicated_groups()
 RETURNS trigger
AS $function$
begin
      perform * from students_branches__groups join groups on group_id = groups.id
            where student_branch_id = NEW.student_branch_id and course_id = (select course_id from groups where groups.id = NEW.group_id)
            and type = (select type from groups where groups.id = NEW.group_id);
      if found then
            raise exception 'You are already signed to an equivalent group.';
      end if;
      return NEW;
end;
$function$
LANGUAGE plpgsql;

CREATE TRIGGER no_duplicated_groups_trigger BEFORE INSERT OR UPDATE ON students_branches__groups
FOR EACH ROW EXECUTE PROCEDURE no_duplicated_groups();


------------------------------------------------------------------------------
--             Teachers can add a mark only to their groups.                --
------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION public.no_marks_from_outside_teacher()
 RETURNS trigger
AS $function$
begin
      perform * from staff_groups where group_id = NEW.group_id and staff_id = NEW.staff_id;
      if not found then
        raise exception 'You are not teaching this group.';
      end if;
      return NEW;
end;
$function$
LANGUAGE plpgsql;

CREATE TRIGGER no_marks_from_otside_teacher_trigger BEFORE INSERT OR UPDATE ON marks
FOR EACH ROW EXECUTE PROCEDURE no_marks_from_outside_teacher();



------------------------------------------------------------------------------
--                           Only one final mark.                           --
------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION public.set_final_mark()
 RETURNS trigger
AS $function$
begin
      if not NEW.is_final_mark then
        return NEW;
      end if;
      
      perform * from marks where is_final_mark and group_id = NEW.group_id and student_id = NEW.student_id and id != NEW.id;
      if found then
        raise exception 'This mark already exists.';
      end if;
      return NEW;
end;
$function$
LANGUAGE plpgsql;

CREATE TRIGGER final_mark_trigger BEFORE INSERT OR UPDATE ON marks
FOR EACH ROW EXECUTE PROCEDURE set_final_mark();



--------------------------------------------------------------------------------
--Semester number in branches_courses can't exceed nuber of branch's semesters--
--------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION public.semesters_check()
 RETURNS trigger
AS $function$
begin
      if (select semesters_amt from branches where id = new.branch_id) < new.semester_of_branch then
        raise exception 'Number of semesters exceeded.';
      end if;
      return NEW;
end;
$function$
LANGUAGE plpgsql;

CREATE TRIGGER semesters_check_trigger BEFORE INSERT OR UPDATE ON branches_courses
FOR EACH ROW EXECUTE PROCEDURE semesters_check();








INSERT INTO statuses VALUES(2,'student');
INSERT INTO statuses VALUES(3,'teacher');
INSERT INTO statuses VALUES(4,'former student');
INSERT INTO statuses VALUES(5,'former teacher');
INSERT INTO students(student_book,first_name,second_name,last_name,pesel,date_of_birth,place_of_birth,address,mail,phone) VALUES(100001,'Krzysztof','Kamil','Adamiak','93123116598','1993-12-31','Warszawa','Warszawa',NULL,'839273645'); 
INSERT INTO students(student_book,first_name,second_name,last_name,pesel,date_of_birth,place_of_birth,address,mail,phone) VALUES(100002,'Aleksandra','Katarzyna','Kantorowska','94092364849','1994-09-23','Nisko','Nisko',NULL,'546325978'); 
INSERT INTO students(student_book,first_name,second_name,last_name,pesel,date_of_birth,place_of_birth,address,mail,phone) VALUES(100003,'Blanka','Ilona','Albrychiewicz','94021568940','1994-02-15','Wadowice','Wadowice',NULL,'652974136'); 
INSERT INTO students(student_book,first_name,second_name,last_name,pesel,date_of_birth,place_of_birth,address,mail,phone) VALUES(100004,'Aneta','Laura','Altenberger','94043059745','1994-04-30','Dobczyce','Dobczyce',NULL,'945689123'); 
INSERT INTO students(student_book,first_name,second_name,last_name,pesel,date_of_birth,place_of_birth,address,mail,phone) VALUES(100005,'Agata','Danuta','Anders','92010146582','1992-01-01','Krakow','Krakow',NULL,'126794561'); 
INSERT INTO students(student_book,first_name,second_name,last_name,pesel,date_of_birth,place_of_birth,address,mail,phone) VALUES(100006,'Iwona','Dominika','Lubaszewska','90090613541','1990-09-06','Krakow','Krakow',NULL,'695689123'); 
INSERT INTO students(student_book,first_name,second_name,last_name,pesel,date_of_birth,place_of_birth,address,mail,phone) VALUES(100007,'Damian','Cezary','Machnacki','88100861273','1988-10-08','Krakow','Krakow',NULL,'469752314'); 
INSERT INTO students(student_book,first_name,second_name,last_name,pesel,date_of_birth,place_of_birth,address,mail,phone) VALUES(100008,'Dariusz','Andrzej','Mirecki','93122764990','1993-12-27','Warszawa','Krakow',NULL,'869125469'); 
INSERT INTO students(student_book,first_name,second_name,last_name,pesel,date_of_birth,place_of_birth,address,mail,phone) VALUES(100009,'Patryk',NULL,'Rydelek','93110246503','1993-11-02','Lublin','Krakow',NULL,'193728591'); 
INSERT INTO students(student_book,first_name,second_name,last_name,pesel,date_of_birth,place_of_birth,address,mail,phone) VALUES(100010,'Karol','Julian','Czulej','92112364598','1992-11-23','Katowice','Lublin',NULL,'164859132'); 
INSERT into departments (name, address) values('WMiI', 'Lojasiewicza 6');
INSERT into institutes (department_id, name, address) values(1, 'ZKiZIM', 'Lojasiewicza 6, 3 pietro');
INSERT into cathedrals (institute_id, name, address) values(1, 'katedra algorytmiki', 'Lojasiewicza 6, pokoj 404');
INSERT into staff values('matecki.g', NULL, 'Grzegorz', NULL, 'Matecki', '81070268945','1981-07-02','Szczecin','Krakow',NULL,NULL, 'dr', 1, 'naukowo-dydaktyczny', 1);
insert into research (name) VALUES ('pewien zespol badawaczy');
insert into staff_research VALUES (12, 1);
insert into associations (name) VALUES ('kolo informatykow');
insert into students_associations VALUES (1, 1);
INSERT into branches (name, institute, semesters_amt) values (
'informatyka analityczna', 1, 6);
INSERT into group_types (type_name) values ('wyklad');
INSERT into courses (name, ects, final_mark_group_type) values ('ID', 6, 1);
INSERT into groups (name, course_id, type, year, semester) VALUES ('ID wyklad', 1, 1, 2013, 1);
INSERT into group_types (type_name) values ('cwiczenia');
INSERT into groups (name, course_id, type, year, semester) values ('ID cw gr 1', 1, 2, 2013, 1);
insert into classes (group_id, day_of_week, start, stop, classroom) VALUES (2, 'friday', '10:15', '11:45', 0162);
insert into classes (group_id, day_of_week, start, stop, classroom) VALUES (2, 'friday', '12:15', '13:45', 0162);
insert into classes (group_id, day_of_week, start, stop, classroom) VALUES (1, 'friday', '8:30', '10:00', 0174);
insert into staff_groups VALUES (12, 1);
insert into staff_groups VALUES (12, 2);
insert into branches_courses VALUES (1, 1, 3, 'true');
insert into students_branches (student_id, branch_id, semester) VALUES (1, 1, 3);
insert into students_branches (student_id, branch_id, semester) VALUES (2, 1, 3);
insert into students_branches (student_id, branch_id, semester) VALUES (3, 1, 1);
insert INTO students_branches__groups VALUES (1, 1);
insert INTO students_branches__groups VALUES (2, 1);
insert INTO students_branches__groups VALUES (1, 2);
insert INTO students_branches__groups VALUES (2, 2);
insert into marks (student_id , staff_id, group_id, mark, description, is_final_mark) VALUES (1, 12, 2, 3.5, 'projekt', false);
insert into marks (student_id , staff_id, group_id, mark, description, is_final_mark) VALUES (2, 12, 2, 5, 'projekt', false);
insert into marks (student_id , staff_id, group_id, mark, description, is_final_mark) VALUES (2, 12, 2, 5, 'koncowa-cw', true);
insert into marks (student_id , staff_id, group_id, mark, description, is_final_mark) VALUES (2, 12, 1, 5, 'koncowa-wyk', true);
insert into marks (student_id , staff_id, group_id, mark, description, is_final_mark) VALUES (1, 12, 2, 3, 'koncowa-cw', true);
insert into marks (student_id , staff_id, group_id, mark, description, is_final_mark) VALUES (1, 12, 1, 2, 'koncowa-wyk', true);

                
