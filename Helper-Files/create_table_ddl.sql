Create Table Users (
	id number Primary Key,
	email varchar2(200) Unique Not Null,
	firstname varchar2(200) Not Null,
	lastname varchar2(200) Not Null,
	phonenumber varchar2(200),
	password varchar2(500) Not Null,
	pokemonid varchar2(20) Not Null
);

Create Sequence user_seq INCREMENT BY 1 START WITH 1;