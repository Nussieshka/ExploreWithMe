CREATE TABLE IF NOT EXISTS categories(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS events(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    annotation TEXT NOT NULL,
    category_id BIGINT NOT NULL REFERENCES categories(id),
    confirmed_requests BIGINT,
    created_on TIMESTAMP WITHOUT TIME ZONE,
    description TEXT,
    event_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    initiator_id BIGINT NOT NULL REFERENCES users(id),
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    is_paid BOOLEAN NOT NULL,
    participant_limit INTEGER,
    published_on TIMESTAMP WITHOUT TIME ZONE,
    request_moderation BOOLEAN,
    state INTEGER,
    title TEXT NOT NULL,
    views BIGINT
);

CREATE TABLE IF NOT EXISTS requests(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    created_on TIMESTAMP WITHOUT TIME ZONE,
    event_id BIGINT REFERENCES events(id),
    requester_id BIGINT REFERENCES users(id),
    status INTEGER
);

CREATE TABLE IF NOT EXISTS compilations(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR(255) NOT NULL UNIQUE,
    is_pinned BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS events_compilations(
    event_id BIGINT NOT NULL REFERENCES events(id),
    compilation_id BIGINT NOT NULL REFERENCES compilations(id)
);

CREATE TABLE IF NOT EXISTS comments(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    message TEXT,
    user_id BIGINT REFERENCES users(id),
    created_on TIMESTAMP WITHOUT TIME ZONE,
    edited_on TIMESTAMP WITHOUT TIME ZONE,
    event_id BIGINT REFERENCES events(id)
);

CREATE TABLE IF NOT EXISTS replies(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    message TEXT,
    user_id BIGINT REFERENCES users(id),
    created_on TIMESTAMP WITHOUT TIME ZONE,
    edited_on TIMESTAMP WITHOUT TIME ZONE,
    comment_id BIGINT REFERENCES comments(id)
);

CREATE TABLE IF NOT EXISTS users_replies_likes(
    user_id BIGINT NOT NULL REFERENCES users(id),
    reply_id BIGINT NOT NULL REFERENCES replies(id)
);

CREATE TABLE IF NOT EXISTS users_comments_likes(
    user_id BIGINT NOT NULL REFERENCES users(id),
    comment_id BIGINT NOT NULL REFERENCES comments(id)
);
