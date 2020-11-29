COPY employees (id, username, password, full_name, enabled) FROM stdin;
1	admin	$2a$10$.IoUh1LIln7J6ERBWiUy5uiHrMbsfe47AYWIqZcrxfwWGCqmza5K2	Bruce Nolan	t
2	doctor	$2a$12$/I4LSWjhrHRSVmSGcauOWuNTixyoo/PEP3SyCfpXK1izOB0czgUkG	Gregory House	t
3	nurse	$2a$12$hCpO5uP1VlWAd62zHsDsuuIqyeyzhimiSEOe68WBj/DHIfaHSG8m6	Carla Espinosa	t
4	C.Turk	$2a$10$zVIjDX/w394eBHlAVc1qy.pgczjjX0WT6ALhdxMq388H730VALEla	Christopher Turk	t
5	jd	$2a$10$3wS2o0slX1tH7vXkPaw/puruD2gcztTdUY2vifL3/Ls6ghZkWXZ.a	John Dorian	t
\.

COPY roles (id, name) FROM stdin;
1	ADMIN
2	DOCTOR
3	NURSE
\.

COPY employees_roles (user_id, role_id) FROM stdin;
1	1
2	2
3	3
4	2
5	2
\.

COPY treatments (id, name, is_drug) FROM stdin;
1	Aspirine	t
2	Massage	f
3	Paracetomol	t
4	Brilliant green	f
5	Psychotherapy session	f
6	Penicillin	t
7	Antihistaminic	t
\.

COPY diagnoses (id, mkb_code, mkb_name) FROM stdin;
1	W01.2\n	Падение на поверхности одного уровня в результате поскальзывания, ложного шага или спотыкания. В школе, другом учреждении и общественном административном районе\n
2	X20\n	Контакт с ядовитыми змеями и ящерицами\n
3	A30\n	Лепра [болезнь Гансена]\n
4	T78.4	Аллергия неуточненная
5	R78.0	Обнаружение алкоголя в крови
6	F32.0\n	Депрессивный эпизод легкой степени
7	W50.0\n	Удар, толчок, пинок, выкручивание, укус или оцарапывание другим лицом. В доме, квартире, жилом здании\n
8	W57.6	Укус или ужаливание неядовитым насекомым и другими неядовитыми членистоногими. На производственных и строительных площадях и в помещениях
\.

INSERT INTO patients
(name, birth_date, insure_num, healthy, doctor_id) VALUES
('Tony Stark', '1987-12-25', 'MARK_00', false, '2'),
('Peter Parker', '2005-08-1', 'MARK_1', false, '1')

