insert into `role` (name) values ('ROLE_ADMIN');
insert into `role` (name) values ('ROLE_USER');

insert into `user` (first_name, last_name, `password`) values ('Admin', 'Userovich', '$2a$12$bXGFAo/nTzGbGVQRpK8LZ.dGZ.7bqtCJwh8Ae9UEyBhsUsHR369yq');
insert into `user` (first_name, last_name, `password`) values ('Walter', 'White', '$2a$12$cipoQtzx9IY9YFfrCBAtneDcmdChjqAELesYVMgJhfKx.u4oQCBQ2');
insert into `user` (first_name, last_name, `password`) values ('Hank', 'Schrader', '$2a$12$A38TQgs.BkOg5r/wOjU0CudcfTrELfqFbwfwNtbVWMc6XEPhlLfEu');
insert into `user` (first_name, last_name, `password`) values ('Saul', 'Goodman', '$2a$12$H5sEY8Ht/4Tm4sEhrPPzP.eM/PWT.8RZIuRbA3rs4mRHOcgWJEscW');

insert into user_role (user_id, role_id) values (1, 1);
insert into user_role (user_id, role_id) values (1, 2);
insert into user_role (user_id, role_id) values (2, 1);
insert into user_role (user_id, role_id) values (3, 2);
insert into user_role (user_id, role_id) values (4, 2);