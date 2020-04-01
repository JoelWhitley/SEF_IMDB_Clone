SELECT *
FROM person;
-- WHERE fullname LIKE '%Baker%';

SELECT credits_roll.person_id, fullname, person.role,show_id, character_name,show_title
FROM credits_roll,`show`,person
WHERE credits_roll.show_id = `show`.showid AND person.person_id = credits_roll.person_id;

