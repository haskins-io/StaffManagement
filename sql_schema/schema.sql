create sequence product_product_id_seq
    as integer;

alter sequence product_product_id_seq owner to markhaskins;

create sequence rate_rate_id_seq
    as integer;

alter sequence rate_rate_id_seq owner to markhaskins;

create sequence role_role_id_seq
    as integer;

alter sequence role_role_id_seq owner to markhaskins;

create sequence project_employee_pe_id_seq
    as integer;

alter sequence project_employee_pe_id_seq owner to markhaskins;

create sequence employees_holidays_eh_id_seq
    as integer;

alter sequence employees_holidays_eh_id_seq owner to postgres;

create sequence project_notes_pn_id_seq
    as integer;

alter sequence project_notes_pn_id_seq owner to postgres;

create sequence department_department_id_seq
    as integer;

alter sequence department_department_id_seq owner to postgres;

create sequence projecttasks_pt_id_seq
    as integer;

alter sequence projecttasks_pt_id_seq owner to postgres;

create sequence projecttasks_pt_id_seq1
    as integer;

alter sequence projecttasks_pt_id_seq1 owner to postgres;

create table employees
(
    department_id   integer,
    manager_id      integer,
    name            text,
    rate_id         integer,
    employee_id     integer default 0     not null
        constraint employees_pk
            primary key,
    work_allocation integer default 0     not null,
    is_manager      boolean default false not null,
    hired           bigint  default 0     not null,
    location        text                  not null,
    role            text                  not null
);

comment on column employees.role is 'role';

alter table employees
    owner to markhaskins;

create table projects
(
    name          text,
    code          text,
    project_id    integer default nextval('product_product_id_seq'::regclass) not null
        constraint projects_pk
            primary key,
    budget        integer default 0                                           not null,
    status        integer default 0                                           not null,
    priority      integer default 0                                           not null,
    description   text                                                        not null,
    progress      integer default 0                                           not null,
    due_date      bigint  default 0                                           not null,
    cost          integer default 0                                           not null,
    documentation text    default ''::text                                    not null
);

alter table projects
    owner to markhaskins;

alter sequence product_product_id_seq owned by projects.project_id;

create table projectresources
(
    employee_id integer
        constraint projectresources_employees__fk
            references employees,
    project_id  integer
        constraint projectresources_projects__fk
            references projects,
    allocation  integer,
    cost        real    default 0                                               not null,
    pr_id       integer default nextval('project_employee_pe_id_seq'::regclass) not null
        constraint projectresources_pk
            primary key,
    start       bigint  default 0                                               not null,
    "end"       bigint  default 0                                               not null
);

comment on column projectresources.start is 'start';

alter table projectresources
    owner to markhaskins;

alter sequence project_employee_pe_id_seq owned by projectresources.pr_id;

create table rates
(
    rate_id    integer default nextval('rate_rate_id_seq'::regclass) not null
        constraint rates_pk
            primary key,
    name       text,
    hour_rate  real    default 0                                     not null,
    day_rate   real    default 0                                     not null,
    week_rate  real    default 0                                     not null,
    month_rate real    default 0                                     not null,
    year_rate  real    default 0                                     not null
);

alter table rates
    owner to markhaskins;

alter sequence rate_rate_id_seq owned by rates.rate_id;

create table roles
(
    role_id integer default nextval('role_role_id_seq'::regclass) not null
        constraint roles_pk
            primary key,
    name    text
);

alter table roles
    owner to markhaskins;

alter sequence role_role_id_seq owned by roles.role_id;

create table employeeholidays
(
    eh_id       integer default nextval('employees_holidays_eh_id_seq'::regclass) not null
        constraint employeesholidays_pk
            primary key,
    employee_id integer
        constraint employeesholidays_employees__fk
            references employees,
    start       bigint  default 0                                                 not null,
    "end"       bigint  default 0                                                 not null
);

alter table employeeholidays
    owner to postgres;

alter sequence employees_holidays_eh_id_seq owned by employeeholidays.eh_id;

create table notes
(
    id    integer default nextval('project_notes_pn_id_seq'::regclass) not null
        constraint notes_pk
            primary key,
    title text,
    note  text,
    date  bigint  default 0                                            not null
);

alter table notes
    owner to postgres;

alter sequence project_notes_pn_id_seq owned by notes.id;

create table departments
(
    department_id integer default nextval('department_department_id_seq'::regclass) not null
        constraint department_pk
            primary key,
    name          text                                                              not null,
    head          integer
        constraint departments_employees__fk
            references employees
);

alter table departments
    owner to postgres;

alter sequence department_department_id_seq owned by departments.department_id;

create table employeenotes
(
    en_id       serial
        constraint employeenotes_pk
            primary key,
    employee_id integer           not null
        constraint employeenotes_employees__fk
            references employees,
    note_id     integer default 0 not null
);

alter table employeenotes
    owner to postgres;

create table tasks
(
    id       integer default nextval('projecttasks_pt_id_seq'::regclass) not null
        constraint tasks_pk
            primary key,
    name     text    default ''::text                                    not null,
    assigned integer default 0                                           not null,
    progress integer default 0                                           not null,
    status   integer default 0                                           not null,
    due      bigint  default 0                                           not null,
    priority integer default 0                                           not null
);

alter table tasks
    owner to postgres;

alter sequence projecttasks_pt_id_seq owned by tasks.id;

create table projecttasks
(
    pt_id      integer default nextval('projecttasks_pt_id_seq1'::regclass) not null
        constraint projecttasks_pk
            primary key,
    project_id integer default 0
        constraint projecttasks_projects__fk
            references projects,
    task_id    integer default 0                                            not null
);

alter table projecttasks
    owner to postgres;

alter sequence projecttasks_pt_id_seq1 owned by projecttasks.pt_id;

create table employeetasks
(
    et_id       serial
        constraint employeetasks_pk
            primary key,
    employee_id integer default 0 not null
        constraint employeetasks_employees__fk
            references employees,
    task_id     integer default 0 not null
);

alter table employeetasks
    owner to postgres;

create table projectnotes
(
    pn_id      serial
        constraint projectnotes_pk
            primary key,
    project_id integer default 0 not null,
    note_id    integer default 0 not null
        constraint projectnotes_notes__fk
            references notes
);

alter table projectnotes
    owner to postgres;

