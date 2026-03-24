INSERT INTO users (email, password, authorities, enabled)
VALUES
    ('adrian', '$2a$10$D6bl9N.02iMEu8Mm7jKfb.kWC398p8lCbNmmuTmMse1t9SqIoiveu', '{ROLE_ADMIN, ROLE_USER}', true),
    ('john', '12345', '{ROLE_USER}', true);