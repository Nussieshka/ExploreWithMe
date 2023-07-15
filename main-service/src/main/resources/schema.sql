CREATE TABLE IF NOT EXISTS categories(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS users(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email VARCHAR(255),
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS events(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    annotation VARCHAR(255) NOT NULL,
    category_id BIGINT NOT NULL REFERENCES categories(id),
    confirmed_requests BIGINT,
    created_on TIMESTAMP,
    description TEXT,
    event_date TIMESTAMP NOT NULL,
    initiator_id BIGINT NOT NULL REFERENCES users(id),
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    is_paid BOOLEAN NOT NULL,
    participant_limit INTEGER,
    published_on TIMESTAMP,
    request_moderation BOOLEAN,
    state INTEGER,
    title VARCHAR(255) NOT NULL,
    views BIGINT
);

CREATE TABLE IF NOT EXISTS requests(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    created_on TIMESTAMP,
    event_id BIGINT REFERENCES events(id),
    requester_id BIGINT REFERENCES users(id),
    category_status INTEGER
);

CREATE TABLE IF NOT EXISTS compilations(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    is_pinned BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS events_compilations(
    event_id BIGINT NOT NULL REFERENCES events(id),
    compilation_id BIGINT NOT NULL REFERENCES compilations(id)
);
