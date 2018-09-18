package tracker;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
class MealResourceAssembler implements ResourceAssembler<Meal, Resource<Meal>> {

	@Override
	public Resource<Meal> toResource(Meal meal) {
		return new Resource<>(meal,
			linkTo(methodOn(MealController.class).one(meal.getId())).withSelfRel(),
			linkTo(methodOn(MealController.class).all()).withRel("employees"));
	}
}
