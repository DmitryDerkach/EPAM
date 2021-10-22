package xml_input_output;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XMLInputPOutput {
	/*В контейнерах содерижтся информация о рептилиях и рыбах соответственно*/
	static List<Reptile> reptileContainer = new ArrayList<Reptile>();	
	static List<Fish> fishContainer = new ArrayList<Fish>();	
	
	public static void main(String[] args) {

		/*Преобразование XML в объектную DOM модель с обработкой ошибок*/
		Document domObject = buildDocument();
		/*Обрабатываем информацию об аквариумной живности*/
		Node rootNode = domObject.getFirstChild();
		NodeList rootNideChildren = rootNode.getChildNodes();
		for (int i = 0; i < rootNideChildren.getLength(); i++) {
			if (rootNideChildren.item(i).getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			switch (rootNideChildren.item(i).getNodeName()) {
				case "fishes" : {
					getInformationAboutFishes(rootNideChildren.item(i));
				break;	
				}
				case "reptiles" : {
					getInformationAboutReptiles(rootNideChildren.item(i));
				break;	
				}
			}		
		}	
		/*Сформировываем аквариумы по заданному условию*/
		FormedAquariums listOfFormedAquariums = new FormedAquariums(assembleTheAquarium(fishContainer, reptileContainer));
		/*Преобразовываем полученные данные в XML*/
		XML_Output(listOfFormedAquariums);
		
	}// main 

	private static Document buildDocument() {
		File file = new File("epam_input_file.xml");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document document = null;
		try {
			document = dbf.newDocumentBuilder().parse(file);
		} catch (Exception e) {
			System.out.println("Document Parsing Error");
			e.printStackTrace();
			System.exit(0);
		}
		return document; 
	}
	
	private static void getInformationAboutFishes(Node data) {
		Node fishes = data;
		/*Получаем данные из нода fishes*/
		String fishName = "";
		int fishCost = 0;
		NodeList fishChildren =  fishes.getChildNodes();
		for (int a = 0; a < fishChildren.getLength(); a++) {
			if (fishChildren.item(a).getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			/*Извлекаем информацию из нода element, тем самым получая информацию про рыб*/
			NodeList fishData = fishChildren.item(a).getChildNodes();
			for (int b = 0; b < fishData.getLength(); b++) {
				if (fishData.item(b).getNodeType() != Node.ELEMENT_NODE) {
					continue;
				}
				switch (fishData.item(b).getNodeName()) {
					case "name" : {
						fishName = fishData.item(b).getTextContent();
						break;
					}
					case "cost" : {
						fishCost = Integer.valueOf(fishData.item(b).getTextContent());
						break;
					}
				}
			}
			fishContainer.add(new Fish(fishName,fishCost));
		}
	}
	
	public static void getInformationAboutReptiles(Node data) {
		Node reptiles = data;
		/*Получаем данные из нода reptiles*/
		String reptileName = "";
		int reptileCost = 0;
		NodeList reptileChildren =  reptiles.getChildNodes();
		for (int a = 0; a < reptileChildren.getLength(); a++) {
			if (reptileChildren.item(a).getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			/*Извлекаем информацию из нода element, тем самым получая информацию про рыб*/
			NodeList reptileData = reptileChildren.item(a).getChildNodes();
			for (int b = 0; b < reptileData.getLength(); b++) {
				if (reptileData.item(b).getNodeType() != Node.ELEMENT_NODE) {
					continue;
				}
				switch (reptileData.item(b).getNodeName()) {
					case "name" : {
						reptileName = reptileData.item(b).getTextContent();
						break;
					}
					case "cost" : {
						reptileCost = Integer.valueOf(reptileData.item(b).getTextContent());
						break;
					}
				}
			}
			reptileContainer.add(new Reptile(reptileName,reptileCost));
		}
	}

	private static List<Aquarium> assembleTheAquarium(List<Fish> fishContainer, List<Reptile> reptileContainer) {
		/*Создаем список сформированных аквариумов, на основании полученных данных о рыбах, рептилиях и достпуных на данный момент аксессуарах*/
		List<Aquarium> listOfAssembledAquariuns = new ArrayList<Aquarium>();
		
		/*Каждый аквариум будет содержать 2-ух существ одного вида, а также рандомный акссусуар из достпуных в магазине. Рыбы могут жить с рыбами, как и рептилии с
		 *рептилиями, но рыбы не могут жить с рептилиями*/
		
		/*Собираем аквариум только из рыб*/
		do {
			List<Fish> temp = new ArrayList<Fish>();
			temp.add(fishContainer.get(0));
			temp.add(fishContainer.get(1));
			Accessory randomAccessory = Accessory.getRandomAccessory();
			listOfAssembledAquariuns.add(new Aquarium(temp, null, randomAccessory, temp.get(0).getCost() + temp.get(1).getCost() + randomAccessory.getCost()));
			fishContainer.remove(0);
			fishContainer.remove(0);
		} while (fishContainer.size() != 0);
		
		/*Собираем аквариум только из рептилий*/
		do {
			List<Reptile> temp = new ArrayList<Reptile>();
			temp.add(reptileContainer.get(0));
			temp.add(reptileContainer.get(1));
			Accessory randomAccessory = Accessory.getRandomAccessory();
			listOfAssembledAquariuns.add(new Aquarium(null, temp, randomAccessory, temp.get(0).getCost() + temp.get(1).getCost() + randomAccessory.getCost()));
			reptileContainer.remove(0);
			reptileContainer.remove(0);
		} while (reptileContainer.size() != 0);
		
		return listOfAssembledAquariuns;
		
	}//assembleTheAquarium
	
	private static void XML_Output(FormedAquariums listOfFormedAquariums) {
		ObjectMapper mapper = new XmlMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		System.out.println("Успех!");
		try {
			mapper.writeValue(new File("epam_output_file.xml"), listOfFormedAquariums);
		} catch (Exception e) {
			System.out.println("Что-то пошло не так");
		} 
	}
	
}//public class

