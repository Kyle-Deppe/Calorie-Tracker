package tracker;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
class Meal {

	private @Id @GeneratedValue Long id;
	private String entree;
	private String side;
	private String drink;
	private String restaurant;
	private Integer calories;

	Meal(String entree, String side, String drink, String restaurant, Integer calories) {
		this.entree = entree;
		this.side = side;
		this.drink = drink;
		this.restaurant = restaurant;
		this.calories = calories;
	}

	public String getMeal() {
		return this.entree + " " + this.side + " " + this.drink;
	}

	public void setMeal(String meal) {
		String[] parts = meal.split(" ");
		this.entree = parts[0];
		this.side = parts[1];
		this.drink = parts[2];
	}
}
