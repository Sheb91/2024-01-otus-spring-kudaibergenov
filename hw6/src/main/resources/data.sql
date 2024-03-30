insert into authors(name) values ('John');
insert into authors(name) values ('Mark');
insert into authors(name) values ('Elon');

insert into genres(name) values ('Science');
insert into genres(name) values ('Novels');
insert into genres(name) values ('Comedy');

insert into books(name, author_id, genre_id) values ('The quantum physics', 1, 1);
insert into books(name, author_id, genre_id) values ('A Connecticut Yankee in King Arthur''s Court', 2, 2);
insert into books(name, author_id, genre_id) values ('Ha-ha', 3, 3);

insert into comments(description, book_id) values ('This is f**king awesome !', 1);
insert into comments(description, book_id) values ('Like this !', 1);
insert into comments(description, book_id) values ('Hate it all', 1);
insert into comments(description, book_id) values ('I want to read it again', 1);