CREATE SCHEMA login_app_db;

set search_path to login_app_db;


create table permission_group_label(
		id  serial not null,
        name varchar(50) not null,
        
        primary key (id),
	    unique(name)
);

CREATE UNIQUE INDEX permission_group_label_INSENSITIVE_CONSTRAINT on 
permission_group_label (lower(name));




create table users_auth_details (
       id  serial not null,
       username varchar(255) not null,
       name varchar(255) not null,
       surname varchar(255) not null,
       hashed_password varchar(255),
       ISACCOUNTNONEXPIRED boolean not null,
       ISACCOUNTNONLOCKED boolean not null,
       ISCREDENTIALSNONEXPIRED boolean not null,
       ISENABLED boolean not null,
       last_password_change_date timestamp not null,
       permission_group_name varchar(50) not null,
       
       primary key (id),
       unique(username),
       foreign key (permission_group_name) REFERENCES permission_group_label(name) ON UPDATE CASCADE
);


CREATE UNIQUE INDEX USERNAME_INSENSITIVE_CONSTRAINT on 
users_auth_details (lower(username));

  
ALTER TABLE users_auth_details
  ADD CREATED_BY VARCHAR(512),
  ADD CREATED_DATE TIMESTAMP default ((now() at time zone 'utc')),
  ADD LAST_MODIFIED_BY VARCHAR(512),
  ADD LAST_MODIFIED_DATE TIMESTAMP;
  
  

create table permissions (
    id  serial not null,
    authority varchar(50) not null,

    primary key (id),
	unique(authority)
);

CREATE UNIQUE INDEX PERMISSION_AUTHORITY_INSENSITIVE_CONSTRAINT on 
 PERMISSIONS (lower(AUTHORITY));



create table permission_group (
        id  serial not null,
        name varchar(50) not null,
        fk_authority varchar(50) not null,

        primary key (id),
	    unique(name, FK_AUTHORITY),
	    foreign key (name) REFERENCES permission_group_label(name) ON UPDATE CASCADE,
	    
		foreign key (FK_AUTHORITY) REFERENCES PERMISSIONS(AUTHORITY) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE UNIQUE INDEX PERMISSION_GROUP_INSENSITIVE_CONSTRAINT on 
PERMISSION_GROUP (lower(NAME), lower(FK_AUTHORITY));


create table authorities (
        id  serial not null,
        AUTHORITY VARCHAR(50) NOT NULL,
        FK_ID_USERS_AUTH_DETAILS int4 not null,

		FOREIGN KEY (AUTHORITY) REFERENCES PERMISSIONS(AUTHORITY) ON DELETE CASCADE ON UPDATE CASCADE,
	    FOREIGN KEY (FK_ID_USERS_AUTH_DETAILS) REFERENCES USERS_AUTH_DETAILS(ID) ON DELETE CASCADE ON UPDATE CASCADE,
        unique (FK_ID_USERS_AUTH_DETAILS, AUTHORITY),
        primary key (id)
);


CREATE TABLE USERS_ACCESS_TOKEN (
	FK_ID_USERS_AUTH_DETAILS INTEGER NOT NULL,
	TOKEN_ID VARCHAR(255) NOT NULL,
	ISSUED_DATE TIMESTAMP NOT NULL,
	EXPIRATION_DATE TIMESTAMP NOT NULL,
	
	primary key(TOKEN_ID),
	FOREIGN KEY (FK_ID_USERS_AUTH_DETAILS) REFERENCES USERS_AUTH_DETAILS (ID) ON DELETE CASCADE ON UPDATE CASCADE
);
