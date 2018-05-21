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

create table modules
(
    id                      UUID not null primary key,
    module_id               int,
    name                    varchar(200),
    course_description_id   UUID
);

create table course_descriptions
(
    id                      UUID not null primary key,
    course_id               varchar(200),
    title                   varchar(200),
    summary                 varchar(2000),
    teacher_id              varchar(200)
);

alter table modules
add foreign key (course_description_id)
references course_descriptions (id);

alter table learning_paths
add foreign key (module_id)
references modules (id);

alter table online_materials
add foreign key (learning_path_id) 
references learning_paths (id);
