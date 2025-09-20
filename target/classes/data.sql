
-- ============================================
-- USERS (inserciones con todos los campos)
-- ============================================

INSERT INTO users (id,name,email,password, age, weight, sex, type)
VALUES (1,'Ernesto De la Cruz','1111111111@u.icesi.edu.co','1234',28, 72.5, 'M', 'Colaborador');

INSERT INTO users (id,name, email,password,age, weight, sex, type)
VALUES (2,'Mohamed Sahur','1111111112@u.icesi.edu.co','12345', 25, 60.0, 'F', 'Estudiante');

INSERT INTO users (id,name,email,password, age, weight, sex, type)
VALUES (3,'Carlos Ramirez','1111111113@u.icesi.edu.co','12346', 35, 80.3, 'M', 'Colaborador');

INSERT INTO users (id,name,email,password, age, weight, sex, type)
VALUES (4,'Ana Torres','1111111114@u.icesi.edu.co','12347', 30, 55.8, 'F', 'Estudiante');



-- ============================================
-- TRAINERS
-- ============================================
INSERT INTO trainers (id, name,specialty,cellphone) VALUES (1, 'Pablo Perez','Pilates','5050000');
INSERT INTO trainers (id, name,specialty,cellphone) VALUES (2, 'Martin Martinez','Bicicleta','5050001');
-- ============================================
-- NOTIFICATIONS
-- ============================================
INSERT INTO notifications (id, title, description)
VALUES (1, 'Clase Pilates', 'Clase de pilates programada');

INSERT INTO notifications (id, title, description)
VALUES (2, 'Pago pendiente', 'Recuerda pagar tu mensualidad');

INSERT INTO notifications (id, title, description)
VALUES (3, 'Nueva rutina', 'Se te asignó una nueva rutina de entrenamiento');

-- USER - NOTIFICATIONS
INSERT INTO user_notifications (user_id, notification_id) VALUES (1, 1);
INSERT INTO user_notifications (user_id, notification_id) VALUES (2, 1);
INSERT INTO user_notifications (user_id, notification_id) VALUES (1, 2);
INSERT INTO user_notifications (user_id, notification_id) VALUES (2, 3);

-- ============================================
-- EVENTS
-- ============================================
INSERT INTO events (id, name, date, notificationId)
VALUES (1, 'Clase Pilates', '2025-09-19 10:30:00', 1);

-- ============================================
-- EXERCISES
-- ============================================
INSERT INTO exercises (id, name, type, description, sets_Count, repetitions, video_Url)

VALUES (1, 'Press Bench', 'Fuerza', 'Levantar peso con una Barra larga en una banca a la altura del pecho',
        4, 10, 'https://www.youtube.com/watch?v=SCVCLChPQFY');

INSERT INTO exercises (id, name, type, description, sets_Count, repetitions, video_Url)
VALUES (2, 'Sentadillas', 'Fuerza', 'Ejercicio de piernas con peso corporal',
        3, 12, 'https://www.youtube.com/watch?v=aclHkVaku9U');

INSERT INTO exercises (id, name, type, description, sets_Count, repetitions, video_Url)
VALUES (3, 'Burpees', 'Cardio', 'Ejercicio de cuerpo completo',
        3, 15, 'https://www.youtube.com/watch?v=TU8QYVW0gDU');

-- ============================================
-- RUTINES
-- ============================================
INSERT INTO rutines (id, name ,type) VALUES (1, 'Full Body Rutine','Fuerza');
INSERT INTO rutines (id, name,type) VALUES (2, 'Cardio Express','Cardio');
INSERT INTO rutines (id, name,type) VALUES (3, 'Extreme Flexibility','Flexibilidad');

-- USER - RUTINES
INSERT INTO user_rutines (user_id, rutine_id) VALUES (1, 1);
INSERT INTO user_rutines (user_id, rutine_id) VALUES (2, 1);
INSERT INTO user_rutines (user_id, rutine_id) VALUES (2, 2);

-- RUTINE - EXERCISES
-- Full Body Rutine: Press Bench + Sentadillas
INSERT INTO rutine_exercises (rutine_id, exercise_id) VALUES (1, 1);
INSERT INTO rutine_exercises (rutine_id, exercise_id) VALUES (1, 2);

-- Cardio Express: Burpees + Sentadillas
INSERT INTO rutine_exercises (rutine_id, exercise_id) VALUES (2, 2);
INSERT INTO rutine_exercises (rutine_id, exercise_id) VALUES (2, 3);

-- ============================================
-- PROGRESSES
-- ============================================

INSERT INTO progresses (id, repetitions, time, user_id, progress_date)
VALUES (1, 12, 600, 2, '2025-09-19');

INSERT INTO progresses (id, repetitions, time, user_id, progress_date)
VALUES (2, 8, 300, 1, '2025-09-20');


-- ============================================
-- ROLES
-- ============================================

INSERT INTO roles (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_USER');


-- ============================================
-- PERMISOS
-- ============================================

INSERT INTO permissions (id, name) VALUES (1, 'CREATE_RUTINE');
INSERT INTO permissions (id, name) VALUES (2, 'VIEW_PROGRESS');
INSERT INTO permissions (id, name) VALUES (3, 'MANAGE_USERS');

-- ============================================
-- RELACIÓN ROLE - PERMISSIONS
-- ============================================

INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 1);
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 2);
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 3);
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 2);

-- USUARIOS (ya los tienes: Juan y María)
-- RELACIÓN USER - ROLE
INSERT INTO user_roles (user_id, role_id) VALUES (1, 2); -- Juan es ROLE_USER
INSERT INTO user_roles (user_id, role_id) VALUES (2, 1); -- María es ROLE_ADMIN
