insert into authors(name) values ('JohnTest');
insert into authors(name) values ('MarkTest');
insert into authors(name) values ('ElonTest');

insert into genres(name) values ('ScienceTest');
insert into genres(name) values ('NovelsTest');
insert into genres(name) values ('ComedyTest');

insert into books(name, author_id, genre_id) values ('BookTest1', 1, 1);
insert into books(name, author_id, genre_id) values ('BookTest2', 2, 2);
insert into books(name, author_id, genre_id) values ('BookTest3', 3, 3);

insert into comments(description, book_id) values ('Comment1ForFirstBook', 1);
insert into comments(description, book_id) values ('Comment2ForFirstBook', 1);
insert into comments(description, book_id) values ('Comment3ForFirstBook', 1);