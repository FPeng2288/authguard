-- Insert base role
INSERT INTO roles (name) VALUES
                             ('ROLE_ADMIN'),
                             ('ROLE_USER');

-- Insert test user (password: test123)
INSERT INTO users (username, email, password) VALUES
                                                  ('admin', 'admin@example.com', '$2a$10$JgvOMZ1U/vqYSBAlc0YRXeEMyVGIXc7UgacZ2Nsm2HOunzBrJ1Sqi'),
                                                  ('user1', 'user1@example.com', '$2a$10$WZ5MW0L6VLvTBKYSsEdpLOMqxeMsXbI3bvootpZ8n/JQfPTC37Q72');

-- Grant roles
INSERT INTO user_roles (user_id, role_id, granted_by)
SELECT u.id, r.id, 'SYSTEM'
FROM users u
         CROSS JOIN roles r
WHERE u.username = 'admin' AND r.name IN ('ROLE_ADMIN', 'ROLE_USER');

INSERT INTO user_roles (user_id, role_id, granted_by)
SELECT u.id, r.id, 'SYSTEM'
FROM users u
         CROSS JOIN roles r
WHERE u.username = 'user1' AND r.name = 'ROLE_USER';