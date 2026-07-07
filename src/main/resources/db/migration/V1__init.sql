CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       nickname VARCHAR(50) NOT NULL,
                       email VARCHAR(100),
                       profile_image_url VARCHAR(500),

                       created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       deleted_at DATETIME NULL,

                       UNIQUE KEY uk_users_email (email),
                       KEY idx_users_deleted_at (deleted_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE auth (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      user_id BIGINT NOT NULL,
                      provider VARCHAR(20) NOT NULL,
                      provider_user_id VARCHAR(100),
                      password_hash VARCHAR(255),

                      created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      deleted_at DATETIME NULL,

                      UNIQUE KEY uk_auth_provider_user (provider, provider_user_id),
                      KEY idx_auth_user_id (user_id),
                      KEY idx_auth_deleted_at (deleted_at),

                      CONSTRAINT fk_auth_user
                          FOREIGN KEY (user_id) REFERENCES users(id),

                      CONSTRAINT chk_auth_provider
                          CHECK (provider IN ('LOCAL', 'KAKAO'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE daily_records (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               user_id BIGINT NOT NULL,
                               record_date DATE NOT NULL,
                               content TEXT NOT NULL,
                               mood VARCHAR(30),
                               mood_tags JSON,
                               activity_tags JSON,

                               created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               deleted_at DATETIME NULL,

                               UNIQUE KEY uk_daily_record_user_date (user_id, record_date),
                               KEY idx_daily_records_user_date (user_id, record_date),
                               KEY idx_daily_records_deleted_at (deleted_at),

                               CONSTRAINT fk_daily_records_user
                                   FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE ai_analysis_logs (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  daily_record_id BIGINT NOT NULL,
                                  provider VARCHAR(30) NOT NULL,
                                  model VARCHAR(100),
                                  prompt TEXT,
                                  raw_response JSON,
                                  status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
                                  error_message TEXT,

                                  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                  deleted_at DATETIME NULL,

                                  KEY idx_ai_analysis_logs_daily_record_id (daily_record_id),
                                  KEY idx_ai_analysis_logs_status (status),
                                  KEY idx_ai_analysis_logs_deleted_at (deleted_at),

                                  CONSTRAINT fk_ai_analysis_logs_daily_record
                                      FOREIGN KEY (daily_record_id) REFERENCES daily_records(id),

                                  CONSTRAINT chk_ai_analysis_status
                                      CHECK (status IN ('PENDING', 'SUCCESS', 'FAILED'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE casting_images (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                image_url VARCHAR(500) NOT NULL,
                                title VARCHAR(100),
                                genre VARCHAR(50),
                                mood VARCHAR(50),
                                keywords JSON,

                                created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                deleted_at DATETIME NULL,

                                KEY idx_casting_images_genre_mood (genre, mood),
                                KEY idx_casting_images_deleted_at (deleted_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE casting_cards (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               daily_record_id BIGINT NOT NULL,
                               casting_image_id BIGINT,
                               title VARCHAR(100) NOT NULL,
                               subtitle VARCHAR(100),
                               genre VARCHAR(50) NOT NULL,
                               role_name VARCHAR(100) NOT NULL,
                               highlight TEXT,
                               one_line_comment TEXT,
                               score INT,
                               analysis_summary TEXT,
                               is_favorite BOOLEAN NOT NULL DEFAULT FALSE,
                               generated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                               created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               deleted_at DATETIME NULL,

                               UNIQUE KEY uk_casting_card_daily_record (daily_record_id),
                               KEY idx_casting_cards_image_id (casting_image_id),
                               KEY idx_casting_cards_favorite (is_favorite),
                               KEY idx_casting_cards_deleted_at (deleted_at),

                               CONSTRAINT fk_casting_cards_daily_record
                                   FOREIGN KEY (daily_record_id) REFERENCES daily_records(id),

                               CONSTRAINT fk_casting_cards_image
                                   FOREIGN KEY (casting_image_id) REFERENCES casting_images(id)
                                       ON DELETE SET NULL,

                               CONSTRAINT chk_casting_score
                                   CHECK (score IS NULL OR score BETWEEN 0 AND 100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE user_settings (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               user_id BIGINT NOT NULL,
                               push_enabled BOOLEAN NOT NULL DEFAULT TRUE,
                               daily_reminder_enabled BOOLEAN NOT NULL DEFAULT FALSE,
                               daily_reminder_time TIME,

                               created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               deleted_at DATETIME NULL,

                               UNIQUE KEY uk_user_settings_user (user_id),
                               KEY idx_user_settings_deleted_at (deleted_at),

                               CONSTRAINT fk_user_settings_user
                                   FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;