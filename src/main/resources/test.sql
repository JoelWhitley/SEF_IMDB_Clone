SELECT *
FROM person;
-- WHERE fullname LIKE '%Baker%';

SELECT DISTINCT fullname,person.role,birthdate,bio,character_name,show_title,start_year,genre,length
FROM credits_roll,`show`,person
WHERE credits_roll.show_id = `show`.showid AND person.person_id = credits_roll.person_id AND UPPER(fullname) LIKE '%ADAM%';

SELECT username, password
FROM account;

SELECT * FROM `show`;

SELECT fullname,person.role,birthdate,bio,character_name,showid,show_title,,movie,series,start_year,genre,length;

SELECT fullname,person.role,birthdate,bio,character_name,showid,show_title,start_year,genre,length
FROM credits_roll,`show`,person
WHERE credits_roll.show_id = `show`.showid 
AND person.person_id = credits_roll.person_id
AND UPPER(person.role) LIKE '%Producer%'
AND UPPER(fullname) LIKE '%fullName%';