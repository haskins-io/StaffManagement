create sequence employee_employee_id_seq
    as integer;

alter sequence employee_employee_id_seq owner to markhaskins;

create sequence hose_hose_id_seq
    as integer;

alter sequence hose_hose_id_seq owner to markhaskins;

create sequence product_product_id_seq
    as integer;

alter sequence product_product_id_seq owner to markhaskins;

create sequence rate_rate_id_seq
    as integer;

alter sequence rate_rate_id_seq owner to markhaskins;

create sequence role_role_id_seq
    as integer;

alter sequence role_role_id_seq owner to markhaskins;

create sequence sem_id_seq
    as integer;

alter sequence sem_id_seq owner to markhaskins;

create table departments
(
    name          text,
    department_id integer default nextval('hose_hose_id_seq'::regclass) not null
        constraint departments_pk
            primary key
);

alter table departments
    owner to markhaskins;

alter sequence hose_hose_id_seq owned by departments.department_id;

create table managers
(
    name          text,
    department_id integer
        constraint managers_departments_department_id_fk
            references departments,
    manager_id    integer default nextval('sem_id_seq'::regclass) not null
        constraint managers_pk
            primary key
);

alter table managers
    owner to markhaskins;

alter sequence sem_id_seq owned by managers.manager_id;

create table projects
(
    name       text,
    code       text,
    project_id integer default nextval('product_product_id_seq'::regclass) not null
        constraint projects_pk
            primary key,
    budget     integer,
    status     integer,
    priority   integer,
    due_date   date,
    dscription text,
    progress   integer
);

alter table projects
    owner to markhaskins;

alter sequence product_product_id_seq owned by projects.project_id;

create table rates
(
    rate_id    integer default nextval('rate_rate_id_seq'::regclass) not null
        constraint rates_pk
            primary key,
    name       text,
    hour_rate  real,
    day_rate   real,
    week_rate  real,
    month_rate real,
    year_rate  real
);

alter table rates
    owner to markhaskins;

alter sequence rate_rate_id_seq owned by rates.rate_id;

create table employees
(
    department_id integer,
    manager_id    integer
        constraint employees_managers_manager_id_fk
            references managers,
    name          text,
    rate_id       integer
        constraint employees_rates_rate_id_fk
            references rates,
    employee_id   integer default nextval('employee_employee_id_seq'::regclass) not null
        constraint employees_pk
            primary key
);

alter table employees
    owner to markhaskins;

alter sequence employee_employee_id_seq owned by employees.employee_id;

create table project_employee
(
    employee_id integer
        constraint project_employee_employees__fk
            references employees,
    project_id  integer
        constraint project_employee_projects__fk
            references projects,
    allocation  integer,
    start       date,
    "end"       date,
    cost        real,
    pe_id       serial
        constraint project_employee_pk
            primary key
);

alter table project_employee
    owner to markhaskins;

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

create table employees_holidays
(
    eh_id       serial
        constraint employees_holidays_pk
            primary key,
    employee_id integer
);

alter table employees_holidays
    owner to postgres;

create table project_notes
(
    pn_id      serial
        constraint project_notes_pk
            primary key,
    project_id integer
);

alter table project_notes
    owner to postgres;

