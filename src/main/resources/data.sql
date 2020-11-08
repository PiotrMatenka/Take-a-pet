insert into user(id, first_name, last_name, email, password, phone_Number) values
(1, 'Andżej', 'Andżejewski', 'andzej@wp.pl', '{noop}eeeee', '364125987' ),
(2, 'Justyna','Toona',  'justynka@wp.pl', '{noop}costam1234', '145876984'),
(3, 'Piotron', 'Tojaa', 'piotron2000@wp.pl', '{noop}niewiem', '136987451'),
(4, 'Jan', 'Kowalski', 'jankowalski@wp.pl', '{noop}jan', '478936541'),
(5, 'Zygfryd', 'DeLowe', 'krzyzacy@onet.pl', '{noop}zigi', '123645872'),
(6, 'Andżelika', 'Andżelewska', 'andzela@gmail.com', '{noop}lili', '253147895');

insert into category (id, name, image_url) values
(1, 'Gady', '../img/categories/reptiles.png'),
(2, 'Płazy', '../img/categories/frog.png' ),
(3, 'Ptaki ozdobne', '../img/categories/parrot.png' ),
(4, 'Koty rasowe', '../img/categories/cat.jpg'),
(5, 'Gryzonie', '../img/categories/hamster.png'  ),
(6, 'Akwarystyka', '../img/categories/fish.png'  ),
(7, 'Psy rasowe', '../img/categories/dog.png' ),
(8, 'Oddam za darmo', '../img/categories/adoption.jpg'  ),
(9, 'Trzoda i ptactwo rolnicze', '../img/categories/cow.png'  );


insert into advertisement(id, title, description, price, city, start, end, user_id, category_id) values
(1, 'Żółw Andżejowaty', 'Zółw chiński, egzotyczny', 80.00, 'Lublin', '2019-10-08 15:00:00', null , 3, 1),
(2, 'kot Felek', 'kot dachowiec', 0.00, 'świdnik','2019-10-08 15:00:00', null , 2, 8),
(3, 'jaszczur Marian', 'legwan zielony, spory', 400.00, 'Kraśnik', '2020-10-08 15:00:00', null, 5, 1 )
;

insert into images (id, title, upload_url, delete_url, advertisement_id) values
(1, 'pic2.jpg', '../upload-dir/1/pic2.jpg', '/JAVA/animals/src/main/resources/static/upload-dir/1/pic2.jpg', 1),
(2, 'pic3.jpg', '../upload-dir/1/pic3.jpg', '/JAVA/animals/src/main/resources/static/upload-dir/1/pic3.jpg', 1),
(3, 'pic1.jpg', '../upload-dir/2/pic1.jpg', '/JAVA/animals/src/main/resources/static/upload-dir/2/pic1.jpg', 2),
(4, 'pic2.jpg', '../upload-dir/2/pic2.jpg', '/JAVA/animals/src/main/resources/static/upload-dir/2/pic2.jpg', 2);


insert into user_role(id, role, description) VALUES
(1,'USER', 'default role for user'),
(2, 'ADMIN', 'admin role, can control orders');

insert into role_user(user_id, role_id) values
(1,1),
(2,1),
(3,2),
(4,1),
(5,1),
(6,1),
(3,1);
