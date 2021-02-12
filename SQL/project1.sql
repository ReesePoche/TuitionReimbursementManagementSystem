-- drop entangling constraints

ALTER TABLE employees DROP CONSTRAINT fk_supervisor_to_employees;
ALTER TABLE departments DROP CONSTRAINT fk_departments_to_employees;
ALTER TABLE tuition_reimbursement_requests DROP CONSTRAINT fk_requests_to_employees;
ALTER TABLE tuition_reimbursement_requests DROP CONSTRAINT fk_requests_to_messages;
ALTER TABLE request_notes DROP CONSTRAINT fk_notes_to_requests;
ALTER TABLE request_notes DROP CONSTRAINT fk_notes_to_employees;
ALTER TABLE request_attachments DROP CONSTRAINT fk_attachments_to_requests;
ALTER TABLE request_attachments DROP CONSTRAINT fk_attachments_to_employees;
ALTER TABLE submitted_grades DROP CONSTRAINT fk_grades_to_requests;
ALTER TABLE submitted_grades DROP CONSTRAINT fk_employees_to_requests;
ALTER TABLE grade_documentation_and_presentations DROP CONSTRAINT fk_grade_docs_to_grades;
ALTER TABLE grade_notes DROP CONSTRAINT fk_grade_notes_to_grades;
ALTER TABLE grade_notes DROP CONSTRAINT fk_grade_notes_to_employees;
ALTER TABLE reimbursement_payouts DROP CONSTRAINT fk_payouts_to_requests;
ALTER TABLE reimbursement_messages DROP CONSTRAINT fk_messages_to_requests;
ALTER TABLE reimbursement_messages DROP CONSTRAINT fk_messages_to_reciever;
ALTER TABLE reimbursement_messages DROP CONSTRAINT fk_messages_to_sender;


DROP TRIGGER employees_pk_insert;
DROP TRIGGER departments_pk_insert;
DROP TRIGGER tuition_reimbursement_requests_pk_insert;
DROP TRIGGER request_notes_pk_insert;
DROP TRIGGER request_attachments_pk_insert;
DROP TRIGGER submitted_grades_pk_insert;
DROP TRIGGER grade_documentation_and_presentations_pk_insert;
DROP TRIGGER grade_notes_pk_insert;
DROP TRIGGER reimbursement_payouts_pk_insert;
DROP TRIGGER reimbursement_messages_pk_insert;


--DROP TABLES
DROP TABLE employees;
DROP TABLE departments;
DROP TABLE tuition_reimbursement_requests;
DROP TABLE request_notes;
DROP TABLE request_attachments;
DROP TABLE submitted_grades;
DROP TABLE grade_documentation_and_presentations;
DROP TABLE grade_notes;
DROP TABLE reimbursement_payouts;
DROP TABLE reimbursement_messages;

DROP SEQUENCE employees_pk_sequence;
DROP SEQUENCE departments_pk_sequence;
DROP SEQUENCE tuition_reimbursement_requests_pk_sequence;
DROP SEQUENCE request_notes_pk_sequence;
DROP SEQUENCE request_attachments_pk_sequence;
DROP SEQUENCE submitted_grades_pk_sequence;
DROP SEQUENCE grade_documentation_and_presentations_pk_sequence;
DROP SEQUENCE grade_notes_pk_sequence;
DROP SEQUENCE reimbursement_payouts_pk_sequence;
DROP SEQUENCE reimbursement_messages_pk_sequence;



COMMIT;

-- making employee table


CREATE TABLE employees (
    id          NUMBER(10), 
    email       VARCHAR2(30) NOT NULL,
    password    VARCHAR2(30) NOT NULL,
    first_name  VARCHAR2(20) NOT NULL, 
    last_name   VARCHAR2(20) NOT NULL, 
    middle_name VARCHAR2(20), 
    job_title   VARCHAR2(50) NOT NULL, 
    supervisor  NUMBER(10), 
    department  NUMBER(10)
);


    


CREATE TABLE departments(
    id                  NUMBER(10), 
    department_name     VARCHAR2(50) NOT NULL, 
    head_of_department  NUMBER(10)
);

CREATE TABLE tuition_reimbursement_requests (
    id                              NUMBER(10), 
    for_employee                    NUMBER(10) NOT NULL, 
    event_type                      VARCHAR2(3) NOT NULL,
    total_costs                     NUMBER(10,2) NOT NULL, 
    event_description               VARCHAR2(500) NOT NULL, -- EVENT_DESCRIPTION
    event_location                  VARCHAR(500)  NOT NULL,
    start_datetime_of_event         TIMESTAMP NOT NULL,
    hours_of_work_missed            NUMBER(10,2),
    work_related_justification      VARCHAR2(500) NOT NULL,
    grade_format                    NUMBER(1) NOT NULL, -- 0 for out of 100, 1 for pass/fail, 2 for presenation
    score_required                  NUMBER(3) NOT NULL, -- 70 default, 100 for pass if pass/fail, 100 for presentation
    date_completed_and_submitted    TIMESTAMP,
    status_of_request               VARCHAR2(8) NOT NULL,
    waiting_on_message              NUMBER(10),
    has_supervisor_approval         VARCHAR2(1) NOT NULL,
    has_department_head_approval    VARCHAR2(1) NOT NULL,
    has_benco_approval              VARCHAR2(1) NOT NULL
);

CREATE TABLE request_notes (
    id              NUMBER(10),
    request_id      NUMBER(10) NOT NULL,
    note_subject    VARCHAR2(30) NOT NULL,
    note            VARCHAR2(500) NOT NULL,
    added_at        TIMESTAMP NOT NULL,
    added_by        NUMBER(10)
);

CREATE TABLE request_attachments (
    id                  NUMBER(10),
    request_id          NUMBER(10) NOT NULL,
    file_description    VARCHAR2(30) NOT NULL,
    attachment          BLOB NOT NULL,
    file_type           VARCHAR2(10) NOT NULL,
    added_at            TIMESTAMP NOT NULL,
    added_by            NUMBER(10),
    is_approval_email   VARCHAR2(1) NOT NULL
);

CREATE TABLE submitted_grades (
    id                           NUMBER(10), 
    request_id                   NUMBER(10) NOT NULL, 
    employee_reported_score      NUMBER(3) NOT NULL,
    submitted_at                 TIMESTAMP NOT NULL,
    reviewed_by                  NUMBER(10),
    reviewed_at                  TIMESTAMP,
    passed                       VARCHAR2(1)
);

CREATE TABLE grade_documentation_and_presentations (
    id                  NUMBER(10),
    grade_submittion_id NUMBER(10) NOT NULL,
    file_description    VARCHAR2(30) NOT NULL,
    attachment          BLOB NOT NULL, 
    file_type           VARCHAR2(10) NOT NULL,
    added_at            TIMESTAMP NOT NULL
);

CREATE TABLE grade_notes (
    id                  NUMBER(10), 
    grade_submittion_id NUMBER(10) NOT NULL,
    note_subject        VARCHAR2(30) NOT NULL,
    note                VARCHAR2(500) NOT NULL, 
    added_at            TIMESTAMP NOT NULL,
    added_by            NUMBER(10)
);

CREATE TABLE reimbursement_payouts (
    id                      NUMBER(10),
    request_id              NUMBER(10) NOT NULL, 
    reimbursement_amount    NUMBER(10,2) NOT NULL,
    for_year                NUMBER(4) NOT NULL, 
    status                  VARCHAR2(10) NOT NULL,
    date_of_payment         TIMESTAMP,
    notes                   VARCHAR2(500)
);

CREATE TABLE reimbursement_messages (
    id                      NUMBER(10),
    request_id              NUMBER(10) NOT NULL,
    type_of_message         VARCHAR2(5) NOT NULL, 
    receiver_id             NUMBER(10), -- RECEIVER_ID receiver_id
    sender_id               NUMBER(10),
    sender_notes            VARCHAR2(500),
    status                  VARCHAR2(2) NOT NULL,
    response                VARCHAR2(2), 
    time_created            TIMESTAMP NOT NULL,
    time_of_response        TIMESTAMP
);

COMMIT;
-- Table restraints

    --pk constraints
ALTER TABLE employees ADD  CONSTRAINT pk_employees PRIMARY KEY (id);
ALTER TABLE departments ADD  CONSTRAINT pk_departments PRIMARY KEY (id);
ALTER TABLE tuition_reimbursement_requests ADD  CONSTRAINT pk_tuition_reimbursement_requests PRIMARY KEY (id);
ALTER TABLE request_notes ADD  CONSTRAINT pk_request_notes PRIMARY KEY (id);
ALTER TABLE request_attachments ADD  CONSTRAINT pk_request_attachments PRIMARY KEY (id);
ALTER TABLE submitted_grades ADD  CONSTRAINT pk_submitted_grades PRIMARY KEY (id);
ALTER TABLE grade_documentation_and_presentations ADD  CONSTRAINT pk_grade_documentation PRIMARY KEY (id);
ALTER TABLE grade_notes ADD  CONSTRAINT pk_grade_notes PRIMARY KEY (id);
ALTER TABLE reimbursement_payouts ADD  CONSTRAINT pk_reimbursement_payouts PRIMARY KEY (id);
ALTER TABLE reimbursement_messages ADD  CONSTRAINT pk_reimbursement_messages PRIMARY KEY (id);
    
    --employees restraints
ALTER TABLE employees ADD CONSTRAINT email_is_unique UNIQUE (email);
ALTER TABLE employees ADD CONSTRAINT fk_supervisor_to_employees FOREIGN KEY (supervisor) REFERENCES employees(id) ON DELETE SET NULL; -- RE RUN

    -- departments constraints
ALTER TABLE departments ADD CONSTRAINT NAME_is_unique UNIQUE (department_name);
ALTER TABLE departments ADD CONSTRAINT fk_departments_to_employees FOREIGN KEY (head_of_department) REFERENCES employees(id) ON DELETE SET NULL;

    -- tuition_reimbursement_requests
ALTER TABLE tuition_reimbursement_requests ADD CONSTRAINT fk_requests_to_employees FOREIGN KEY (for_employee) REFERENCES employees(id) ON DELETE CASCADE; 
ALTER TABLE tuition_reimbursement_requests ADD CONSTRAINT vaild_event_types CHECK 
    ( event_type in ('UC', 'S', 'C', 'CPC', 'TT', 'O'));
    -- UC, S, C, CPC, TT, O : 'university course', 'seminar', 'certification', 'certification prep class', 'technical training', 'other'
ALTER TABLE tuition_reimbursement_requests ADD CONSTRAINT vaild_grade_formats CHECK 
    ( grade_format in (0, 1, 2)); -- 0 for out of 100, 1 for pass/fail, 2 for presentation
ALTER TABLE tuition_reimbursement_requests ADD CONSTRAINT status_of_request CHECK 
    ( status_of_request in ('NS', 'PROS', 'PSVA', 'PDHA', 'PBCA', 'APG', 'APGC', 'PEFB', 'PSVFB', 'PDHFB', 'APPROVED', 'DENIED'));
ALTER TABLE tuition_reimbursement_requests ADD CONSTRAINT fk_requests_to_messages FOREIGN KEY (waiting_on_message) REFERENCES reimbursement_messages(id) ON DELETE SET NULL;
ALTER TABLE tuition_reimbursement_requests ADD CONSTRAINT sv_approval_T_or_F CHECK ( has_supervisor_approval in ('T', 'F'));
ALTER TABLE tuition_reimbursement_requests ADD CONSTRAINT dh_approval_T_or_F CHECK ( has_department_head_approval in ('T', 'F'));
ALTER TABLE tuition_reimbursement_requests ADD CONSTRAINT benco_approval_T_or_F CHECK ( has_benco_approval in ('T', 'F'));

    --request_notes constraints
ALTER TABLE request_notes ADD CONSTRAINT fk_notes_to_requests FOREIGN KEY (request_id) REFERENCES tuition_reimbursement_requests(id) ON DELETE CASCADE;
ALTER TABLE request_notes ADD CONSTRAINT fk_notes_to_employees FOREIGN KEY (added_by) REFERENCES employees(id) ON DELETE SET NULL; 

    -- request_attachments
ALTER TABLE request_attachments ADD CONSTRAINT fk_attachments_to_requests FOREIGN KEY (request_id) REFERENCES tuition_reimbursement_requests(id) ON DELETE CASCADE; 
ALTER TABLE request_attachments ADD CONSTRAINT fk_attachments_to_employees FOREIGN KEY (added_by) REFERENCES employees(id) ON DELETE SET NULL; 
ALTER TABLE request_attachments ADD CONSTRAINT is_email_approval_T_or_F CHECK ( is_approval_email in ('T', 'F'));

    --submitted_grades constraints
ALTER TABLE submitted_grades ADD CONSTRAINT fk_grades_to_requests FOREIGN KEY (request_id) REFERENCES tuition_reimbursement_requests(id) ON DELETE CASCADE; 
ALTER TABLE submitted_grades ADD CONSTRAINT fk_employees_to_requests FOREIGN KEY (reviewed_by) REFERENCES employees(id) ON DELETE SET NULL;
ALTER TABLE submitted_grades ADD CONSTRAINT passed_T_or_F CHECK ( passed in ('T', 'F'));

    --grade_documentation_and_presentations constraints
ALTER TABLE grade_documentation_and_presentations ADD CONSTRAINT fk_grade_docs_to_grades FOREIGN KEY (grade_submittion_id) REFERENCES submitted_grades(id) ON DELETE CASCADE; 

    --grade_notes constraints
ALTER TABLE grade_notes ADD CONSTRAINT fk_grade_notes_to_grades FOREIGN KEY (grade_submittion_id) REFERENCES submitted_grades(id) ON DELETE CASCADE;
ALTER TABLE grade_notes ADD CONSTRAINT fk_grade_notes_to_employees FOREIGN KEY (added_by) REFERENCES employees(id) ON DELETE SET NULL; 

    --reimbursement_payouts constraints
ALTER TABLE reimbursement_payouts ADD CONSTRAINT fk_payouts_to_requests FOREIGN KEY (request_id) REFERENCES tuition_reimbursement_requests(id) ON DELETE CASCADE; 
ALTER TABLE reimbursement_payouts ADD CONSTRAINT status CHECK ( status in ('PEA', 'PG', 'AWARDED'));
        -- pending employee approval, pending grading, awarded.... a denied payout will be deleted

    --reimbursement_messages constraints
ALTER TABLE reimbursement_messages ADD CONSTRAINT fk_messages_to_requests FOREIGN KEY (request_id) REFERENCES tuition_reimbursement_requests(id) ON DELETE CASCADE;
ALTER TABLE reimbursement_messages ADD CONSTRAINT valid_types_of_message CHECK ( type_of_message in ('FR', 'AR', 'PAN', 'GSR', 'GCR', 'MGFBR', 'RDN', 'RAN', 'FRR'));
        --feedback request, approval request, pre approval notice, grade submition request, grade confirmation request, more grade feedback request ... request denied notice, rquest awarded notice
        -- feedback request reply
ALTER TABLE reimbursement_messages ADD CONSTRAINT fk_messages_to_reciever FOREIGN KEY (receiver_id) REFERENCES employees(id) ON DELETE SET NULL;
ALTER TABLE reimbursement_messages ADD CONSTRAINT fk_messages_to_sender FOREIGN KEY (sender_id) REFERENCES employees(id) ON DELETE SET NULL; -- if null => automated
ALTER TABLE reimbursement_messages ADD CONSTRAINT valid_statuses CHECK ( status in ('UR', 'UD', 'R'));
        --unread, undecided, responded
ALTER TABLE reimbursement_messages ADD CONSTRAINT valid_responses CHECK ( response in ('A', 'D', 'RF', 'SF', 'C', 'GS'));
        -- APPROVED, denied, requesting feedback, sent feedback confirm, grade submitted 
COMMIT;

-- making sequences to track pk for tables
CREATE SEQUENCE employees_pk_sequence 
    START WITH 1
    INCREMENT BY 1; 
CREATE SEQUENCE departments_pk_sequence 
    START WITH 1
    INCREMENT BY 1;
CREATE SEQUENCE tuition_reimbursement_requests_pk_sequence 
    START WITH 1
    INCREMENT BY 1;
CREATE SEQUENCE request_notes_pk_sequence 
    START WITH 1
    INCREMENT BY 1;
CREATE SEQUENCE request_attachments_pk_sequence 
    START WITH 1
    INCREMENT BY 1;
CREATE SEQUENCE submitted_grades_pk_sequence 
    START WITH 1
    INCREMENT BY 1;
CREATE SEQUENCE grade_documentation_and_presentations_pk_sequence 
    START WITH 1
    INCREMENT BY 1;
CREATE SEQUENCE grade_notes_pk_sequence 
    START WITH 1
    INCREMENT BY 1;
CREATE SEQUENCE reimbursement_payouts_pk_sequence 
    START WITH 1
    INCREMENT BY 1;
CREATE SEQUENCE reimbursement_messages_pk_sequence 
    START WITH 1
    INCREMENT BY 1;
-- a value to deal with issues 
INSERT INTO employees (id, email, password, first_name, last_name, middle_name, job_title, supervisor, department)
    VALUES (-1, 'system@adim', 'systemadmin', 'System', 'Generated', 'a', 'none', null, 1);

-- triggers to deal with PKs
CREATE TRIGGER employees_pk_insert
BEFORE INSERT ON employees
FOR EACH ROW
BEGIN
  SELECT employees_pk_sequence.nextval
  INTO :new.id
  FROM dual;
END;
/
CREATE TRIGGER departments_pk_insert
BEFORE INSERT ON departments
FOR EACH ROW
BEGIN
  SELECT departments_pk_sequence.nextval
  INTO :new.id
  FROM dual;
END;

/
CREATE TRIGGER tuition_reimbursement_requests_pk_insert
BEFORE INSERT ON tuition_reimbursement_requests
FOR EACH ROW
BEGIN
  SELECT tuition_reimbursement_requests_pk_sequence.nextval
  INTO :new.id
  FROM dual;
END;
/
CREATE TRIGGER request_notes_pk_insert
BEFORE INSERT ON request_notes
FOR EACH ROW
BEGIN
  SELECT request_notes_pk_sequence.nextval
  INTO :new.id
  FROM dual;
END;
/
CREATE TRIGGER request_attachments_pk_insert
BEFORE INSERT ON request_attachments
FOR EACH ROW
BEGIN
  SELECT request_attachments_pk_sequence.nextval
  INTO :new.id
  FROM dual;
END;
/
CREATE TRIGGER submitted_grades_pk_insert
BEFORE INSERT ON submitted_grades
FOR EACH ROW
BEGIN
  SELECT submitted_grades_pk_sequence.nextval
  INTO :new.id
  FROM dual;
END;
/
CREATE TRIGGER grade_documentation_and_presentations_pk_insert
BEFORE INSERT ON grade_documentation_and_presentations
FOR EACH ROW
BEGIN
  SELECT grade_documentation_and_presentations_pk_sequence.nextval
  INTO :new.id
  FROM dual;
END;
/
CREATE TRIGGER grade_notes_pk_insert
BEFORE INSERT ON grade_notes
FOR EACH ROW
BEGIN
  SELECT grade_notes_pk_sequence.nextval
  INTO :new.id
  FROM dual;
END;
/
CREATE TRIGGER reimbursement_payouts_pk_insert
BEFORE INSERT ON reimbursement_payouts
FOR EACH ROW
BEGIN
  SELECT reimbursement_payouts_pk_sequence.nextval
  INTO :new.id
  FROM dual;
END;
/
CREATE TRIGGER reimbursement_messages_pk_insert
BEFORE INSERT ON reimbursement_messages
FOR EACH ROW
BEGIN
  SELECT reimbursement_messages_pk_sequence.nextval
  INTO :new.id
  FROM dual;
END;
/
COMMIT;


INSERT INTO departments (department_name, head_of_department)
    VALUES ('Coporate Admin Departmnet', null);
INSERT INTO departments (department_name, head_of_department)
    VALUES ('Human Resources Department', null);
INSERT INTO departments (department_name, head_of_department)
    VALUES ('Sales Department', null);
INSERT INTO departments (department_name, head_of_department)
    VALUES ('Information Technology Department', null);
INSERT INTO departments (department_name, head_of_department)
    VALUES ('Quality Control Department', null);
commit;


-- top managment employees

INSERT INTO employees (email, password, first_name, last_name, middle_name, job_title, supervisor, department)
    VALUES ('ceo@CorpAdmin', 'ceopassword', 'cameron', 'walker', 'G', 'CEO', null, 1);
UPDATE departments SET head_of_department = 1 WHERE department_name = 'Coporate Admin Departmnet';

INSERT INTO employees (email, password, first_name, last_name, middle_name, job_title, supervisor, department)
    VALUES ('BM@CorpAdmin', 'bmpassword', 'allen', 'walker', 'J', 'Board Member', 1, 1);


INSERT INTO employees (email, password, first_name, last_name, middle_name, job_title, supervisor, department)
    VALUES ('DM@CorpAdmin', 'dmpassword', 'john', 'smith', 'J', 'Departments Manager', 2, 1);
commit;

-- HR employees
INSERT INTO employees (email, password, first_name, last_name, middle_name, job_title, supervisor, department)
    VALUES ('headOfHR@HR', 'hhrpassword', 'Sally', 'Thibodaux', 'M', 'Head Of Human Resources', 3, 2); -- 4
UPDATE departments SET head_of_department = 4 WHERE department_name = 'Human Resources Department';

INSERT INTO employees (email, password, first_name, last_name, middle_name, job_title, supervisor, department)
    VALUES ('HHRC@HR', 'hhrcpassword', 'Valerie', 'Nice', 'Q', 'Head Human Resource Coordinator', 4, 2); -- 5

INSERT INTO employees (email, password, first_name, last_name, middle_name, job_title, supervisor, department)
    VALUES ('HHAT@HR', 'hhatpassword', 'Ryan', 'Brown', 'A', 'Head Talent Acquisition Specialist', 4, 2); --6
    
INSERT INTO employees (email, password, first_name, last_name, middle_name, job_title, supervisor, department)
    VALUES ('BenCo@HR', 'bencopassword', 'Dylan', 'Adams', 'R', 'Benefits Coordinator', 5, 2); --7
    
INSERT INTO employees (email, password, first_name, last_name, middle_name, job_title, supervisor, department)
    VALUES ('BenCoA@HR', 'bencoapassword', 'William', 'Mylie', 'T', 'Assistant Benefits Coordinator', 7, 2); --8


-- Sales
INSERT INTO employees (email, password, first_name, last_name, middle_name, job_title, supervisor, department)
    VALUES ('adamkennedy@sales', 'headofsalespassword', 'Adam', 'Kennedy', 'J', 'Head Of Sales Department', 3, 3); -- 9
UPDATE departments SET head_of_department = 9 WHERE department_name = 'Sales Department';

INSERT INTO employees (email, password, first_name, last_name, middle_name, job_title, supervisor, department)
    VALUES ('tammymarsa@sales', 'headofsalespassword', 'Tammy', 'Marsa', 'L', 'Assistant Head of Sales', 9, 3); -- 10

INSERT INTO employees (email, password, first_name, last_name, middle_name, job_title, supervisor, department)
    VALUES ('timlittle@sales', 'timlittlepassword', 'Tim', 'Little', 'J', 'Sales Associate', 10, 3); -- 11

commit;

select * from departments;
select * from employees;
select * from reimbursement_messages;
select * from tuition_reimbursement_requests;

 -- select * from tuition_reimbursement_requests;








