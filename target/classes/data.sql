INSERT INTO family
    (id, family_name)
VALUES (100, 'Tisäter'),
       (101, 'Johansson'),
       (102, 'Blomström'),
       (103, 'Hermansson'),
       (104, 'Lopez');

INSERT INTO person
(id, created_at, first_name, last_name, phone_number, address, family_id)
VALUES (100, '2020-06-15', 'Marcus', 'Tisäter', '070123123123', 'Tegnergatan 37', 100),
       (101, '2021-06-16', 'Daniel', 'Dahlberg', '073123441234', 'Tegnergatan 37', 100),
       (102, '2002-12-25', 'Ola', 'Tisäter Dahlberg', '073415980474', 'Rondovägen 123', 100),
       (103, '2290-04-29', 'Greger', 'Johansson', '071340123845', 'Musikvägen 1', 101),
       (104, '2432-03-31', 'Stefan', 'Johansson', '074112308501', 'Musikvägen 2', 101),
       (105, '2000-10-03', 'Malin', 'Blomström', '074132953941', 'Blomgatan 3', 102),
       (106, '1999-02-01', 'Josefin', 'Hermansson', '071235037512', 'Marievägen 1', 103),
       (107, '2020-04-23', 'Evelina', 'Hermansson', '070000000000', 'Hemvägen 3', 103),
       (108, '2020-04-25', 'Amalia', 'Lopez Gomez', '073144519300', 'Rapsodigatan 4', 104);
