CREATE TABLE Book (
    id SERIAL PRIMARY KEY,
    title VARCHAR(500),
    author VARCHAR(300),
    inclusion_date TIMESTAMP,
    date_of_the_conclusion TIMESTAMP,
    evaluation_grade INTEGER,
    status VARCHAR(10)
);