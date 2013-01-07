# default


# --- !Ups

CREATE TABLE users (
	id         BIGINT     NOT NULL AUTO_INCREMENT,
	name       VARCHAR   NOT NULL,
	created_at TIMESTAMP,
	updated_at TIMESTAMP,
	PRIMARY KEY (id),
	UNIQUE INDEX uidx_name (name)
);

CREATE TABLE boxes (
	id            BIGINT     NOT NULL AUTO_INCREMENT,
	created_by_id BIGINT     NOT NULL,
	name          VARCHAR   NOT NULL,
	created_at    TIMESTAMP,
	updated_at    TIMESTAMP,
	PRIMARY KEY (id),
	FOREIGN KEY (created_by_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE story_cards (
	id            BIGINT     NOT NULL AUTO_INCREMENT,
	box_id        BIGINT     NOT NULL,
	created_by_id BIGINT     NOT NULL,
	front         VARCHAR   NOT NULL,
	back          VARCHAR   NOT NULL,
	points        INT        NOT NULL,
	done          BOOLEAN    NOT NULL,
	created_at    TIMESTAMP,
	updated_at    TIMESTAMP,
	PRIMARY KEY (id),
	FOREIGN KEY (box_id)        REFERENCES boxes(id) ON DELETE CASCADE,
	FOREIGN KEY (created_by_id) REFERENCES users(id) ON DELETE CASCADE
);


# --- !Downs
 
DROP TABLE users;

DROP TABLE boxes;

DROP TABLE story_cards;
