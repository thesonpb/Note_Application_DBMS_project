drop database if exists `note_database_2`;
create database `note_database_2`;

use `note_database_2`;

drop table if exists `note`;

create table `note` (
	`idNote` int(11) not null auto_increment,
    `Ntitle` varchar(50) not null, 
    `Ntag` varchar(50) not null, 
    `Ncontent` text not null,
    `NdateCreated` date not null,
    primary key(`idNote`)
);

insert into `note`(`Ntitle`, `Ntag`, `Ncontent`, `NdateCreated`) values
('Note01', '1', 'this is the first note', '2021/03/24'),
('Note02', '2', 'this is the second note', '2021/03/20'),
('Note03', '3', 'this is the third note', '2021/03/21');

drop table if exists `mediaFile`;

create table `mediaFile` (
	`idMedia` int(11) not null auto_increment,
	`Mname` varchar(50) not null,
    `Msize` int(11) not null,
    `Mlink` varchar(50) not null,
    `Mtype` varchar(50) not null, 
    `idNote` int(11) not null,
    primary key(`idMedia`),
    constraint `fk_idNote_note` foreign key(`idNote`) references `note`(`idNote`)
);

insert into `mediaFile` (`Mname`, `Msize`, `Mlink`, `Mtype`, `idNote`) values
('image01', 100, 'F:\\folder1\\image01', 'image', 1), 
('audio01', 200, 'F:\\folder1\\audio01', 'audio', 1), 
('audio02', 200, 'F:\\folder2\\audio02', 'audio', 2), 
('image02', 300, 'F:\\folder2\\image02', 'image', 2),
('image03', 300, 'F:\\folder3\\image03', 'image', 4), 
('audio03', 300, 'F:\\folder3\\audio03', 'audio', 4);

select note.idNote, Ntitle, idMedia, Mlink, Mtype
from note
inner join mediaFile
on note.idNote = mediaFile.idNote;
