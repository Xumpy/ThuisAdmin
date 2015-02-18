create sequence if not exists seq_ta_bedragen start with 1;
create sequence if not exists seq_ta_bedrag_documenten start with 1;
create sequence if not exists seq_ta_type_groep start with 1;
create sequence if not exists seq_ta_personen start with 2;
create sequence if not exists seq_ta_rekeningen start with 2;

create table if not exists ta_personen(
	pk_id int primary key,
	naam varchar,
	voornaam varchar);

create table if not exists ta_rekeningen(
	pk_id int primary key,
	waarde decimal,
	naam varchar,
	laatst_bijgewerkt date);

create table if not exists ta_type_groep(
	pk_id int primary key,
	fk_hoofd_type_groep_id int,
	fk_personen_id int,
	naam varchar,
	omschrijving varchar,
	negatief int,
	code_id varchar);
	
alter table ta_type_groep add foreign key(fk_hoofd_type_groep_id) references public.ta_type_groep(pk_id);
alter table ta_type_groep add foreign key(fk_personen_id) references public.ta_personen(pk_id);

create table if not exists ta_bedragen(
	pk_id int primary key,
	fk_type_groep_id int,
	fk_persoon_id int,
	fk_rekening_id int,
	bedrag decimal,
	datum date,
	omschrijving varchar);

alter table ta_bedragen add foreign key(fk_type_groep_id) references public.ta_type_groep(pk_id);
alter table ta_bedragen add foreign key(fk_persoon_id) references public.ta_personen(pk_id);
alter table ta_bedragen add foreign key(fk_rekening_id) references public.ta_rekeningen(pk_id);

create table if not exists ta_bedrag_documenten(
    pk_id int primary key,
    fk_bedrag_id int,
	omschrijving varchar,
    document blob,
	document_naam varchar,
	document_mime varchar);
	
alter table ta_bedrag_documenten add foreign key(fk_bedrag_id) references public.ta_bedragen(pk_id);

insert into ta_personen select * from (
select 1, 'Test Persoon 1', null
) x where not exists(select * from ta_personen);

insert into ta_rekeningen select * from (
select 1, 2000, 'Test Rekening 1', sysdate
) x where not exists(select * from ta_rekeningen);

insert into ta_type_groep select * from (
select 1 as pk_id, null as fk_hoofd_type_groep_id, 1 as fk_personen_id, 'Hoofdgroep' as naam, null as omschrijving, 0 as negatief, null as code_id union
select 2 as pk_id, 1 as fk_hoofd_type_groep_id, 1 as fk_personen_id, 'Test' as naam, null as omschrijving, 1 as negatief, null as code_id
) x where not exists(select * from ta_type_groep);