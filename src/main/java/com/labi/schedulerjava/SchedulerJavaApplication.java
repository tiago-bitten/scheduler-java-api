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

		volunteerRepository.saveAll(List.of(volunteer1, volunteer2, volunteer3, volunteer4, volunteer5, volunteer6, volunteer7, volunteer8, volunteer9, volunteer10, volunteer11, volunteer12, volunteer13, volunteer14, volunteer15, volunteer16, volunteer17, volunteer18, volunteer19, volunteer20));


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
