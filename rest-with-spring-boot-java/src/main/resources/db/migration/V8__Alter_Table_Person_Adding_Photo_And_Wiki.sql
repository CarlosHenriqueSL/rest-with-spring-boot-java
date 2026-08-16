-- Adds two more columns to table 'person': a url from the person's wikipedia profile, and a url for the person's photo

ALTER TABLE person
ADD COLUMN wikipedia_profile_url VARCHAR(255),
ADD COLUMN photo_url VARCHAR(255) DEFAULT 'https://raw.githubusercontent.com/leandrocgsi/rest-with-spring-boot-and-java-erudio/refs/heads/main/photos/00_some_person.jpg';