


    alter table book 
       add column available bit

    alter table book 
       add column created_date datetime(6)

    alter table book 
       add column last_modified_date datetime(6)

    alter table book 
       add column created_user_id integer

    alter table book 
       add column modified_user_id integer

    alter table book 
       add constraint FKatg5r3k1eo62h94ikojx8q7xc 
       foreign key (created_user_id) 
       references user_credential (id)

    alter table book 
       add constraint FK7n4ho2s345hjc4alw87tmcpew 
       foreign key (modified_user_id) 
       references user_credential (id)

    alter table borrowing_records 
       add column created_date datetime(6)

    alter table borrowing_records 
       add column last_modified_date datetime(6)

    alter table borrowing_records 
       add column created_user_id integer

    alter table borrowing_records 
       add column modified_user_id integer

    alter table patron 
       add column created_date datetime(6)

    alter table patron 
       add column last_modified_date datetime(6)

    alter table patron 
       add column created_user_id integer

    alter table patron 
       add column modified_user_id integer

    alter table borrowing_records 
       add constraint FKgby1r6v8b3c6gwgwj4rjfpxrj 
       foreign key (created_user_id) 
       references user_credential (id)

    alter table borrowing_records 
       add constraint FKfmkgjkm5evnac6onv9o7mnuqy 
       foreign key (modified_user_id) 
       references user_credential (id)

    alter table patron 
       add constraint FKc9h5pnyq431pmm455u3rf9a6q 
       foreign key (created_user_id) 
       references user_credential (id)

    alter table patron 
       add constraint FKgllebujj3fglye4pero9723np 
       foreign key (modified_user_id) 
       references user_credential (id)

    alter table borrowing_records 
       add column created_date datetime(6)

    alter table borrowing_records 
       add column last_modified_date datetime(6)

    alter table borrowing_records 
       add column created_user_id integer

    alter table borrowing_records 
       add column modified_user_id integer

    alter table patron 
       add column created_date datetime(6)

    alter table patron 
       add column last_modified_date datetime(6)

    alter table patron 
       add column created_user_id integer

    alter table patron 
       add column modified_user_id integer

    alter table borrowing_records 
       add constraint FKgby1r6v8b3c6gwgwj4rjfpxrj 
       foreign key (created_user_id) 
       references user_credential (id)

    alter table borrowing_records 
       add constraint FKfmkgjkm5evnac6onv9o7mnuqy 
       foreign key (modified_user_id) 
       references user_credential (id)

    alter table patron 
       add constraint FKc9h5pnyq431pmm455u3rf9a6q 
       foreign key (created_user_id) 
       references user_credential (id)

    alter table patron 
       add constraint FKgllebujj3fglye4pero9723np 
       foreign key (modified_user_id) 
       references user_credential (id)
