-- Ensure `password` column exists in `user` table
ALTER TABLE `user` ADD COLUMN IF NOT EXISTS password VARCHAR(255) NOT NULL;

-- Create `role` table
CREATE TABLE IF NOT EXISTS role (
                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                    name VARCHAR(50) UNIQUE NOT NULL
    );

-- Insert default roles (Ensure they are added only if they don't already exist)
INSERT INTO role (id, name)
SELECT 1, 'ROLE_ADMIN' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM role WHERE id = 1);
INSERT INTO role (id, name)
SELECT 2, 'ROLE_USER' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM role WHERE id = 2);

-- Create Many-to-Many Relationship Table (User_Roles)
CREATE TABLE IF NOT EXISTS user_roles (
                                          user_id INT NOT NULL,
                                          role_id INT NOT NULL,
                                          PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES `user`(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
    );
