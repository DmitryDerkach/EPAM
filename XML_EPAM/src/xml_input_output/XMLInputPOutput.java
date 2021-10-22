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
	public static void main(String[] args) {
		List<Reptile> reptileContainer = new ArrayList<Reptile>();	
		List<Fish> fishContainer = new ArrayList<Fish>();	
		/*Преобразование XML в объектную DOM модель с обработкой ошибок*/
		Document domObject = buildDocument();
		
		Node rootNode = domObject.getFirstChild();
		/*Вытаскиваем ноды Fishes + Reptiles, исключая #text, т.к. данный нод не несет информации для обработки*/
		NodeList rootNideChildren = rootNode.getChildNodes();
		Node fishes = null;
		Node reptiles = null;
		for (int i = 0; i < rootNideChildren.getLength(); i++) {
			if (rootNideChildren.item(i).getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			switch (rootNideChildren.item(i).getNodeName()) {
				case "fishes" : {
					fishes = rootNideChildren.item(i);
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
				break;	
				}//case fishes
				case "reptiles" : {
					reptiles = rootNideChildren.item(i);
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
//						System.out.println(reptileContainer.toString());
					}
				break;	
				}//case reptiles
			}//switch		
		}//for	
		
	FormedAquariums listOfFormedAquariums = new FormedAquariums(assembleTheAquarium(fishContainer, reptileContainer, Accessory.FIRST_ACCESORY));
	XML_Output(listOfFormedAquariums);
		
	}// main 

//	private static List<Fish> getInformationAboutFishes(NodeList data) {
//		fishes = data.item(i);
//		/*Получаем данные из нода fishes*/
//		String fishName = "";
//		int fishCost = 0;
//		NodeList fishChildren =  fishes.getChildNodes();
//		for (int a = 0; a < fishChildren.getLength(); a++) {
//			if (fishChildren.item(a).getNodeType() != Node.ELEMENT_NODE) {
//				continue;
//			}
//			/*Извлекаем информацию из нода element, тем самым получая информацию про рыб*/
//			NodeList fishData = fishChildren.item(a).getChildNodes();
//			for (int b = 0; b < fishData.getLength(); b++) {
//				if (fishData.item(b).getNodeType() != Node.ELEMENT_NODE) {
//					continue;
//				}
//				switch (fishData.item(b).getNodeName()) {
//					case "name" : {
//						fishName = fishData.item(b).getTextContent();
//						break;
//					}
//					case "cost" : {
//						fishCost = Integer.valueOf(fishData.item(b).getTextContent());
//						break;
//					}
//				}
//			}
//			fishContainer.add(new Fish(fishName,fishCost));
//		}
//	}
//	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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
	
	private static List<Aquarium> assembleTheAquarium(List<Fish> fishContainer, List<Reptile> reptileContainer,  Accessory accessory) {
		/*Создаем список сформированных аквариумов, на основании полученных данных о рыбах, рептилиях и достпуных на данный момент аксессуарах*/
		List<Aquarium> listOfAssembledAquariuns = new ArrayList<Aquarium>();
		
		/*Каждый аквариум будет содержать 2-ух существ одного вида. Рыбы могут жить с рыбами, как и рептилии с
		 *рептилиями, но рыбы не могут жить с рептилиями*/
		
		/*Собираем аквариум только из рыб*/
		do {
			List<Fish> temp = new ArrayList<Fish>();
			temp.add(fishContainer.get(0));
			temp.add(fishContainer.get(1));
			listOfAssembledAquariuns.add(new Aquarium(temp, accessory.FIRST_ACCESORY, null));
			fishContainer.remove(0);
			fishContainer.remove(0);
		} while (fishContainer.size() != 0);
		
		/*Собираем аквариум только из рептилий*/
		do {
			List<Reptile> temp = new ArrayList<Reptile>();
			temp.add(reptileContainer.get(0));
			temp.add(reptileContainer.get(1));
			listOfAssembledAquariuns.add(new Aquarium(null, accessory.FIRST_ACCESORY, temp));
			reptileContainer.remove(0);
			reptileContainer.remove(0);
		} while (reptileContainer.size() != 0);
		
		return listOfAssembledAquariuns;
		
	}//assembleTheAquarium
	
	
	
	

		

}//public class

