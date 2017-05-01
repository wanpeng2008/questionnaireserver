INSERT INTO public.role(	id, name)	VALUES ('a0b2b514-8c9e-4c6f-86ee-1e46f97f3d34', 'ROLE_ADMIN');
INSERT INTO public.role(	id, name)	VALUES ('f972d70c-7d25-4571-80e3-51bb87ad202c', 'ROLE_USER');

INSERT INTO public.sys_user(id,username,password,email,last_pwd_reset_date) VALUES ('8e703264-b036-44b7-8222-398981a96d6d','admin','$2a$10$v4vrWiqrVmJR0fT0T/Ogxu041nwGHO9pA6gbPC43uKXVrIZcyVlx6','admin@localhost.com',now());
INSERT INTO public.user_role(user_id,role_id) VALUES ('8e703264-b036-44b7-8222-398981a96d6d','a0b2b514-8c9e-4c6f-86ee-1e46f97f3d34');