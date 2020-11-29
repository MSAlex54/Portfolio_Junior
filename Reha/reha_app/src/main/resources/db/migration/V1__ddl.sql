CREATE TABLE patients (
    id bigint NOT NULL,
    name character varying(150),
    birth_date date,
    insure_num character varying(50),
    healthy boolean DEFAULT false NOT NULL,
    doctor_id bigint
);

ALTER TABLE patients ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME patients_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

CREATE TABLE patients_diagnoses (
    id bigint NOT NULL,
    patient_id bigint NOT NULL,
    diagnosis_id bigint NOT NULL,
    is_active boolean
);

ALTER TABLE patients_diagnoses ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME patients_diagnoses_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

CREATE TABLE treatments (
    id bigint NOT NULL,
    name character varying(255),
    is_drug boolean
);

ALTER TABLE treatments ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME treatments_treatments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

CREATE TABLE assignments (
    id bigint NOT NULL,
    patient_id bigint NOT NULL,
    treatment_id bigint NOT NULL,
    start_date date,
    finish_date date,
    interval_size integer,
    interval_type character varying(50),
    days_of_week character varying(100),
    day_of_month integer,
    moments character varying(50),
    dose character varying(255),
    status character varying(50)
);

ALTER TABLE assignments ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME assignment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

CREATE TABLE diagnoses (
    id bigint NOT NULL,
    mkb_code character varying(50),
    mkb_name text
);


ALTER TABLE diagnoses ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME diagnoses_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

CREATE TABLE events (
    id bigint NOT NULL,
    assignment_id bigint,
    patient_id bigint,
    date_time timestamp without time zone,
    treatment_id bigint,
    dose character varying(255),
    note text,
    status character varying(50),
    active boolean
);

ALTER TABLE events ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME event_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

-- users and roles
CREATE TABLE employees (
    id bigint NOT NULL,
    username character varying(50) NOT NULL,
    password character varying(255) NOT NULL,
    full_name character varying(150) NOT NULL,
    enabled boolean NOT NULL
);

ALTER TABLE employees ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME employees_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

CREATE TABLE employees_roles (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);

CREATE TABLE roles (
    id bigint NOT NULL,
    name character varying(25) NOT NULL
);

ALTER TABLE roles ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


ALTER TABLE ONLY assignments
    ADD CONSTRAINT assignment_pkey PRIMARY KEY (id);

ALTER TABLE ONLY diagnoses
    ADD CONSTRAINT diagnosis_pkey PRIMARY KEY (id);

ALTER TABLE ONLY employees
    ADD CONSTRAINT employees_pkey PRIMARY KEY (id);

ALTER TABLE ONLY events
    ADD CONSTRAINT events_pkey PRIMARY KEY (id);

ALTER TABLE ONLY patients_diagnoses
    ADD CONSTRAINT patients_diagnoses_pkey PRIMARY KEY (id);

ALTER TABLE ONLY patients
    ADD CONSTRAINT patients_pkey PRIMARY KEY (id);

ALTER TABLE ONLY roles
    ADD CONSTRAINT roles_name_key UNIQUE (name);

ALTER TABLE ONLY roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);

ALTER TABLE ONLY treatments
    ADD CONSTRAINT treatments_pkey PRIMARY KEY (id);

ALTER TABLE ONLY employees
    ADD CONSTRAINT username UNIQUE (username);

ALTER TABLE ONLY employees_roles
    ADD CONSTRAINT users_roles_pkey PRIMARY KEY (user_id);

ALTER TABLE ONLY assignments
    ADD CONSTRAINT assign_pat_fk FOREIGN KEY (patient_id) REFERENCES patients(id) NOT VALID;

ALTER TABLE ONLY assignments
    ADD CONSTRAINT assign_treat_fk FOREIGN KEY (treatment_id) REFERENCES treatments(id) NOT VALID;

ALTER TABLE ONLY events
    ADD CONSTRAINT event_assignment FOREIGN KEY (assignment_id) REFERENCES assignments(id);

ALTER TABLE ONLY events
    ADD CONSTRAINT event_patient FOREIGN KEY (patient_id) REFERENCES patients(id) NOT VALID;

ALTER TABLE ONLY patients_diagnoses
    ADD CONSTRAINT fk_diagnosis FOREIGN KEY (diagnosis_id) REFERENCES diagnoses(id) NOT VALID;

ALTER TABLE ONLY patients_diagnoses
    ADD CONSTRAINT fk_patient FOREIGN KEY (patient_id) REFERENCES patients(id) NOT VALID;

ALTER TABLE ONLY patients
    ADD CONSTRAINT fk_patients_doctor FOREIGN KEY (doctor_id) REFERENCES employees(id) NOT VALID;

ALTER TABLE ONLY employees_roles
    ADD CONSTRAINT role_id_fk FOREIGN KEY (role_id) REFERENCES roles(id);

ALTER TABLE ONLY events
    ADD CONSTRAINT treatment_event FOREIGN KEY (treatment_id) REFERENCES treatments(id);

ALTER TABLE ONLY employees_roles
    ADD CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES employees(id) NOT VALID;