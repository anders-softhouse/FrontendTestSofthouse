INSERT INTO family
    (id, user_Id, family_name)
VALUES (1, '1', 'Tisäter'),
       (2, '1', 'Johansson'),
       (3, '1', 'Blomström'),
       (4, '1', 'Hermansson'),
       (5, '1', 'Lopez');

INSERT INTO person
(id, user_Id, created_at, first_name, last_name, phone_number, address, family_id)
VALUES (1, '1', '2020-06-15', 'Marcus', 'Tisäter', '070123123123', 'Tegnergatan 37', 1),
       (2, '1', '2021-06-16', 'Daniel', 'Dahlberg', '073123441234', 'Tegnergatan 37', 1),
       (3, '1', '2002-12-25', 'Ola', 'Tisäter Dahlberg', '073415980474', 'Rondovägen 123', 1),
       (4, '1', '2290-04-29', 'Greger', 'Johansson', '071340123845', 'Musikvägen 1', 2),
       (5, '1', '2432-03-31', 'Stefan', 'Johansson', '074112308501', 'Musikvägen 2', 2),
       (6, '1', '2000-10-03', 'Malin', 'Blomström', '074132953941', 'Blomgatan 3', 3),
       (7, '1', '1999-02-01', 'Josefin', 'Hermansson', '071235037512', 'Marievägen 1', 4),
       (8, '1', '2020-04-23', 'Evelina', 'Hermansson', '070000000000', 'Hemvägen 3', 4),
       (9, '1', '2020-04-25', 'Amalia', 'Lopez Gomez', '073144519300', 'Rapsodigatan 4', 5);
