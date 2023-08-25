set global local_infile=1;


CREATE TABLE apttest (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         district VARCHAR(255),
                         address VARCHAR(255),
                         main_number INT,
                         sub_number INT,
                         complex_name VARCHAR(255),
                         exclusive_area DECIMAL(10, 2),
                         contract_year_month INT,
                         contract_date DATE,
                         transaction_amount INT,
                         floor INT,
                         construction_year INT,
                         road_name VARCHAR(255),
                         release_reason_date DATE,
                         registration_application_date DATE,
                         transaction_type VARCHAR(255),
                         agent_location VARCHAR(255)
);





LOAD DATA LOCAL INFILE 'C:/Users/user1/Downloads/aptt.csv'
INTO TABLE pos.apttest
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;

select * from apttest;