-- 1. ENUM 타입을 테이블로 대체
USE saegil;
CREATE TABLE IF NOT EXISTS security_questions (
                                                  question_id INT PRIMARY KEY AUTO_INCREMENT,
                                                  question_text VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS member_types (
                                            type_id INT PRIMARY KEY AUTO_INCREMENT,
                                            type_name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS ingredient_statuses (
                                                   status_id INT PRIMARY KEY AUTO_INCREMENT,
                                                   status_name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS inquiry_statuses (
                                                status_id INT PRIMARY KEY AUTO_INCREMENT,
                                                status_name VARCHAR(50) UNIQUE NOT NULL
);

-- 초기 데이터 삽입
INSERT INTO member_types (type_name) VALUES ('Regular'), ('Login'), ('Admin');
INSERT INTO ingredient_statuses (status_name) VALUES ('Unused'), ('Partially Used'), ('Used');
INSERT INTO inquiry_statuses (status_name) VALUES ('Incomplete'), ('Complete');

-- 2. 주요 테이블
USE saegil;

CREATE TABLE IF NOT EXISTS major_categories (
                                                major_category_id INT PRIMARY KEY AUTO_INCREMENT,
                                                name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS sub_categories (
                                              sub_category_id INT PRIMARY KEY AUTO_INCREMENT,
                                              major_category_id INT NOT NULL,
                                              name VARCHAR(255) NOT NULL,
                                              FOREIGN KEY (major_category_id) REFERENCES major_categories(major_category_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS ingredient_images (
                                                 ingredient_image_id INT PRIMARY KEY AUTO_INCREMENT,
                                                 uuid VARCHAR(255) UNIQUE NOT NULL,
                                                 original_name VARCHAR(255),
                                                 file_name VARCHAR(255),
                                                 url VARCHAR(255),
                                                 upload_date DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS member_images (
                                             member_image_id INT PRIMARY KEY AUTO_INCREMENT,
                                             uuid VARCHAR(255) UNIQUE NOT NULL,
                                             original_name VARCHAR(255),
                                             file_name VARCHAR(255),
                                             url VARCHAR(255),
                                             upload_date DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS final_images (
                                            final_image_id INT PRIMARY KEY AUTO_INCREMENT,
                                            uuid VARCHAR(255) UNIQUE NOT NULL,
                                            original_name VARCHAR(255),
                                            file_name VARCHAR(255),
                                            url VARCHAR(255),
                                            upload_date DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS cooking_step_images (
                                                   cooking_step_image_id INT PRIMARY KEY AUTO_INCREMENT,
                                                   uuid VARCHAR(255) UNIQUE NOT NULL,
                                                   original_name VARCHAR(255),
                                                   file_name VARCHAR(255),
                                                   url VARCHAR(255),
                                                   upload_date DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS notice_images (
                                             notice_image_id INT PRIMARY KEY AUTO_INCREMENT,
                                             uuid VARCHAR(255) UNIQUE NOT NULL,
                                             original_name VARCHAR(255),
                                             file_name VARCHAR(255),
                                             url VARCHAR(255),
                                             upload_date DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS banner_images (
                                             banner_image_id INT PRIMARY KEY AUTO_INCREMENT,
                                             uuid VARCHAR(255) UNIQUE NOT NULL,
                                             original_name VARCHAR(255),
                                             file_name VARCHAR(255),
                                             url VARCHAR(255),
                                             upload_date DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS ingredients (
                                           ingredient_id INT PRIMARY KEY AUTO_INCREMENT,
                                           sub_category_id INT NOT NULL,
                                           name VARCHAR(255) NOT NULL,
                                           ingredient_image_id INT,
                                           FOREIGN KEY (sub_category_id) REFERENCES sub_categories(sub_category_id) ON DELETE CASCADE,
                                           FOREIGN KEY (ingredient_image_id) REFERENCES ingredient_images(ingredient_image_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS members (
                                       member_id INT PRIMARY KEY AUTO_INCREMENT,
                                       email VARCHAR(255) UNIQUE NOT NULL,
                                       password VARCHAR(255) NOT NULL,
                                       nickname VARCHAR(255) NOT NULL,
                                       profile_picture_id INT,
                                       security_question_id INT NOT NULL,
                                       member_type_id INT NOT NULL,
                                       join_date DATETIME NOT NULL,
                                       last_access_date DATETIME,
                                       FOREIGN KEY (profile_picture_id) REFERENCES member_images(member_image_id) ON DELETE SET NULL,
                                       FOREIGN KEY (security_question_id) REFERENCES security_questions(question_id),
                                       FOREIGN KEY (member_type_id) REFERENCES member_types(type_id)
);

CREATE TABLE IF NOT EXISTS recipes (
                                       recipe_id INT PRIMARY KEY AUTO_INCREMENT,
                                       author_id INT NOT NULL,
                                       description TEXT NOT NULL,
                                       cooking_time INT,
                                       servings INT,
                                       created_at DATETIME NOT NULL,
                                       views INT DEFAULT 0 NOT NULL,
                                       rating FLOAT DEFAULT 0,
                                       bookmarks_count INT DEFAULT 0,
                                       final_image_id INT,
                                       cooking_process_video_link TEXT,
                                       FOREIGN KEY (author_id) REFERENCES members(member_id) ON DELETE CASCADE,
                                       FOREIGN KEY (final_image_id) REFERENCES final_images(final_image_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS cooking_steps (
                                             step_id INT PRIMARY KEY AUTO_INCREMENT,
                                             recipe_id INT NOT NULL,
                                             description TEXT NOT NULL,
                                             step_image_id INT,
                                             step_number INT NOT NULL,
                                             FOREIGN KEY (recipe_id) REFERENCES recipes(recipe_id) ON DELETE CASCADE,
                                             FOREIGN KEY (step_image_id) REFERENCES cooking_step_images(cooking_step_image_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS comments (
                                        comment_id INT PRIMARY KEY AUTO_INCREMENT,
                                        recipe_id INT NOT NULL,
                                        author_id INT NOT NULL,
                                        content TEXT NOT NULL,
                                        created_at DATETIME NOT NULL,
                                        FOREIGN KEY (recipe_id) REFERENCES recipes(recipe_id) ON DELETE CASCADE,
                                        FOREIGN KEY (author_id) REFERENCES members(member_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS notices (
                                       notice_id INT PRIMARY KEY AUTO_INCREMENT,
                                       member_id INT NOT NULL,
                                       title VARCHAR(255) NOT NULL,
                                       content TEXT NOT NULL,
                                       notice_image_id INT,
                                       created_at DATETIME NOT NULL,
                                       FOREIGN KEY (member_id) REFERENCES members(member_id) ON DELETE CASCADE,
                                       FOREIGN KEY (notice_image_id) REFERENCES notice_images(notice_image_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS banners (
                                       banner_id INT PRIMARY KEY AUTO_INCREMENT,
                                       member_id INT NOT NULL,
                                       notice_id INT NOT NULL,
                                       banner_image_id INT,
                                       created_at DATETIME NOT NULL,
                                       FOREIGN KEY (member_id) REFERENCES members(member_id) ON DELETE CASCADE,
                                       FOREIGN KEY (notice_id) REFERENCES notices(notice_id) ON DELETE CASCADE,
                                       FOREIGN KEY (banner_image_id) REFERENCES banner_images(banner_image_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS refrigerator_ingredients (
                                                        ingredient_id INT NOT NULL,
                                                        member_id INT NOT NULL,
                                                        quantity VARCHAR(255) NOT NULL,
                                                        status_id INT NOT NULL,
                                                        expiration_date DATETIME,
                                                        PRIMARY KEY (ingredient_id, member_id),
                                                        FOREIGN KEY (ingredient_id) REFERENCES ingredients(ingredient_id) ON DELETE CASCADE,
                                                        FOREIGN KEY (member_id) REFERENCES members(member_id) ON DELETE CASCADE,
                                                        FOREIGN KEY (status_id) REFERENCES ingredient_statuses(status_id)
);

CREATE TABLE IF NOT EXISTS recipe_ingredients (
                                                  ingredient_id INT NOT NULL,
                                                  recipe_id INT NOT NULL,
                                                  ingredient_name VARCHAR(255) NOT NULL,
                                                  quantity VARCHAR(255),
                                                  PRIMARY KEY (ingredient_id, recipe_id),
                                                  FOREIGN KEY (ingredient_id) REFERENCES ingredients(ingredient_id) ON DELETE CASCADE,
                                                  FOREIGN KEY (recipe_id) REFERENCES recipes(recipe_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS inquiries (
                                         inquiry_id INT PRIMARY KEY AUTO_INCREMENT,
                                         member_id INT NOT NULL,
                                         content TEXT NOT NULL,
                                         created_at DATETIME NOT NULL,
                                         status_id INT NOT NULL,
                                         FOREIGN KEY (member_id) REFERENCES members(member_id) ON DELETE CASCADE,
                                         FOREIGN KEY (status_id) REFERENCES inquiry_statuses(status_id)
);

CREATE TABLE IF NOT EXISTS bookmarks (
                                         bookmark_id INT PRIMARY KEY AUTO_INCREMENT,
                                         member_id INT NOT NULL,
                                         recipe_id INT NOT NULL,
                                         saved_at DATETIME NOT NULL,
                                         FOREIGN KEY (member_id) REFERENCES members(member_id) ON DELETE CASCADE,
                                         FOREIGN KEY (recipe_id) REFERENCES recipes(recipe_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS comment_reports (
                                               report_id INT PRIMARY KEY AUTO_INCREMENT,
                                               reporter_member_id INT NOT NULL,
                                               comment_id INT NOT NULL,
                                               report_title TEXT NOT NULL,
                                               report_content TEXT NOT NULL,
                                               reported_at DATETIME NOT NULL,
                                               FOREIGN KEY (reporter_member_id) REFERENCES members(member_id) ON DELETE CASCADE,
                                               FOREIGN KEY (comment_id) REFERENCES comments(comment_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS recipe_reports (
                                              report_id INT PRIMARY KEY AUTO_INCREMENT,
                                              reporter_member_id INT NOT NULL,
                                              recipe_id INT NOT NULL,
                                              report_title TEXT NOT NULL,
                                              report_content TEXT NOT NULL,
                                              reported_at DATETIME NOT NULL,
                                              FOREIGN KEY (reporter_member_id) REFERENCES members(member_id) ON DELETE CASCADE,
                                              FOREIGN KEY (recipe_id) REFERENCES recipes(recipe_id) ON DELETE CASCADE
);
