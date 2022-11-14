CREATE TABLE Person(
 id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
 firstName VARCHAR(30) NOT NULL,
 middleName VARCHAR(30) NOT NULL,
 lastName VARCHAR(30) NOT NULL,
 birthdayYear int NOT NULL CHECK (birthdayYear > 1900),
 CONSTRAINT person_name_key UNIQUE(firstName, middleName, lastName)
);


CREATE TABLE Book(
 id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
 person_id int REFERENCES Person(id) ON DELETE SET NULL,
 title VARCHAR(50) NOT NULL UNIQUE,
 author VARCHAR(50) NOT NULL,
 year int CHECK (year > 1500)
);

drop table book;
drop table person;


INSERT INTO Person (firstName, middleName, lastName, birthdayYear) VALUES
 ('Семен', 'Андреевич', 'Баландюк', 2003),
 ('Никита', 'Евгеньевич', 'Бочаров', 2003),
 ('Данил', 'Михайлович', 'Иванов', 2002),
 ('Артем', 'Сергеевич', 'Алексеев', 2002),
 ('Марк', 'Киреевич', 'Яковлев', 2000);


INSERT INTO Book (person_id, title, author, year) VALUES
   (NULL, 'Преступление и наказание', 'Ф.М. Достоевский', 1866),
   (NULL, 'Война и мир', 'Л.Н. Толстой', 1863),
   (NULL, 'Мертвые души', 'Н.В. Гоголь', 1835),
   (NULL, 'Ревизор', 'Н.В. Гоголь', 1835),
   (NULL, 'Тихий дон', 'М.А. Шолохов', 1925),
   (NULL, 'Идиот', 'Ф.М. Достоевский', 1867),
   (NULL, 'Анна Каренина', 'Л.Н. Толстой', 1873);




UPDATE Book SET person_id = 1 WHERE id = 2;

UPDATE Book SET person_id = null WHERE id = 2;

SELECT * FROM Person;
SELECT * FROM Book;


SELECT p.firstname, p.middlename, p.lastname, b.title, b.author, b.year FROM Book AS b
INNER JOIN Person p ON b.person_id = p.id;
