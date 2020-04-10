SELECT *
FROM person;
-- WHERE fullname LIKE '%Baker%';

SELECT DISTINCT fullname,person.role,birthdate,bio,character_name,show_title,start_year,genre,length
FROM credits_roll,`show`,person
WHERE credits_roll.show_id = `show`.showid AND person.person_id = credits_roll.person_id AND UPPER(fullname) LIKE '%ADAM%';

SELECT username, password
FROM account;

SELECT * FROM `show`;

SELECT fullname,person.role,birthdate,bio,character_name,showid,show_title,movie,series,start_year,genre,length
FROM credits_roll,`show`,person
WHERE credits_roll.show_id = `show`.showid 
AND person.person_id = credits_roll.person_id
AND UPPER(person.role) LIKE '%Producer%';

SELECT `show`.showid, `show`.show_title, `show`.genre, `show`.length, `show`.movie, `show`.series, `show`.proco_id, `show`.`year`
FROM `show`
LEFT JOIN `credits_roll`
ON `show`.showid = credits_roll.show_id
LEFT JOIN `person`
on credits_roll.person_id = person.person_id
WHERE ( UPPER(show_title) LIKE UPPER('%star%')
OR UPPER(genre) LIKE UPPER('%star%')
OR UPPER(person.fullname) LIKE UPPER('%star%')
OR UPPER(credits_roll.character_name) LIKE UPPER('%star%'))
group by showid;