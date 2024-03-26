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
);

alter table departments
    owner to markhaskins;

alter sequence hose_hose_id_seq owned by departments.department_id;

create table employees
(
    department_id integer,
    manager_id    integer,
    name          text,
    rate_id       integer,
    employee_id   integer default nextval('employee_employee_id_seq'::regclass) not null
);

alter table employees
    owner to markhaskins;

alter sequence employee_employee_id_seq owned by employees.employee_id;

create table managers
(
    name          text,
    department_id integer,
    manager_id    integer default nextval('sem_id_seq'::regclass) not null
);

alter table managers
    owner to markhaskins;

alter sequence sem_id_seq owned by managers.manager_id;

create table projects
(
    name       text,
    code       text,
    project_id integer default nextval('product_product_id_seq'::regclass) not null
);

alter table projects
    owner to markhaskins;

alter sequence product_product_id_seq owned by projects.project_id;

create table project_employee
(
    employee_id integer,
    project_id  integer,
    allocation  integer,
    start       date,
    "end"       date,
    cost        real
);

alter table project_employee
    owner to markhaskins;

create table rates
(
    rate_id    integer default nextval('rate_rate_id_seq'::regclass) not null,
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

create table roles
(
    role_id integer default nextval('role_role_id_seq'::regclass) not null,
    name    text
);

alter table roles
    owner to markhaskins;

alter sequence role_role_id_seq owned by roles.role_id;