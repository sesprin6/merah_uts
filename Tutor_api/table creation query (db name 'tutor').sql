use tutor;

create table paket
(
	id int primary key auto_increment,
    paket varchar(50) not null
);

create table sekolah
(
	id int primary key auto_increment,
    nama varchar(100) not null,
    tempat varchar(100)
);

create table siswa
(
	id int primary key auto_increment,
    no_induk int unique not null,
    paket int not null,
    nama varchar(100) not null,
    j_kelamin enum('L', 'P') not null,
    tempat_lahir varchar(200),
    tanggal_lahir date,
    sekolah int not null,
    alamat varchar(300),
    nama_wali varchar(100),
    telp varchar(20),
    
    foreign key (paket) references paket (id),
    foreign key (sekolah) references sekolah (id)
);