/* 
 * The MIT License
 *
 * Copyright 2018 André H. Juffer, Biocenter Oulu
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
/**
 * Author:  ajuffer
 * Created: Mar 27, 2018
 */

create table choices (
    id                              UUID not null primary key,
    phrase                          varchar(200),
    multiple_choice_question_id     UUID
);

create table multiple_choice_questions (
    id                      UUID not null primary key,
    quiz_id                 UUID,
    answer                  varchar(200),
    question                varchar(200)
);

create table module_items (
    id                      UUID not null primary key,
    discriminator           varchar(200),
    module_id               UUID,
    module_item_id          int,
    title                   varchar(200),
    content                 varchar(3000),
    simulator               varchar(200)
);

/* DEPRECATED
create table online_materials
(
    id                      UUID not null primary key,
    material_id             int,
    learning_path_id        UUID,
    content                 varchar(3000)
);

create table learning_paths
(
    id                      UUID not null primary key,
    module_id               UUID
);
*/

create table modules
(
    id                      UUID not null primary key,
    module_id               int,
    name                    varchar(200),
    course_description_id   UUID,
    course_id               UUID
);

create table course_descriptions
(
    id                      UUID not null primary key,
    course_description_id   varchar(200),
    title                   varchar(200),
    summary                 varchar(2000),
    teacher_id              varchar(200)
);

create table student_monitors
(
    id                      UUID not null primary key,
    monitor_id              int,
    student_id              varchar(200),
    current_module_id       int,
    current_module_item_id  int,
    complete                boolean
);

create table rosters
(
    monitor_id              UUID,
    course_id               UUID
);

create table courses
(
    id                      UUID not null primary key,
    course_id               varchar(200),
    course_description_id   varchar(200),
    title                   varchar(200),
    summary                 varchar(2000),
    teacher_id              varchar(200),
    number_of_seats         int,
    ongoing                 boolean,
    start_date              bigint,
    end_date                bigint
);

alter table choices
add foreign key (multiple_choice_question_id)
references multiple_choice_questions (id);

alter table multiple_choice_questions
add foreign key (quiz_id)
references module_items (id);

alter table module_items
add foreign key (module_id)
references modules (id);

alter table modules
add foreign key (course_description_id)
references course_descriptions (id);

alter table modules
add foreign key (course_id)
references courses (id);

/* DEPRECATED
alter table learning_paths
add foreign key (module_id)
references modules (id);

alter table online_materials
add foreign key (learning_path_id) 
references learning_paths (id);
*/

alter table rosters
add primary key (monitor_id, course_id);

alter table rosters
add foreign key (monitor_id) 
references student_monitors(id);

alter table rosters
add foreign key (course_id)
references courses (id);
