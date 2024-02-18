package com.labi.schedulerjava;

import com.labi.schedulerjava.adapters.persistence.MinistryRepository;
import com.labi.schedulerjava.adapters.persistence.UserRepository;
import com.labi.schedulerjava.adapters.persistence.VolunteerMinistryRepository;
import com.labi.schedulerjava.adapters.persistence.VolunteerRepository;
import com.labi.schedulerjava.core.domain.model.Ministry;
import com.labi.schedulerjava.core.domain.model.User;
import com.labi.schedulerjava.core.domain.model.Volunteer;
import com.labi.schedulerjava.core.domain.model.VolunteerMinistry;
import com.labi.schedulerjava.core.domain.service.UserMinistryService;
import com.labi.schedulerjava.enums.VolunteerOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class SchedulerJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedulerJavaApplication.class, args);
	}

	@Autowired
	private MinistryRepository ministryRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserMinistryService userMinistryService;

	@Autowired
	private VolunteerRepository volunteerRepository;

	@Autowired
	private VolunteerMinistryRepository volunteerMinistryRepository;

	@Bean
	public Object createMinistry() {
		Ministry ministry = new Ministry("CEIA", "Ceia principal", "#494949");
		Ministry ministry2 = new Ministry("LOUVOR", "Louvor principal", "#AC2020");
		Ministry ministry3 = new Ministry("DANÇA", "Dança principal", "#6F43C5");
		Ministry ministry4 = new Ministry("TEATRO", "Teatro principal", "#2FAC95");
		Ministry ministry5 = new Ministry("CRIANÇAS", "Crianças principal", "#BB1386");
		Ministry ministry6 = new Ministry("ADOLESCENTES", "Adolescentes principal", "#5C8CDE");
		ministryRepository.saveAll(List.of(ministry, ministry2, ministry3, ministry4, ministry5, ministry6));

		Volunteer volunteer1 = new Volunteer("Tiago", "Silveira", "11642426954", "48998533335", LocalDate.parse("2003-02-26"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer2 = new Volunteer("Mariana", "Costa", "12345678901", "47999223344", LocalDate.parse("1995-07-19"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer3 = new Volunteer("Carlos", "Mendes", "23456789012", "47998877665", LocalDate.parse("1988-11-30"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer4 = new Volunteer("Julia", "Barbosa", "34567890123", "47997766554", LocalDate.parse("1992-05-15"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer5 = new Volunteer("Lucas", "Silva", "45678901234", "47996655443", LocalDate.parse("1998-09-10"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer6 = new Volunteer("Maria", "Santos", "56789012345", "47995544332", LocalDate.parse("2000-12-25"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer7 = new Volunteer("João", "Oliveira", "67890123456", "47994433221", LocalDate.parse("1990-03-05"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer8 = new Volunteer("Ana", "Pereira", "78901234567", "47993322110", LocalDate.parse("1996-06-20"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer9 = new Volunteer("Pedro", "Ribeiro", "89012345678", "47992211009", LocalDate.parse("1994-01-15"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer10 = new Volunteer("Paula", "Ferreira", "90123456789", "47991100998", LocalDate.parse("1999-04-30"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer11 = new Volunteer("Miguel", "Santana", "01234567890", "47990099887", LocalDate.parse("1997-08-15"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer12 = new Volunteer("Larissa", "Oliveira", "12345678901", "47998877665", LocalDate.parse("1993-12-30"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer13 = new Volunteer("Rafael", "Pereira", "23456789012", "47997766554", LocalDate.parse("1991-05-15"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer14 = new Volunteer("Gabriela", "Silva", "34567890123", "47996655443", LocalDate.parse("1995-09-10"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer15 = new Volunteer("Fernando", "Santos", "45678901234", "47995544332", LocalDate.parse("2001-12-25"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer16 = new Volunteer("Isabela", "Oliveira", "56789012345", "47994433221", LocalDate.parse("1989-03-05"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer17 = new Volunteer("Ricardo", "Pereira", "67890123456", "47993322110", LocalDate.parse("1992-06-20"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer18 = new Volunteer("Larissa", "Ribeiro", "78901234567", "47992211009", LocalDate.parse("1998-01-15"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer19 = new Volunteer("Rafael", "Ferreira", "89012345678", "47991100998", LocalDate.parse("1993-04-30"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer20 = new Volunteer("Gabriela", "Santana", "90123456789", "47990099887", LocalDate.parse("1994-08-15"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer21 = new Volunteer("Bruno", "Machado", "11223344556", "47988997766", LocalDate.parse("1990-07-10"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer22 = new Volunteer("Carla", "Dias", "22334455667", "47987886655", LocalDate.parse("1987-08-25"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer23 = new Volunteer("Daniel", "Nunes", "33445566778", "47986775544", LocalDate.parse("1985-09-15"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer24 = new Volunteer("Eduarda", "Sousa", "44556677889", "47985664433", LocalDate.parse("1992-10-05"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer25 = new Volunteer("Felipe", "Martins", "55667788990", "47984553322", LocalDate.parse("1988-11-20"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer26 = new Volunteer("Gisele", "Barros", "66778899001", "47983442211", LocalDate.parse("1991-12-10"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer27 = new Volunteer("Hugo", "Lima", "77889900112", "47982331100", LocalDate.parse("1986-01-30"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer28 = new Volunteer("Iris", "Fernandes", "88990011223", "47981220099", LocalDate.parse("1993-02-25"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer29 = new Volunteer("Joel", "Correia", "99001122334", "47980119988", LocalDate.parse("1994-03-15"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer30 = new Volunteer("Kátia", "Mendes", "00112233445", "47979008877", LocalDate.parse("1996-04-10"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer31 = new Volunteer("Leonardo", "Rocha", "11223344556", "47977997766", LocalDate.parse("1997-05-20"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer32 = new Volunteer("Mônica", "Alves", "22334455667", "47976886655", LocalDate.parse("1999-06-15"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer33 = new Volunteer("Nelson", "Cardoso", "33445566778", "47975775544", LocalDate.parse("2002-07-25"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer34 = new Volunteer("Olívia", "Pinto", "44556677889", "47974664433", LocalDate.parse("2003-08-30"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer35 = new Volunteer("Paulo", "Silveira", "55667788990", "47973553322", LocalDate.parse("2004-09-10"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer36 = new Volunteer("Renata", "Brito", "66778899001", "47972442211", LocalDate.parse("2005-10-05"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer37 = new Volunteer("Sérgio", "Vieira", "77889900112", "47971331100", LocalDate.parse("2006-11-15"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer38 = new Volunteer("Tânia", "Costa", "88990011223", "47970220099", LocalDate.parse("2007-12-20"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer39 = new Volunteer("Ubiratan", "Santos", "99001122334", "47969119988", LocalDate.parse("2008-01-30"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer40 = new Volunteer("Vanessa", "Lopes", "00112233445", "47968008877", LocalDate.parse("2009-02-10"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer41 = new Volunteer("William", "Gomes", "10203040506", "47966997766", LocalDate.parse("1990-10-20"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer42 = new Volunteer("Ximena", "Rodrigues", "20304050607", "47965886655", LocalDate.parse("1989-09-15"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer43 = new Volunteer("Yasmin", "Freitas", "30405060708", "47964775544", LocalDate.parse("1992-08-10"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer44 = new Volunteer("Zacarias", "Moura", "40506070809", "47963664433", LocalDate.parse("1993-07-05"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer45 = new Volunteer("Aline", "Castro", "50607080910", "47962553322", LocalDate.parse("1995-06-25"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer46 = new Volunteer("Bruno", "Ferreira", "60708090111", "47961442211", LocalDate.parse("1994-05-15"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer47 = new Volunteer("Cecília", "Ramos", "70809010212", "47960331100", LocalDate.parse("1997-04-30"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer48 = new Volunteer("Diego", "Silva", "80901020313", "47959220099", LocalDate.parse("1996-03-20"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer49 = new Volunteer("Eliane", "Souza", "90102030414", "47958119988", LocalDate.parse("1998-02-10"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer50 = new Volunteer("Fábio", "Pereira", "01203040515", "47957008877", LocalDate.parse("1999-01-05"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer51 = new Volunteer("Giovanna", "Barbosa", "12304050616", "47955997766", LocalDate.parse("2001-12-25"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer52 = new Volunteer("Hélio", "Gonçalves", "23405060717", "47954886655", LocalDate.parse("2000-11-15"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer53 = new Volunteer("Isadora", "Lima", "34506070818", "47953775544", LocalDate.parse("2002-10-05"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer54 = new Volunteer("Jorge", "Santana", "45607080919", "47952664433", LocalDate.parse("2003-09-20"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer55 = new Volunteer("Karina", "Menezes", "56708090120", "47951553322", LocalDate.parse("2004-08-10"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer56 = new Volunteer("Leandro", "Nascimento", "67809010221", "47950442211", LocalDate.parse("2005-07-30"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer57 = new Volunteer("Mirella", "Carvalho", "78901020322", "47949331100", LocalDate.parse("2006-06-15"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer58 = new Volunteer("Nícolas", "Borges", "89010203023", "47948220099", LocalDate.parse("2007-05-05"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer59 = new Volunteer("Olga", "Dias", "90102030424", "47947119988", LocalDate.parse("2008-04-25"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer60 = new Volunteer("Patrícia", "Fonseca", "01203040525", "47946008877", LocalDate.parse("2009-03-10"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer61 = new Volunteer("Roberto", "Guimarães", "12304050626", "47944997766", LocalDate.parse("2010-02-20"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer62 = new Volunteer("Samanta", "Henrique", "23405060727", "47943886655", LocalDate.parse("2011-01-10"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer63 = new Volunteer("Tiago", "Alves", "34506070828", "47942775544", LocalDate.parse("2012-12-05"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer64 = new Volunteer("Úrsula", "Martins", "45607080929", "47941664433", LocalDate.parse("2013-11-25"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer65 = new Volunteer("Vitor", "Rocha", "56708090130", "47940553322", LocalDate.parse("2014-10-15"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer66 = new Volunteer("Wesley", "Correia", "67809010231", "47939442211", LocalDate.parse("2015-09-30"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer67 = new Volunteer("Xuxa", "Silveira", "78901020332", "47938331100", LocalDate.parse("2016-08-20"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer68 = new Volunteer("Yuri", "Melo", "89010203033", "47937220099", LocalDate.parse("2017-07-10"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer69 = new Volunteer("Zilda", "Barros", "90102030434", "47936119988", LocalDate.parse("2018-06-05"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer70 = new Volunteer("Alan", "Figueiredo", "01203040535", "47935008877", LocalDate.parse("2019-05-25"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer71 = new Volunteer("Bianca", "Garcia", "12304050636", "47933997766", LocalDate.parse("2020-04-15"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer72 = new Volunteer("César", "Lopes", "23405060737", "47932886655", LocalDate.parse("2021-03-30"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer73 = new Volunteer("Daniela", "Morais", "34506070838", "47931775544", LocalDate.parse("2022-02-20"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer74 = new Volunteer("Eduardo", "Nunes", "45607080939", "47930664433", LocalDate.parse("2023-01-10"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer75 = new Volunteer("Flávia", "Oliveira", "56708090140", "47929553322", LocalDate.parse("2024-12-05"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer76 = new Volunteer("Gustavo", "Pinto", "67809010241", "47928442211", LocalDate.parse("2025-11-25"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer77 = new Volunteer("Helena", "Queiroz", "78901020342", "47927331100", LocalDate.parse("2026-10-15"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer78 = new Volunteer("Igor", "Ribeiro", "89010203043", "47926220099", LocalDate.parse("2027-09-30"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer79 = new Volunteer("Júlia", "Santos", "90102030444", "47925119988", LocalDate.parse("2028-08-20"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer80 = new Volunteer("Kleber", "Torres", "01203040545", "47924008877", LocalDate.parse("2029-07-10"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer81 = new Volunteer("Lívia", "Vieira", "12304050646", "47922997766", LocalDate.parse("2030-06-05"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer82 = new Volunteer("Marcelo", "Xavier", "23405060747", "47921886655", LocalDate.parse("2031-05-25"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer83 = new Volunteer("Nívea", "Zanetti", "34506070848", "47920775544", LocalDate.parse("2032-04-15"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer84 = new Volunteer("Otávio", "Araújo", "45607080949", "47919664433", LocalDate.parse("2033-03-30"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer85 = new Volunteer("Patrícia", "Brito", "56708090150", "47918553322", LocalDate.parse("2034-02-20"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer86 = new Volunteer("Quirino", "Costa", "67809010251", "47917442211", LocalDate.parse("2035-01-10"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer87 = new Volunteer("Rafaela", "Dutra", "78901020352", "47916331100", LocalDate.parse("2036-12-05"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer88 = new Volunteer("Sandro", "Esteves", "89010203053", "47915220099", LocalDate.parse("2037-11-25"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer89 = new Volunteer("Tânia", "Fernandes", "90102030454", "47914119988", LocalDate.parse("2038-10-15"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer90 = new Volunteer("Umberto", "Gomes", "01203040555", "47913008877", LocalDate.parse("2039-09-30"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer91 = new Volunteer("William", "Morais", "11122233344", "47966997766", LocalDate.parse("1990-04-15"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer92 = new Volunteer("Ximena", "Araújo", "22233344455", "47965886655", LocalDate.parse("1989-05-20"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer93 = new Volunteer("Yasmin", "Braga", "33344455566", "47964775544", LocalDate.parse("1992-06-25"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer94= new Volunteer("Zélio", "Campos", "44455566677", "47963664433", LocalDate.parse("1993-07-30"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer95 = new Volunteer("Aline", "Duarte", "55566677788", "47962553322", LocalDate.parse("1994-08-05"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer96 = new Volunteer("Bruno", "Esteves", "66677788899", "47961442211", LocalDate.parse("1995-09-10"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer97 = new Volunteer("Carina", "Figueiredo", "77788899900", "47960331100", LocalDate.parse("1996-10-15"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer98 = new Volunteer("Diego", "Gomes", "88899900011", "47959220099", LocalDate.parse("1997-11-20"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer99 = new Volunteer("Elisa", "Henrique", "99900011122", "47958119988", LocalDate.parse("1998-12-25"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer100 = new Volunteer("Fábio", "Inácio", "00011122233", "47957008877", LocalDate.parse("1999-01-30"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer101 = new Volunteer("Giovana", "Jardim", "11122233344", "47955997766", LocalDate.parse("2000-02-15"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer102 = new Volunteer("Heitor", "Krause", "22233344455", "47954886655", LocalDate.parse("2001-03-20"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer103 = new Volunteer("Íris", "Lacerda", "33344455566", "47953775544", LocalDate.parse("2002-04-25"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer104 = new Volunteer("Júlio", "Macedo", "44455566677", "47952664433", LocalDate.parse("2003-05-30"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer105 = new Volunteer("Karina", "Nascimento", "55566677788", "47951553322", LocalDate.parse("2004-06-05"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer106 = new Volunteer("Leandro", "Oliveira", "66677788899", "47950442211", LocalDate.parse("2005-07-10"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer107 = new Volunteer("Márcia", "Pereira", "77788899900", "47949331100", LocalDate.parse("2006-08-15"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer108 = new Volunteer("Nícolas", "Queiroz", "88899900011", "47948220099", LocalDate.parse("2007-09-20"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer109 = new Volunteer("Olga", "Ribeiro", "99900011122", "47947119988", LocalDate.parse("2008-10-25"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer110 = new Volunteer("Pablo", "Silveira", "00011122233", "47946008877", LocalDate.parse("2009-11-30"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer111 = new Volunteer("Carla", "Mendes", "11223344556", "47944997766", LocalDate.parse("1990-01-15"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer112 = new Volunteer("Daniel", "Costa", "22334455667", "47943886655", LocalDate.parse("1991-02-20"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer113 = new Volunteer("Eduarda", "Santos", "33445566778", "47942775544", LocalDate.parse("1992-03-25"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer114 = new Volunteer("Fernando", "Lima", "44556677889", "47941664433", LocalDate.parse("1993-04-30"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer115 = new Volunteer("Gabriela", "Alves", "55667788990", "47940553322", LocalDate.parse("1994-05-05"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer116 = new Volunteer("Henrique", "Barros", "66778899001", "47939442211", LocalDate.parse("1995-06-10"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer117 = new Volunteer("Isabela", "Ferreira", "77889900112", "47938331100", LocalDate.parse("1996-07-15"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer118 = new Volunteer("João", "Gomes", "88990011223", "47937220099", LocalDate.parse("1997-08-20"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer119 = new Volunteer("Larissa", "Martins", "99001122334", "47936119988", LocalDate.parse("1998-09-25"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer120 = new Volunteer("Marcelo", "Nunes", "00112233445", "47935008877", LocalDate.parse("1999-10-30"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer121 = new Volunteer("Nathalia", "Oliveira", "11223344556", "47933997766", LocalDate.parse("2000-11-15"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer122 = new Volunteer("Otávio", "Pereira", "22334455667", "47932886655", LocalDate.parse("2001-12-20"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer123 = new Volunteer("Patrícia", "Queiroz", "33445566778", "47931775544", LocalDate.parse("2002-01-25"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer124 = new Volunteer("Rafael", "Ribeiro", "44556677889", "47930664433", LocalDate.parse("2003-02-10"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer125 = new Volunteer("Sabrina", "Silva", "55667788990", "47929553322", LocalDate.parse("2004-03-05"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer126 = new Volunteer("Thiago", "Torres", "66778899001", "47928442211", LocalDate.parse("2005-04-10"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer127 = new Volunteer("Úrsula", "Vieira", "77889900112", "47927331100", LocalDate.parse("2006-05-15"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer128 = new Volunteer("Vítor", "Xavier", "88990011223", "47926220099", LocalDate.parse("2007-06-20"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer129 = new Volunteer("Wagner", "Yamada", "99001122334", "47925119988", LocalDate.parse("2008-07-25"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer130 = new Volunteer("Yasmin", "Zanetti", "00112233445", "47924008877", LocalDate.parse("2009-08-30"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer131 = new Volunteer("Amanda", "Almeida", "12345678910", "47922997766", LocalDate.parse("1990-09-15"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer132 = new Volunteer("Bruno", "Brito", "23456789101", "47921886655", LocalDate.parse("1991-10-20"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer133 = new Volunteer("Cláudia", "Camargo", "34567891012", "47920775544", LocalDate.parse("1992-11-25"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer134 = new Volunteer("Diogo", "Dias", "45678910123", "47919664433", LocalDate.parse("1993-12-30"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer135 = new Volunteer("Elena", "Espinoza", "56789101234", "47918553322", LocalDate.parse("1994-01-05"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer136 = new Volunteer("Fábio", "Fonseca", "67891012345", "47917442211", LocalDate.parse("1995-02-10"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer137 = new Volunteer("Gisele", "Garcia", "78910123456", "47916331100", LocalDate.parse("1996-03-15"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer138 = new Volunteer("Hugo", "Henrique", "89101234567", "47915220099", LocalDate.parse("1997-04-20"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer139 = new Volunteer("Ivana", "Iglesias", "90112345678", "47914119988", LocalDate.parse("1998-05-25"), VolunteerOrigin.USER_REGISTERED);
		Volunteer volunteer140 = new Volunteer("Joana", "Jardim", "01234567890", "47913008877", LocalDate.parse("1999-06-30"), VolunteerOrigin.USER_REGISTERED);

		volunteerRepository.saveAll(List.of(
				volunteer1, volunteer2, volunteer3, volunteer4, volunteer5, volunteer6, volunteer7, volunteer8, volunteer9, volunteer10,
				volunteer11, volunteer12, volunteer13, volunteer14, volunteer15, volunteer16, volunteer17, volunteer18, volunteer19, volunteer20,
				volunteer21, volunteer22, volunteer23, volunteer24, volunteer25, volunteer26, volunteer27, volunteer28, volunteer29, volunteer30,
				volunteer31, volunteer32, volunteer33, volunteer34, volunteer35, volunteer36, volunteer37, volunteer38, volunteer39, volunteer40,
				volunteer41, volunteer42, volunteer43, volunteer44, volunteer45, volunteer46, volunteer47, volunteer48, volunteer49, volunteer50,
				volunteer51, volunteer52, volunteer53, volunteer54, volunteer55, volunteer56, volunteer57, volunteer58, volunteer59, volunteer60,
				volunteer61, volunteer62, volunteer63, volunteer64, volunteer65, volunteer66, volunteer67, volunteer68, volunteer69, volunteer70,
				volunteer71, volunteer72, volunteer73, volunteer74, volunteer75, volunteer76, volunteer77, volunteer78, volunteer79, volunteer80,
				volunteer81, volunteer82, volunteer83, volunteer84, volunteer85, volunteer86, volunteer87, volunteer88, volunteer89, volunteer90,
				volunteer91, volunteer92, volunteer93, volunteer94, volunteer95, volunteer96, volunteer97, volunteer98, volunteer99, volunteer100,
				volunteer101, volunteer102, volunteer103, volunteer104, volunteer105, volunteer106, volunteer107, volunteer108, volunteer109, volunteer110,
				volunteer111, volunteer112, volunteer113, volunteer114, volunteer115, volunteer116, volunteer117, volunteer118, volunteer119, volunteer120,
				volunteer121, volunteer122, volunteer123, volunteer124, volunteer125, volunteer126, volunteer127, volunteer128, volunteer129, volunteer130,
				volunteer131, volunteer132, volunteer133, volunteer134, volunteer135, volunteer136, volunteer137, volunteer138, volunteer139, volunteer140
		));

		List<Ministry> ministries = List.of(ministry, ministry2, ministry3, ministry4, ministry5, ministry6);
		List<Volunteer> volunteers = volunteerRepository.findAll();

		List<VolunteerMinistry> volunteerMinistries = new ArrayList<>();
		Random random = new Random();

		for (Volunteer volunteer : volunteers) {
			int numMinistriesForVolunteer = random.nextInt(5);

			List<Ministry> assignedMinistries = new ArrayList<>();
			for (int i = 0; i < numMinistriesForVolunteer; i++) {
				Ministry randomMinistry = ministries.get(random.nextInt(ministries.size()));

				if (!assignedMinistries.contains(randomMinistry)) {
					assignedMinistries.add(randomMinistry);
					volunteerMinistries.add(new VolunteerMinistry(volunteer, randomMinistry));
					randomMinistry.setTotalVolunteers(randomMinistry.getTotalVolunteers() + 1);
				} else {
					i--;
				}
			}
		}

		volunteerMinistryRepository.saveAll(volunteerMinistries);

		return null;
	}

	@Bean
	public Object createUser() {
		User user = new User("Admin", "admin@admin.com", "admin01");
		user.setIsSuperUser(true);
		user.setIsApproved(true);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);

		userMinistryService.associate(user, List.of(1L, 2L, 3L, 4L, 5L, 6L));
		return null;
	}
}
