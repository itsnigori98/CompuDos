INSERT INTO events (event_code, event_date, location, description) VALUES ('EVT-001', '2025-11-05', 'Auditorio Principal', 'Conferencia sobre Innovacion Tecnologica.');
INSERT INTO events (event_code, event_date, location, description) VALUES ('EVT-002', '2025-12-10', 'Sala de Conferencias A', 'Taller de Desarrollo Personal.');
INSERT INTO events (event_code, event_date, location, description) VALUES ('EVT-003', '2025-11-20', 'Plaza Central', NULL);

--

INSERT INTO exercises (exercise_code, title, due_date, max_score) VALUES ('EX-001', 'Ejercicio de Algebra', '2025-10-15', 100);
INSERT INTO exercises (exercise_code, title, due_date, max_score) VALUES ('EX-002', 'Ejercicio de Calculo', '2025-10-20', 100);
INSERT INTO exercises (exercise_code, title, due_date, max_score) VALUES ('EX-003', 'Ejercicio de Fisica', '2025-10-25', 100);
INSERT INTO exercises (exercise_code, title, due_date, max_score) VALUES ('EX-004', 'Ejercicio de Quimica', '2025-10-30', 100);

--
INSERT INTO history (log_code, log_date, action, details) VALUES ('LOG-001', '2025-09-01 10:00:00', 'CREACION', 'Se creo un nuevo usuario en el sistema.');
INSERT INTO history (log_code, log_date, action, details) VALUES ('LOG-002', '2025-09-02 14:30:00', 'ACTUALIZACION', 'Se actualizo la informacion del perfil del usuario.');
INSERT INTO history (log_code, log_date, action, details) VALUES ('LOG-003', '2025-09-03 09:15:00', 'ELIMINACION', 'Se elimino un registro de la base de datos.');
INSERT INTO history (log_code, log_date, action, details) VALUES ('LOG-004', '2025-09-04 16:45:00', 'ACCESO', 'El usuario accedio al sistema desde una nueva ubicacion.');
