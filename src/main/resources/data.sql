
insert into user_details (id,first_name, last_name, phone_Number) values
(1, 'Andżej', 'Andżejewski','364125987'),
(2, 'Justyna','Toona','145876984' ),
(3, 'Piotron', 'Tojaa','136987451'),
(4, 'Jan', 'Kowalski','478936541'),
(5, 'Zygfryd', 'DeLowe','123645872'),
(6, 'Andżelika', 'Andżelewska', '253147895');


insert into user(id,  email, password, user_details_id, is_enabled) values
(1, 'andzej@wp.pl', '{noop}eeeee', 1, true ),
(2, 'justynka@wp.pl', '{noop}costam1234' , 2, true ),
(3,  'pioterm185@wp.pl', '{noop}niewiem' , 3, true),
(4,  'jankowalski@wp.pl', '{noop}jan', 4, true),
(5,  'krzyzacy@onet.pl', '{noop}zigi', 5 , true),
(6,  'andzela@gmail.com', '{noop}lili', 6, true);

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
(3, 'jaszczur Marian', 'legwan zielony, spory', 400.00, 'Kraśnik', '2020-10-08 15:00:00', null, 5, 1 ),
(4, 'Żółw Andżejowaty2', 'Zółw chiński, egzotyczny', 80.00, 'Lublin', '2019-10-08 15:00:00', null , 3, 1),
(5, 'Żółw Andżejowaty3', 'Zółw chiński, egzotyczny', 80.00, 'Lublin', '2019-10-08 15:00:00', null , 3, 1),
(6, 'Żółw Andżejowaty4', 'Zółw chiński, egzotyczny', 80.00, 'Lublin', '2019-10-08 15:00:00', null , 3, 1),
(7, 'Żółw Andżejowaty5', 'Zółw chiński, egzotyczny', 80.00, 'Lublin', '2019-10-08 15:00:00', null , 3, 1)
;

insert into images (id, title, upload_url, delete_url, advertisement_id) values
(1, 'pic2.jpg', '/upload-dir/1/pic2.jpg', '/JAVA/Take a pet/src/main/resources/static/upload-dir/1/pic2.jpg', 1),
(2, 'pic3.jpg', '/upload-dir/1/pic3.jpg', '/JAVA/Take a pet/src/main/resources/static/upload-dir/1/pic3.jpg', 1),
(3, 'pic1.jpg', '/upload-dir/2/pic1.jpg', '/JAVA/Take a pet/src/main/resources/static/upload-dir/2/pic1.jpg', 2),
(4, 'pic2.jpg', '/upload-dir/2/pic2.jpg', '/JAVA/Take a pet/src/main/resources/static/upload-dir/2/pic2.jpg', 2),
(5, 'pic8.jpg', '/upload-dir/4/pic8.jpg', '/JAVA/Take a pet/src/main/resources/static/upload-dir/4/pic8.jpg', 4),
(6, 'pic7.jpg', '/upload-dir/5/pic7.jpg', '/JAVA/Take a pet/src/main/resources/static/upload-dir/5/pic7.jpg', 5),
(7, 'pic7.jpg', '/upload-dir/4/pic7.jpg', '/JAVA/Take a pet/src/main/resources/static/upload-dir/4/pic7.jpg', 4),
(8, 'pic8.jpg', '/upload-dir/5/pic8.jpg', '/JAVA/Take a pet/src/main/resources/static/upload-dir/5/pic8.jpg', 5),
(9, 'pic7.jpg', '/upload-dir/6/pic7.jpg', '/JAVA/Take a pet/src/main/resources/static/upload-dir/6/pic7.jpg', 6),
(10, 'pic7.jpg', '/upload-dir/7/pic7.jpg', '/JAVA/Take a pet/src/main/resources/static/upload-dir/7/pic7.jpg', 7),
(11, 'pic6.jpg', '/upload-dir/7/pic6.jpg', '/JAVA/Take a pet/src/main/resources/static/upload-dir/7/pic6.jpg', 7)
;



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
