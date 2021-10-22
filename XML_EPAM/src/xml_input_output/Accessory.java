package xml_input_output;

import com.fasterxml.jackson.annotation.JsonFormat;
@JsonFormat(shape = JsonFormat.Shape.OBJECT)

public enum Accessory {

	FIRST_ACCESORY("Hygger aquarium gravel cleaner", 150),
	SECOND_ACCESORY("AQQA Aquarium Lights",70),
	THIRD_ACCESORY("PINVNBY Natural Aquarium Driftwood", 90),
	FOURTH_ACCESORY("API Stress Coat Water Conditioner", 35);
	
	String name = "";
	int cost = 0;
	
	private Accessory(String name, int cost) {
		this.cost = cost;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

}
