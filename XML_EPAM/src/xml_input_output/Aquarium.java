package xml_input_output;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.*;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"accessory", "reptile", "fish"})
public class Aquarium {
	@JacksonXmlElementWrapper(useWrapping = false)
	public List<Fish> fish = new ArrayList<Fish>();
	public Accessory accessory;
	@JacksonXmlElementWrapper(useWrapping = false)
	public List<Reptile> reptiles = new ArrayList<Reptile>();
	
	public Aquarium(List<Fish> fish, List<Reptile> reptiles, Accessory accessory) {
		this.fish = fish;
		this.accessory = accessory;
		this.reptiles = reptiles;
	}

	public List<Fish> getFish() {
		return fish;
	}

	public void setFish(List<Fish> fish) {
		this.fish = fish;
	}

	public Accessory getAccessory() {
		return accessory;
	}

	public void setAccessory(Accessory accessory) {
		this.accessory = accessory;
	}

	public List<Reptile> getReptiles() {
		return reptiles;
	}

	public void setReptiles(List<Reptile> reptiles) {
		this.reptiles = reptiles;
	}
		
}
