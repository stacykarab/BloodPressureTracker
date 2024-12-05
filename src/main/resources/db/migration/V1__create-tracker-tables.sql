create type bp_disorder as enum ('HYPERTENSION', 'HYPOTENSION', 'NONE');
create cast (varchar as bp_disorder) with inout as implicit;

create type gender as enum ('M', 'F');
create cast (varchar as gender) with inout as implicit;

create table users
(
    id            int          not null primary key generated always as identity,
    name          varchar(255) not null,
    gender        gender       not null,
    birth_date    date,
    height        int,
    weight        int,
    smoking       boolean,
    avg_systolic  int,
    avg_diastolic int,
    constraint bp_valid check (avg_systolic is null or avg_systolic between 40 and 200
        and avg_diastolic is null or avg_diastolic between 20 and 120)
);

create table chronic_illnesses
(
    id         int          not null primary key generated always as identity,
    name       varchar(100) not null,
    bp_related bp_disorder  not null
);

create table user_chronic_illnesses
(
    user_id            int not null,
    chronic_illness_id int not null,
    primary key (user_id, chronic_illness_id),
    foreign key (user_id) references users (id),
    foreign key (chronic_illness_id) references chronic_illnesses (id)
);

create table user_daily_bp_statistics
(
    user_id                 int  not null,
    date                    date not null,
    avg_systolic_night      int,
    avg_diastolic_night     int,
    avg_systolic_morning    int,
    avg_diastolic_morning   int,
    avg_systolic_afternoon  int,
    avg_diastolic_afternoon int,
    avg_systolic_evening    int,
    avg_diastolic_evening   int,
    constraint bp_valid check (avg_systolic_night is null or avg_systolic_night between 40 and 200
                                    and
                               avg_diastolic_night is null or avg_diastolic_night between 20 and 120
                                   and
                               avg_systolic_morning is null or avg_systolic_morning between 40 and 200
                                   and
                               avg_diastolic_morning is null or avg_diastolic_morning between 20 and 120
                                   and
                               avg_systolic_afternoon is null or avg_systolic_afternoon between 40 and 200
                                   and
                               avg_diastolic_afternoon is null or avg_diastolic_afternoon between 20 and 120
                                   and
                               avg_systolic_evening is null or avg_systolic_evening between 40 and 200
                                   and
                               avg_diastolic_evening is null or avg_diastolic_evening between 20 and 120),
    primary key (user_id, date),
    foreign key (user_id) references users (id)
);



