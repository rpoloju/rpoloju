DROP DATABASE IF EXISTS INFO5707;

CREATE DATABASE IF NOT EXISTS INFO5707;
 
USE INFO5707;


CREATE TABLE author(
   auth_id varchar(32),
   auth_f_name varchar(32),
   auth_m_name varchar(32),
   auth_l_name varchar(32),
   auth_nick_name varchar(32),
   auth_address varchar(255),
   auth_email varchar(255),
   auth_phone varchar(32),
   auth_mobile varchar(32),
   auth_website varchar(255),
   PRIMARY KEY (auth_id));

CREATE TABLE file_type(
   file_type_id varchar(32),
   file_type_name varchar(255),
   file_type_desc varchar(255),
   PRIMARY KEY (file_type_id)
);

CREATE TABLE author_type(
   auth_type_id varchar(32),
   auth_type varchar(255),
   resource_id varchar(32),
   PRIMARY KEY (auth_type_id)
);

CREATE TABLE keyword(
   keyword_id varchar(32),
   resource_id varchar(32),
   keyword varchar(255),
   PRIMARY KEY (keyword_id)
);

CREATE TABLE src_type(
   src_type_id varchar(32),
   resource_id varchar(32),
   PRIMARY KEY (src_type_id)
);

CREATE TABLE subject(
   subject_id varchar(32),
   resource_id varchar(32),
   PRIMARY KEY (subject_id)
);

CREATE TABLE lang(
   lang_id varchar(32),
   resource_id varchar(32),
   PRIMARY KEY (lang_id)
);

CREATE TABLE publisher(
   publisher_id varchar(32),
   pub_name varchar(255),
   publisher_address varchar(255),
   publisher_email varchar(255),
   publisher_phone varchar(32),
   resource_id varchar(32),
   PRIMARY KEY (publisher_id)
);

CREATE TABLE article_resource(
   resource_id varchar(32),
   title varchar(255),
   pub_date DATE,
   source_hyperlink varchar(255),
   source_file varchar(255),
   publisher_id varchar(32),
   lang_id varchar(32),
   pub_type varchar(32),
   subject_id varchar(32),
   src_type_id varchar(32),
   keyword_id varchar(32),
   auth_type_id varchar(32),
   source_file_type_id varchar(32),
   PRIMARY KEY (resource_id
));

ALTER TABLE publisher ADD FOREIGN KEY (resource_id) REFERENCES article_resource (resource_id);

ALTER TABLE author_type
   ADD FOREIGN KEY (resource_id) REFERENCES article_resource (resource_id);

ALTER TABLE keyword
   ADD FOREIGN KEY (resource_id) REFERENCES article_resource (resource_id);

ALTER TABLE src_type
   ADD FOREIGN KEY (resource_id) REFERENCES article_resource (resource_id);

ALTER TABLE subject
   ADD FOREIGN KEY (resource_id) REFERENCES article_resource (resource_id);

ALTER TABLE lang
   ADD FOREIGN KEY (resource_id) REFERENCES article_resource (resource_id);

ALTER TABLE article_resource
   ADD FOREIGN KEY (publisher_id) REFERENCES publisher (publisher_id),
   ADD FOREIGN KEY (lang_id) REFERENCES lang (lang_id),
   ADD FOREIGN KEY (subject_id) REFERENCES subject (subject_id),
   ADD FOREIGN KEY (src_type_id) REFERENCES src_type (src_type_id),
   ADD FOREIGN KEY (keyword_id) REFERENCES keyword (keyword_id),
   ADD FOREIGN KEY (auth_type_id) REFERENCES author_type (auth_type_id),
   ADD FOREIGN KEY (source_file_type_id) REFERENCES file_type (file_type_id);
 

