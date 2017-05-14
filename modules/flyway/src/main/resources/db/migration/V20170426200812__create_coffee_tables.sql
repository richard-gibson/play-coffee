create table COFFEE_BUSINESS (
  id int PRIMARY KEY auto_increment,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NOT NULL
);


create table COFFEE_OUTLET (
  id INT PRIMARY KEY auto_increment,
  business_id int NOT NULL,
  address VARCHAR(1024) NOT NULL,
  town VARCHAR(255) NOT NULL,
  postcode VARCHAR(255) NOT NULL,
  FOREIGN KEY (business_id) REFERENCES coffee_business(id)
);

