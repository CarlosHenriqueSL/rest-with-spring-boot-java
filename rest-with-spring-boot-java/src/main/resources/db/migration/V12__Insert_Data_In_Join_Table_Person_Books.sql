-- seeds the 'person_books' table by randomly assigning books to people

INSERT INTO person_books (person_id, book_id)
SELECT 
    p.id AS person_id, 
    b.id AS book_id
FROM 
    (SELECT id FROM person WHERE id <= 12) p
CROSS JOIN
    (SELECT id FROM books ORDER BY RAND() LIMIT 20) b;

INSERT INTO person_books (person_id, book_id)
SELECT
    p.id AS person_id,
    b.id AS book_id
FROM
    (SELECT id FROM person WHERE id > 12) p
CROSS JOIN
    (SELECT id FROM books ORDER BY RAND() LIMIT 3) b;