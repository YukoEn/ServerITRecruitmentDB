-- data.sql
INSERT INTO category(category_name) VALUES
('Software/ Application Developer'),
('Web Developer'),
('Information Security Specialist'),
('Information Technology Analyst'),
('Information Technology Lead'),
('Database Administrator'),
('Computer Support Specialist'),
('Computer Network Specialist'),
('Cloud Computing Engineer');

INSERT INTO candidate(first_name, last_name, streetaddress, postcode, postoffice, country, phone, email, category_id) VALUES
('John', 'West', 'Kuusikuja 6', '01200', 'Vantaa', 'Finland', '0449212278', 'jwest@yahoo.com', 2),
('Kate', 'Johnson', 'Keilaranta 1', '02150', 'Espoo', 'Finland', '04077882322', 'kjohnson@kotimaa.fi', 6),
('Mike', 'Mars', 'Asematori 3', '00520', 'Helsinki', 'Finland', '0909954367', 'marsms@gmail.com', 4);

INSERT INTO skill(skill_name) VALUES
('Software development skills'),
('Problem solving skills'),
('Experience on C++'),
('Experience on Python'),
('Experience on Linux'),
('Knowledge of cyber security');

INSERT INTO skill_candidate(skill_id, candidate_id, skill_level) VALUES
(1, 1, 'good'),
(2, 1, 'excellent'),
(3, 1, 'satisfactory'),
(4, 1, 'no experience'),
(5, 1, 'good'),
(6, 1, 'no experience'),
(1, 2, 'excellent'),
(2, 2, 'good'),
(3, 2, 'excellent'),
(4, 2, 'excellent'),
(5, 2, 'excellent'),
(6, 2, 'some experience'),
(1, 3, 'satisfactory'),
(2, 3, 'good'),
(3, 3, 'satisfactory'),
(4, 3, 'no experience'),
(5, 3, 'no experiene'),
(6, 3, 'excellent');

INSERT INTO user (username, password, role) VALUES
('user', '$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6', 'USER'),
('admin', '$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C', 'ADMIN');
	