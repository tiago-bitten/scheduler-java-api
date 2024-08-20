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
		List<Volunteer> volunteers = createRealisticVolunteers(100);
		List<Group> groups = createGroups(20);  // Criando 20 grupos menores
		groupRepository.saveAll(groups);

		List<Ministry> ministries = createMinistries();
		ministryRepository.saveAll(ministries);

		List<Activity> activities = createActivities(ministries);
		activityRepository.saveAll(activities);

		assignVolunteersToGroups(volunteers, groups, ministries);
		volunteerRepository.saveAll(volunteers);

		assignVolunteersToMinistries(volunteers, ministries);

		return null;
	}

	private List<Ministry> createMinistries() {
		return List.of(
				new Ministry("Louvor", "Ministério de Louvor", "#FF6347"),       // Tomate
				new Ministry("Intercessão", "Ministério de Intercessão", "#FFD700"),  // Ouro
				new Ministry("Ação Social", "Ministério de Ação Social", "#32CD32"),  // Verde Limão
				new Ministry("Ensino", "Ministério de Ensino", "#1E90FF"),       // Azul Dodger
				new Ministry("Recepção", "Ministério de Recepção", "#FF4500"),   // Laranja Vermelho
				new Ministry("Mídia", "Ministério de Mídia", "#DA70D6"),         // Orquídea
				new Ministry("Infantil", "Ministério Infantil", "#FF69B4"),      // Rosa Quente
				new Ministry("Jovens", "Ministério de Jovens", "#00BFFF"),       // Azul Profundo
				new Ministry("Casais", "Ministério de Casais", "#FF1493"),       // Rosa Choque
				new Ministry("Comunhão", "Ministério de Comunhão", "#7FFF00"),   // Verde Chartreuse
				new Ministry("Evangelismo", "Ministério de Evangelismo", "#8A2BE2"),  // Azul Violeta
				new Ministry("Missões", "Ministério de Missões", "#00FA9A")      // Verde Média Marinha
		);
	}

	private List<Activity> createActivities(List<Ministry> ministries) {
		List<Activity> activities = new ArrayList<>();
		activities.addAll(createActivitiesForMinistry(ministries.get(0), List.of("Vocal", "Bateria", "Guitarra", "Baixo", "Teclado")));
		activities.addAll(createActivitiesForMinistry(ministries.get(1), List.of("Oração Matinal", "Vigília", "Jejum e Oração")));
		activities.addAll(createActivitiesForMinistry(ministries.get(2), List.of("Distribuição de Alimentos", "Visita a Asilos", "Campanhas Solidárias")));
		activities.addAll(createActivitiesForMinistry(ministries.get(3), List.of("Escola Bíblica", "Estudo em Grupo", "Mentoria")));
		activities.addAll(createActivitiesForMinistry(ministries.get(4), List.of("Boas-vindas", "Acompanhamento de Visitantes", "Entrega de Folhetos")));
		activities.addAll(createActivitiesForMinistry(ministries.get(5), List.of("Fotografia", "Transmissão Ao Vivo", "Design Gráfico")));
		activities.addAll(createActivitiesForMinistry(ministries.get(6), List.of("Escola Dominical", "Brincadeiras Educativas", "Contação de Histórias")));
		activities.addAll(createActivitiesForMinistry(ministries.get(7), List.of("Encontros de Jovens", "Debates", "Atividades Esportivas")));
		activities.addAll(createActivitiesForMinistry(ministries.get(8), List.of("Encontro de Casais", "Palestras sobre Casamento", "Retiro de Casais")));
		activities.addAll(createActivitiesForMinistry(ministries.get(9), List.of("Almoços Comunitários", "Festas Anuais", "Eventos Especiais")));
		activities.addAll(createActivitiesForMinistry(ministries.get(10), List.of("Evangelismo nas Ruas", "Distribuição de Folhetos", "Culto ao Ar Livre")));
		activities.addAll(createActivitiesForMinistry(ministries.get(11), List.of("Missões Urbanas", "Missões Rurais", "Missões Internacionais")));

		return activities;
	}

	private List<Activity> createActivitiesForMinistry(Ministry ministry, List<String> activityNames) {
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

	private List<Group> createGroups(int groupCount) {
		List<Group> groups = new ArrayList<>();

		for (int i = 0; i < groupCount; i++) {
			Group group = new Group("Grupo " + (i + 1));
			groups.add(group);
		}

		return groups;
	}

	private void assignVolunteersToGroups(List<Volunteer> volunteers, List<Group> groups, List<Ministry> ministries) {
		Random random = new Random();

		for (Volunteer volunteer : volunteers) {
			Group assignedGroup = null;
			for (Group group : groups) {
				boolean hasCommonMinistry = group.getVolunteers().stream()
						.anyMatch(v -> v.getVolunteerMinistries().stream()
								.anyMatch(m -> volunteer.getVolunteerMinistries().contains(m)));

				if (hasCommonMinistry) {
					assignedGroup = group;
					break;
				}
			}

			if (assignedGroup == null) {
				assignedGroup = groups.get(random.nextInt(groups.size()));
			}

			assignedGroup.addVolunteer(volunteer);
		}
	}

	private void assignVolunteersToMinistries(List<Volunteer> volunteers, List<Ministry> ministries) {
		Random random = new Random();

		for (Volunteer volunteer : volunteers) {
			int numMinistriesForVolunteer = random.nextInt(3) + 1;

			Set<Ministry> assignedMinistries = new HashSet<>();
			for (int i = 0; i < numMinistriesForVolunteer; i++) {
				Ministry randomMinistry = ministries.get(random.nextInt(ministries.size()));

				if (assignedMinistries.add(randomMinistry)) {
					VolunteerMinistry volunteerMinistry = new VolunteerMinistry(volunteer, randomMinistry);

					volunteer.addVolunteerMinistry(volunteerMinistry);

					volunteerMinistryRepository.save(volunteerMinistry);
				}
			}
		}

		volunteerRepository.saveAll(volunteers);
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
