package tracker;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class LoadDatabase {

	@Bean
	CommandLineRunner initDatabase(MealRepository mealRepository,
								   OrderRepository orderRepository) {
		return args -> {
			mealRepository.save(new Meal("Steak", "Fries", "Sprite", "Steaks R Us", 2000));
			mealRepository.save(new Meal("Chicken", "None", "Milk", "Home", 1000));

			mealRepository.findAll().forEach(meal -> {
				log.info("Preloaded " + meal);
			});

			// tag::order[]
			orderRepository.save(new Order("MacBook Pro", Status.COMPLETED));
			orderRepository.save(new Order("iPhone", Status.IN_PROGRESS));

			orderRepository.findAll().forEach(order -> {
				log.info("Preloaded " + order);
			});
			// end::order[]
		};
	}
}
