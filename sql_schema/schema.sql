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

create table employees
(
    department_id   integer,
    manager_id      integer,
    name            text,
    rate_id         integer,
    employee_id     integer default 0 not null
        constraint employees_pk
            primary key,
    work_allocation integer default 0 not null
);

alter table employees
    owner to markhaskins;

create table projects
(
    name        text,
    code        text,
    project_id  integer default nextval('product_product_id_seq'::regclass) not null
        constraint projects_pk
            primary key,
    budget      integer default 0                                           not null,
    status      integer default 0                                           not null,
    priority    integer default 0                                           not null,
    due_date    date    default now()                                       not null,
    description text                                                        not null,
    progress    integer default 0                                           not null
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
    start       date,
    "end"       date,
    cost        real    default 0                                               not null,
    pr_id       integer default nextval('project_employee_pe_id_seq'::regclass) not null
        constraint projectresources_pk
            primary key
);

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
        constraint employees_holidays_pk
            primary key,
    employee_id integer
        constraint employees_holidays_employees__fk
            references employees
);

alter table employeeholidays
    owner to postgres;

alter sequence employees_holidays_eh_id_seq owned by employeeholidays.eh_id;

create table projectnotes
(
    pn_id      integer default nextval('project_notes_pn_id_seq'::regclass) not null
        constraint project_notes_pk
            primary key,
    project_id integer
);

alter table projectnotes
    owner to postgres;

alter sequence project_notes_pn_id_seq owned by projectnotes.pn_id;

create table departments
(
    department_id integer default nextval('department_department_id_seq'::regclass) not null
        constraint department_pk
            primary key,
    name          text                                                              not null,
    head          integer
);

alter table departments
    owner to postgres;

alter sequence department_department_id_seq owned by departments.department_id;

