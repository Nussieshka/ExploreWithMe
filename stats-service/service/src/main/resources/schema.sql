CREATE TABLE IF NOT EXISTS requests(id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, app VARCHAR(255), uri VARCHAR(255), ip VARCHAR(255), timestamp TIMESTAMP WITHOUT TIME ZONE);