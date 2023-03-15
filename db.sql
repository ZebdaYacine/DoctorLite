create database DoctorLite;
use DoctorLite;


create table service(
id BIGINT primary key auto_increment,
name varchar(20)
);

create table role(
id BIGINT primary key auto_increment,
name varchar(20)  /* ==>{admin,manager,user}*/
);

create table type(
id BIGINT primary key auto_increment,
name varchar(20)  /* ==>{medecin,agent administartor,psucholog}*/
);

create table user(
id BIGINT primary key auto_increment,
userName varchar(10),
password varchar(20),
firstName varchar(20),
lastName varchar(20),
phone varchar(10),
type varchar(50),
idService BIGINT not null, 
idRole BIGINT not null, 
idType BIGINT not null, 
FOREIGN KEY (idType) REFERENCES type(id),
FOREIGN KEY (idRole) REFERENCES role(id),
FOREIGN KEY (idService) REFERENCES service(id)
);

create table patient(
id BIGINT primary key auto_increment,
firstName varchar(20),
lastName varchar(20),
phone varchar(10),
address varchar(50),
civilStatus varchar(50),
worke varchar(50),
scientificLevel varchar(50),
socioEconomicLevel varchar(50),
gender varchar(10),
age int,
birthday date,
height int ,
wieght float
);

create table follow(
id BIGINT primary key auto_increment,
dateEnter date,
dateGo date,
idPatient BIGINT,
idService BIGINT,
idDoctor BIGINT,
idPsychologist BIGINT,
sickness varchar(50),
status varchar(50),
FOREIGN KEY (idPatient) REFERENCES patient(id),
FOREIGN KEY (idService) REFERENCES service(id),
FOREIGN KEY (idPsychologist) REFERENCES user(id),
FOREIGN KEY (idDoctor) REFERENCES user(id)
);

create table diagnostic(
id BIGINT primary key auto_increment,
dateDiagnostic date,
sickness varchar(50),
idPatient BIGINT,
idFollow BIGINT,
mediclaDiagnostic varchar(500),
psychologyDiagnostic varchar(500),
interviewDynamics varchar(500),
FOREIGN KEY (idPatient) REFERENCES patient(id),
FOREIGN KEY (idFollow) REFERENCES follow(id)
);





