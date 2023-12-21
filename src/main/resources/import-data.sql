insert into `role` (name)
values ('ROLE_ADMIN'),
       ('ROLE_USER');


#       LOGIN                   PASSWORDS:
#     adminuser@mail.com:       adminuser
#     admin@mail.com:           admin
#     walter@mail.com:          walter
#     hank@mail.com:            hank
#     saul@mail.com:            saul

insert into `user` (first_name, last_name, age, email, `password`)
values ('Adminuser', 'Userovich', 18, 'adminuser@mail.com', '$2a$12$J81crXT7LcaD5LymyI/Tl.OQbImZbtKpi5XqdLHnzG/foj2LwF8L2'),
       ('Admin', 'Adminovich', 17, 'admin@mail.com', '$2a$12$cxdysT3VXyx7ds4ti9DR9evhUiK5HhFOEzBr70d9Q/kGbxG64tx36'),
       ('Walter', 'White', 45, 'walter@mail.com', '$2a$12$p1gEwWOElOkPFf7VIPd3ZOC7458cpq9ewPCbOIHoPzSlFJk0BBOz2'),
       ('Hank', 'Schrader', 42, 'hank@mail.com', '$2a$12$A38TQgs.BkOg5r/wOjU0CudcfTrELfqFbwfwNtbVWMc6XEPhlLfEu'),
       ('Saul', 'Goodman', 41, 'saul@mail.com', '$2a$12$H5sEY8Ht/4Tm4sEhrPPzP.eM/PWT.8RZIuRbA3rs4mRHOcgWJEscW');


insert into user_role (user_id, role_id)
values (1, 1),
       (1, 2),
       (2, 1),
       (3, 2),
       (4, 2),
       (5, 2);
