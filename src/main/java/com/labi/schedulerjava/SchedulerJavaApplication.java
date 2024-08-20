package com.labi.schedulerjava;

import com.labi.schedulerjava.adapters.persistence.*;
import com.labi.schedulerjava.core.domain.model.*;
import com.labi.schedulerjava.core.domain.service.UserMinistryService;
import com.labi.schedulerjava.enums.VolunteerOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class SchedulerJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedulerJavaApplication.class, args);
	}
	@Autowired
	private MinistryRepository ministryRepository;

	@Autowired
	private ActivityRepository activityRepository;

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

	@Autowired
	private GroupRepository groupRepository;

	@Bean
	public Object createMinistry() {
		List<Ministry> ministries = List.of(
				new Ministry("Louvor", "Ministério de Louvor", "#FFB3BA"),
				new Ministry("Intercessão", "Ministério de Intercessão", "#FFDFBA"),
				new Ministry("Ação Social", "Ministério de Ação Social", "#FFFFBA"),
				new Ministry("Ensino", "Ministério de Ensino", "#BAFFC9"),
				new Ministry("Recepção", "Ministério de Recepção", "#BAE1FF"),
				new Ministry("Mídia", "Ministério de Mídia", "#D5BAFF"),
				new Ministry("Infantil", "Ministério Infantil", "#FFCCFF"),
				new Ministry("Jovens", "Ministério de Jovens", "#FFABAB"),
				new Ministry("Casais", "Ministério de Casais", "#FFD1DC"),
				new Ministry("Comunhão", "Ministério de Comunhão", "#C1E1C1")
		);
		ministryRepository.saveAll(ministries);

		List<Activity> activities = new ArrayList<>();
		activities.addAll(createActivitiesForMinistry("Louvor", ministries.get(0), List.of("Vocal", "Bateria", "Guitarra", "Baixo", "Teclado")));
		activities.addAll(createActivitiesForMinistry("Intercessão", ministries.get(1), List.of("Oração Matinal", "Vigília", "Jejum e Oração")));
		activities.addAll(createActivitiesForMinistry("Ação Social", ministries.get(2), List.of("Distribuição de Alimentos", "Visita a Asilos", "Campanhas Solidárias")));
		activities.addAll(createActivitiesForMinistry("Ensino", ministries.get(3), List.of("Escola Bíblica", "Estudo em Grupo", "Mentoria")));
		activities.addAll(createActivitiesForMinistry("Recepção", ministries.get(4), List.of("Boas-vindas", "Acompanhamento de Visitantes", "Entrega de Folhetos")));
		activities.addAll(createActivitiesForMinistry("Mídia", ministries.get(5), List.of("Fotografia", "Transmissão Ao Vivo", "Design Gráfico")));
		activities.addAll(createActivitiesForMinistry("Infantil", ministries.get(6), List.of("Escola Dominical", "Brincadeiras Educativas", "Contação de Histórias")));
		activities.addAll(createActivitiesForMinistry("Jovens", ministries.get(7), List.of("Encontros de Jovens", "Debates", "Atividades Esportivas")));
		activities.addAll(createActivitiesForMinistry("Casais", ministries.get(8), List.of("Encontro de Casais", "Palestras sobre Casamento", "Retiro de Casais")));
		activities.addAll(createActivitiesForMinistry("Comunhão", ministries.get(9), List.of("Almoços Comunitários", "Festas Anuais", "Eventos Especiais")));

		activityRepository.saveAll(activities);

		List<Volunteer> volunteers = createRealisticVolunteers(100);
		volunteerRepository.saveAll(volunteers);

		List<VolunteerMinistry> volunteerMinistries = new ArrayList<>();
		Random random = new Random();

		for (Volunteer volunteer : volunteers) {
			int numMinistriesForVolunteer = random.nextInt(3) + 1;

			Set<Ministry> assignedMinistries = new HashSet<>();
			for (int i = 0; i < numMinistriesForVolunteer; i++) {
				Ministry randomMinistry = ministries.get(random.nextInt(ministries.size()));

				if (assignedMinistries.add(randomMinistry)) {
					volunteerMinistries.add(new VolunteerMinistry(volunteer, randomMinistry));
				}
			}
		}
		volunteerMinistryRepository.saveAll(volunteerMinistries);

		List<Group> groups = createGroupsWithVolunteers(volunteers, 10); // 10 grupos criados
		groupRepository.saveAll(groups);

		return null;
	}

	private List<Activity> createActivitiesForMinistry(String ministryName, Ministry ministry, List<String> activityNames) {
		return activityNames.stream()
				.map(name -> new Activity(name, (long) (Math.random() * 7) + 1, ministry))
				.collect(Collectors.toList());
	}

	private List<Volunteer> createRealisticVolunteers(int count) {
		List<String> firstNames = List.of("Carlos", "Mariana", "Pedro", "Julia", "Lucas", "Maria", "João", "Ana", "Paula", "Miguel", "Larissa", "Rafael", "Gabriela", "Fernando", "Isabela");
		List<String> lastNames = List.of("Silva", "Souza", "Costa", "Pereira", "Oliveira", "Alves", "Lima", "Mendes", "Barbosa", "Santos", "Ribeiro", "Ferreira", "Santana", "Rocha", "Dias");

		List<Volunteer> volunteers = new ArrayList<>();

		for (int i = 0; i < count; i++) {
			String firstName = firstNames.get(new Random().nextInt(firstNames.size()));
			String lastName = lastNames.get(new Random().nextInt(lastNames.size()));
			String fullName = firstName + " " + lastName;

			Volunteer volunteer = new Volunteer(
					firstName,
					lastName,
					generateCPF(),
					generatePhoneNumber(),
					LocalDate.of(1980 + new Random().nextInt(40), 1 + new Random().nextInt(12), 1 + new Random().nextInt(28)),
					VolunteerOrigin.USER_REGISTERED
			);
			volunteers.add(volunteer);
		}

		return volunteers;
	}

	private String generateCPF() {
		Random random = new Random();
		return String.format("%03d.%03d.%03d-%02d",
				random.nextInt(1000),
				random.nextInt(1000),
				random.nextInt(1000),
				random.nextInt(100));
	}

	private String generatePhoneNumber() {
		Random random = new Random();
		return String.format("(47) 9%04d-%04d",
				random.nextInt(10000),
				random.nextInt(10000));
	}

	private List<Group> createGroupsWithVolunteers(List<Volunteer> volunteers, int groupCount) {
		List<Group> groups = new ArrayList<>();
		Random random = new Random();

		for (int i = 0; i < groupCount; i++) {
			Group group = new Group("Grupo " + (i + 1));
			int volunteersInGroup = random.nextInt(10) + 5; // Grupos com 5 a 15 voluntários

			for (int j = 0; j < volunteersInGroup; j++) {
				Volunteer volunteer = volunteers.get(random.nextInt(volunteers.size()));
				group.addVolunteer(volunteer);
			}

			groups.add(group);
		}

		return groups;
	}

	@Bean
	public Object createUser() {
		User user = new User("Admin", "admin@admin.com", "admin01");
		user.setIsSuperUser(true);
		user.setIsApproved(true);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);

		userMinistryService.associate(user, ministryRepository.findAll().stream().map(Ministry::getId).collect(Collectors.toList()));
		return null;
	}
}
