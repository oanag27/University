CREATE TABLE documents (
    id INT AUTO_INCREMENT PRIMARY KEY,
    author VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    num_pages INT NOT NULL,
    type VARCHAR(50) NOT NULL,
    format VARCHAR(50) NOT NULL
);
