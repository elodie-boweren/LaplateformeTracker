--
-- PostgreSQL database dump
--

-- Dumped from database version 16.9 (Debian 16.9-1.pgdg120+1)
-- Dumped by pg_dump version 17.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: public; Type: SCHEMA; Schema: -; Owner: plateformetracker_user
--

-- *not* creating schema, since initdb creates it


ALTER SCHEMA public OWNER TO plateformetracker_user;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: student; Type: TABLE; Schema: public; Owner: plateformetracker_user
--

CREATE TABLE public.student (
    id integer NOT NULL,
    firstname character varying(100),
    lastname character varying(100),
    age integer,
    grade integer
);


ALTER TABLE public.student OWNER TO plateformetracker_user;

--
-- Name: student_id_seq; Type: SEQUENCE; Schema: public; Owner: plateformetracker_user
--

CREATE SEQUENCE public.student_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.student_id_seq OWNER TO plateformetracker_user;

--
-- Name: student_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: plateformetracker_user
--

ALTER SEQUENCE public.student_id_seq OWNED BY public.student.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: plateformetracker_user
--

CREATE TABLE public.users (
    id integer NOT NULL,
    email character varying(100) NOT NULL,
    password_hash text NOT NULL,
    role character varying(20) DEFAULT 'USER'::character varying
);


ALTER TABLE public.users OWNER TO plateformetracker_user;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: plateformetracker_user
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO plateformetracker_user;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: plateformetracker_user
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: student id; Type: DEFAULT; Schema: public; Owner: plateformetracker_user
--

ALTER TABLE ONLY public.student ALTER COLUMN id SET DEFAULT nextval('public.student_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: plateformetracker_user
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: student; Type: TABLE DATA; Schema: public; Owner: plateformetracker_user
--

COPY public.student (id, firstname, lastname, age, grade) FROM stdin;
1	Elodie	Boweren	25	18
2	Louis	Cordier	28	15
3	Laura	Iriskinian	23	17
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: plateformetracker_user
--

COPY public.users (id, email, password_hash, role) FROM stdin;
1	elodie@gmail.fr	$2a$10$usmoyp4Nhw0Kps1Riqq5AuNu6JslCRDIDvVawk6htx46eks8OJ2BK	user
2	louis@gmail.com	$2a$10$ISIwEFoJGk58wVjUFwTAgOZrLDJe9wXgmKyVa.ngdBhFld8a.ee6q	user
\.


--
-- Name: student_id_seq; Type: SEQUENCE SET; Schema: public; Owner: plateformetracker_user
--

SELECT pg_catalog.setval('public.student_id_seq', 3, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: plateformetracker_user
--

SELECT pg_catalog.setval('public.users_id_seq', 2, true);


--
-- Name: student student_pkey; Type: CONSTRAINT; Schema: public; Owner: plateformetracker_user
--

ALTER TABLE ONLY public.student
    ADD CONSTRAINT student_pkey PRIMARY KEY (id);


--
-- Name: users users_email_key; Type: CONSTRAINT; Schema: public; Owner: plateformetracker_user
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: plateformetracker_user
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: DEFAULT PRIVILEGES FOR SEQUENCES; Type: DEFAULT ACL; Schema: -; Owner: postgres
--

ALTER DEFAULT PRIVILEGES FOR ROLE postgres GRANT ALL ON SEQUENCES TO plateformetracker_user;


--
-- Name: DEFAULT PRIVILEGES FOR TYPES; Type: DEFAULT ACL; Schema: -; Owner: postgres
--

ALTER DEFAULT PRIVILEGES FOR ROLE postgres GRANT ALL ON TYPES TO plateformetracker_user;


--
-- Name: DEFAULT PRIVILEGES FOR FUNCTIONS; Type: DEFAULT ACL; Schema: -; Owner: postgres
--

ALTER DEFAULT PRIVILEGES FOR ROLE postgres GRANT ALL ON FUNCTIONS TO plateformetracker_user;


--
-- Name: DEFAULT PRIVILEGES FOR TABLES; Type: DEFAULT ACL; Schema: -; Owner: postgres
--

ALTER DEFAULT PRIVILEGES FOR ROLE postgres GRANT SELECT,INSERT,REFERENCES,DELETE,TRIGGER,TRUNCATE,UPDATE ON TABLES TO plateformetracker_user;


--
-- PostgreSQL database dump complete
--

