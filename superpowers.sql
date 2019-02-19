Drop Database if exists superhero_sightings;
Create Database if not exists superhero_sightings;
USE superhero_sightings;

Create table if not exists `organizations` (
	organizations_id int not null auto_increment,
    `name` varchar(40) not null,
    `description` varchar(40) not null,
    address varchar(40) not null,
    contact varchar(40) not null,
    Primary Key(organizations_id)
);

Create table if not exists `supers` (
	supers_id int not null auto_increment,
    `name` varchar(40) not null,
    bio varchar(40) not null,
    power varchar(40) not null,
    Primary Key(supers_id)
);

Create table if not exists `supers_organizations` (
	supersorganizations_id int not null auto_increment,
    organizations_id int not null,
    supers_id int not null,
    Primary Key(supersorganizations_id),
    Foreign Key(organizations_id) references `organizations`(organizations_id),
    Foreign Key(supers_id) references `supers`(supers_id)
);

Create table if not exists `locations` (
	locations_id int not null auto_increment,
    `name` varchar(40) not null,
    `description` varchar(40) not null,
    address varchar(40) not null,
    latitude decimal(4,2) not null,
    longitude decimal(5,2) not null,
    Primary Key(locations_id)
);

Create table if not exists `sightings` (
	sightings_id int not null auto_increment,
    locations_id int not null,
    `date` DATETIME,
    Primary Key(sightings_id),
    Foreign Key(locations_id) references `locations`(locations_id)
);

Create table if not exists `supers_sightings` (
	superssightings_id int not null auto_increment,
    supers_id int not null,
    sightings_id int not null,
    Primary Key(superssightings_id),
    Foreign Key(supers_id) references `supers`(supers_id),
    Foreign Key(sightings_id) references `sightings`(sightings_id)
);



