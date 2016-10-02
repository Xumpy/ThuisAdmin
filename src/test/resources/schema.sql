create sequence if not exists seq_ta_bedragen start with 100;
create sequence if not exists seq_ta_bedrag_documenten start with 100;
create sequence if not exists seq_ta_type_groep start with 100;
create sequence if not exists seq_ta_personen start with 100;
create sequence if not exists seq_ta_rekeningen start with 100;

create table if not exists ta_personen(
	pkId int primary key,
	naam varchar,
	voornaam varchar,
        user_name varchar,
        md5_password varchar
);

create table if not exists ta_rekeningen(
	pkId int primary key,
        fk_personen_id int,
	waarde decimal,
	naam varchar,
	laatst_bijgewerkt date);

alter table ta_rekeningen add foreign key(fk_personen_id) references public.ta_personen(pkId);

create table if not exists ta_type_groep(
	pkId int primary key,
	fk_hoofd_type_groep_id int,
	fk_personen_id int,
	naam varchar,
	omschrijving varchar,
	negatief int,
	code_id varchar,
        public_groep int);

alter table ta_type_groep add foreign key(fk_hoofd_type_groep_id) references public.ta_type_groep(pkId);
alter table ta_type_groep add foreign key(fk_personen_id) references public.ta_personen(pkId);

create table if not exists ta_bedragen(
	pkId int primary key,
	fk_type_groep_id int,
	fk_persoon_id int,
	fk_rekening_id int,
	bedrag decimal,
	datum date,
	omschrijving varchar);

alter table ta_bedragen add foreign key(fk_type_groep_id) references public.ta_type_groep(pkId);
alter table ta_bedragen add foreign key(fk_persoon_id) references public.ta_personen(pkId);
alter table ta_bedragen add foreign key(fk_rekening_id) references public.ta_rekeningen(pkId);

create table if not exists ta_bedrag_documenten(
    pkId int primary key,
    fk_bedrag_id int,
	omschrijving varchar,
    document blob,
	document_naam varchar,
	document_mime varchar);

alter table ta_bedrag_documenten add foreign key(fk_bedrag_id) references public.ta_bedragen(pkId);

create sequence if not exists seq_ta_job_groups start with 100;
create sequence if not exists seq_ta_jobs start with 100;
create sequence if not exists seq_ta_company start with 100;
create sequence if not exists seq_ta_jobs_group_prices start with 100;

create table if not exists ta_company(
    pkId int primary key,
    name varchar,
    daily_payed_hours decimal);

create table if not exists ta_jobs_group_prices(
    pkId int primary key,
    fk_job_group_id int not null,
    start_date date not null,
    end_date date not null,
    price_per_hour decimal);


create table if not exists ta_job_groups(
	pkId int primary key,
	job_name varchar,
	description varchar,
        fk_company_id int
);

create table if not exists ta_jobs(
	pkId int primary key,
	fk_job_group_id varchar,
	job_date date,
        worked_hours decimal,
        remarks varchar,
        percentage int
);

alter table ta_jobs add foreign key(fk_job_group_id) references public.ta_job_groups(pkId);