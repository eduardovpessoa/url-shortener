DROP DATABASE IF EXISTS url_shortener;
CREATE DATABASE url_shortener
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;