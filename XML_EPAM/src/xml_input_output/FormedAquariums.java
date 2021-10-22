package xml_input_output;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement (localName = "formed_aquariums")
public class FormedAquariums {
	@JacksonXmlElementWrapper(useWrapping = false)
	public List<Aquarium> aquarium = new ArrayList<Aquarium>();

	public FormedAquariums(List<Aquarium> aquarium) {
		this.aquarium = aquarium;
	}

	public FormedAquariums() {
		
	}

	public List<Aquarium> getAquarium() {
		return aquarium;
	}

	public void setAquarium(List<Aquarium> aquarium) {
		this.aquarium = aquarium;
	}
}
