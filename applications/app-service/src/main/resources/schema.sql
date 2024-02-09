CREATE TABLE IF NOT EXISTS author(
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS book(
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    autor_id BIGINT,
    FOREIGN KEY (autor_id) REFERENCES author(id)
);
