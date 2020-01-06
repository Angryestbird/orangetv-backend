DROP TABLE IF EXISTS movie;
CREATE VIRTUAL TABLE movie USING fts4(title,actor,description);