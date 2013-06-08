#!/bin/bash

echo -n "username "
read username

echo -n "password "
read password

#echo -n "host"
#read host

#username="root"
#password="password"
host="localhost"


db="library"

# book_master: [ book_id, name, publisher, copies, price, version, issued_copies ]
book_master="create table if not exists book_master (id INT NOT NULL AUTO_INCREMENT, book_id INT, name VARCHAR(100), publisher VARCHAR(50), copies INT, price FLOAT, version VARCHAR(20), issued_copies INT, PRIMARY KEY (id));"

echo $book_master | mysql -h $host -u $username -p$password $db

## issued_books : [ book_id, stud_id, due_date, issue_date ]
issued_books="create table if not exists issued_books (id INT NOT NULL AUTO_INCREMENT, book_id INT, stud_id INT, due_date DATE, issue_date DATE, PRIMARY KEY (id));"

echo $issued_books | mysql -h$host -u$username -p$password $db

# login : [ username, password, roll, id ]
login="create table if not exists login (id INT NOT NULL AUTO_INCREMENT, username VARCHAR(100), password VARCHAR(200), roll INT(11), PRIMARY KEY (id));"

echo $login | mysql -h$host -u$username -p$password $db

 order_table : [ fid, bookname, copies, publication, version, price ]
order_table="create table if not exists order_table (id INT NOT NULL AUTO_INCREMENT, fid INT, name VARCHAR(100), copies INT, publication VARCHAR(100), version VARCHAR(50), price FLOAT, PRIMARY KEY (id));"

echo $order_table | mysql -h$host -u$username -p$password $db
