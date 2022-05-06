

CREATE TABLE IF NOT EXISTS public.users_table
(
    id bigint NOT NULL DEFAULT nextval('users_table_id_seq'::regclass),
    address character varying(255) COLLATE pg_catalog."default",
    email_id character varying(255) COLLATE pg_catalog."default",
    phone character varying(255) COLLATE pg_catalog."default",
    user_name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT users_table_pkey PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS public.hospital
(
    id bigint NOT NULL DEFAULT nextval('hospital_id_seq'::regclass),
    address character varying(255) COLLATE pg_catalog."default",
    email character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    phone character varying(255) COLLATE pg_catalog."default",
    website character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT hospital_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.doctor
(
    id bigint NOT NULL DEFAULT nextval('doctor_id_seq'::regclass),
    available_slots_count integer,
    email character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    phone character varying(255) COLLATE pg_catalog."default",
    version bigint,
    hospital_id bigint NOT NULL,
    CONSTRAINT doctor_pkey PRIMARY KEY (id),
    CONSTRAINT doctor_hospital_fk1 FOREIGN KEY (hospital_id)
    REFERENCES public.hospital (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS public.slots
(
    id bigint NOT NULL DEFAULT nextval('slots_id_seq'::regclass),
    end_time time without time zone,
    slot_description character varying(255) COLLATE pg_catalog."default",
    slot_name character varying(255) COLLATE pg_catalog."default",
    start_time time without time zone,
    doctor_id bigint NOT NULL,
    CONSTRAINT slots_pkey PRIMARY KEY (id),
    CONSTRAINT slots_doctor_fk1 FOREIGN KEY (doctor_id)
        REFERENCES public.doctor (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


CREATE TABLE IF NOT EXISTS public.appointment
(
    id bigint NOT NULL DEFAULT nextval('appointment_id_seq'::regclass),
    appointment_date date,
    version bigint,
    doctor_id bigint NOT NULL,
    hospital_id bigint NOT NULL,
    slots_id bigint NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT appointment_pkey PRIMARY KEY (id),
    CONSTRAINT appointment_hospital_fk FOREIGN KEY (hospital_id)
        REFERENCES public.hospital (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT appointment_user_fk FOREIGN KEY (user_id)
        REFERENCES public.users_table (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT appointment_slots_fk FOREIGN KEY (slots_id)
        REFERENCES public.slots (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT appointment_doctor_fk FOREIGN KEY (doctor_id)
        REFERENCES public.doctor (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

