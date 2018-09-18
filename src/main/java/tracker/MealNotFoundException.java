package tracker;

class MealNotFoundException extends RuntimeException {

	MealNotFoundException(Long id) {
		super("Could not find employee " + id);
	}
}
