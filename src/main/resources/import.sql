INSERT INTO db_pizzeria.pizze (id, descrizione, foto, name, prezzo)VALUES(1, 'la tipica pizza napoletana, condita con pomodoro, mozzarella, basilico fresco, sale e olio', 'https://upload.wikimedia.org/wikipedia/commons/c/c8/Pizza_Margherita_stu_spivack.jpg', 'Margherita', 5.00);
INSERT INTO db_pizzeria.pizze(id, descrizione, foto, name, prezzo)VALUES(2, 'Gli ingredienti base di questa pizza sono il pomodoro, aglio, olio e origano', 'https://upload.wikimedia.org/wikipedia/commons/1/11/Pizza_marinara.jpg', 'Marinara', 4.00);

INSERT INTO db_pizzeria.ingredienti(id,name)VALUES(1, "Pomodoro")
INSERT INTO db_pizzeria.ingredienti(id,name)VALUES(2, "Basilico")
INSERT INTO db_pizzeria.ingredienti(id,name)VALUES(3, "Fior di latte")
INSERT INTO db_pizzeria.ingredienti(id,name)VALUES(4, "Origano")
INSERT INTO db_pizzeria.ingredienti(id,name)VALUES(5, "Aglio")
INSERT INTO db_pizzeria.ingredienti(id,name)VALUES(6, "Olio d'oliva")
INSERT INTO db_pizzeria.ingredienti(id,name)VALUES(7, "Patatine")
INSERT INTO db_pizzeria.ingredienti(id,name)VALUES(8, "Salsicce")
INSERT INTO db_pizzeria.ingredienti(id,name)VALUES(9, "Mozzarella")
INSERT INTO db_pizzeria.ingredienti(id,name)VALUES(10, "Salame piccante")
INSERT INTO db_pizzeria.ingredienti(id,name)VALUES(11, "Friarielli")
INSERT INTO db_pizzeria.ingredienti(id,name)VALUES(12, "Provola")

INSERT INTO db_pizzeria.roles(id, name)VALUES(1, 'ADMIN');
INSERT INTO db_pizzeria.roles(id, name)VALUES(2, 'USER');

INSERT INTO db_pizzeria.`user`(id, email, first_name, last_name, registerd_at, password)VALUES(1, 'john@email.com', 'John', 'Doe', '2023-11-20 10:35', '{noop}john');
INSERT INTO db_pizzeria.`user`(id, email, first_name, last_name, registerd_at, password)VALUES(2, 'jane@email.com', 'Jane', 'Smith', '2023-11-20 10:37', '{noop}jane');

INSERT INTO db_pizzeria.user_roles(user_id, roles_id)VALUES(1, 1);
INSERT INTO db_pizzeria.user_roles(user_id, roles_id)VALUES(1, 2);
INSERT INTO db_pizzeria.user_roles(user_id, roles_id)VALUES(2, 2);
