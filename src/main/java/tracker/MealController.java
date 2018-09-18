package tracker;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// tag::constructor[]
@RestController
class MealController {

	private final MealRepository repository;

	private final MealResourceAssembler assembler;
	private final OrderResourceAssembler orderAssembler;

	MealController(MealRepository repository,
                   MealResourceAssembler assembler,
                   OrderResourceAssembler orderAssembler) {
		
		this.repository = repository;
		this.assembler = assembler;
		this.orderAssembler = orderAssembler;
	}
	// end::constructor[]

	// Aggregate root

	@GetMapping("/employees")
	Resources<Resource<Meal>> all() {

		List<Resource<Meal>> employees = repository.findAll().stream()
			.map(assembler::toResource)
			.collect(Collectors.toList());
		
		return new Resources<>(employees,
			linkTo(methodOn(MealController.class).all()).withSelfRel());
	}

	@PostMapping("/employees")
	ResponseEntity<?> newEmployee(@RequestBody Meal newMeal) throws URISyntaxException {

		Resource<Meal> resource = assembler.toResource(repository.save(newMeal));

		return ResponseEntity
			.created(new URI(resource.getId().expand().getHref()))
			.body(resource);
	}

	// Single item

	@GetMapping("/employees/{id}")
	Resource<Meal> one(@PathVariable Long id) {

		Meal meal = repository.findById(id)
			.orElseThrow(() -> new MealNotFoundException(id));
		
		return assembler.toResource(meal);
	}

	@PutMapping("/employees/{id}")
	ResponseEntity<?> replaceEmployee(@RequestBody Meal newMeal, @PathVariable Long id) throws URISyntaxException {

		Meal updatedMeal = repository.findById(id)
			.map(meal -> {
				meal.setEntree(newMeal.getEntree());
				meal.setSide(newMeal.getSide());
                meal.setDrink(newMeal.getDrink());
                meal.setRestaurant(newMeal.getRestaurant());
                meal.setCalories(newMeal.getCalories());
				return repository.save(meal);
			})
			.orElseGet(() -> {
				newMeal.setId(id);
				return repository.save(newMeal);
			});

		Resource<Meal> resource = assembler.toResource(updatedMeal);

		return ResponseEntity
			.created(new URI(resource.getId().expand().getHref()))
			.body(resource);
	}

	@DeleteMapping("/employees/{id}")
	ResponseEntity<?> deleteEmployee(@PathVariable Long id) {

		repository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
}
