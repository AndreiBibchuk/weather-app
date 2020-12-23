CREATE TABLE weather_data(
    id bigserial not null,
    temperature double precision,
    location varchar (100),
    timestamp timestamp without time zone not null,
    observatory varchar (50),
    CONSTRAINT pk_weather_data PRIMARY KEY (id)
);
