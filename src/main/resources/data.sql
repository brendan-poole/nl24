--MAKE SURE YOU USE THE CORRECT SEQUENCES!!!!!!!!!!!!!!!!!

insert into nl24.credit_service_provider (id,version,name,auto_pick, user_group) values (nextval('contact_seq'), 0, 'Drive Assist',true, 'DRIVE_ASSIST');
insert into nl24.credit_service_provider (id,version,name,auto_pick, user_group) values (nextval('contact_seq'), 0, 'Easidrive',true, 'ROLE_EASIDRIVE');
insert into nl24.credit_service_provider (id,version,name) values (nextval('contact_seq'), 0, 'S & G Response');

insert into nl24.broker (id,version,name, code, default_credit_service_provider) values (nextval('contact_seq'), 0, 'Test broker A', 'TBA', (select id from nl24.credit_service_provider where name='Drive Assist'));
insert into nl24.broker (id,version,name, code,default_credit_service_provider) values (nextval('contact_seq'), 0, 'Test broker B', 'TBB', (select id from nl24.credit_service_provider where name='Easidrive'));

insert into nl24.insurer (id,version,name) values (nextval('contact_seq'), 0, 'Elephant');

insert into nl24.police (id,version,name) values (nextval('contact_seq'), 0, 'Test Police');

insert into nl24.hire_provider (id,version,name) values (nextval('contact_seq'), 0, 'Test hire provider');
