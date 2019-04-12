import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.io.Reader;
import java.util.Scanner;
class Obsluga_Zamowien_App {

    public static void main(final String[] args) {
        boolean zleLinie = false;
        List<Zamowienie> listaZamowien = new ArrayList<Zamowienie>();
		// TODO Auto-generated method stub
		try (
	            Reader reader = new BufferedReader(
	                    new InputStreamReader(
	                            new FileInputStream(args[0]), "cp1250"));
				CSVReader csvReader =
				        new CSVReaderBuilder(reader)
				        .withSkipLines(1)
				        .build();
						
	        ) {
	            // Reading Records One by One in a String array
	            String[] nextRecord;
	            while ((nextRecord = csvReader.readNext()) != null) {
	                try {
	                	Zamowienie zamowienie = new Zamowienie(
            					nextRecord[0],
            					Long.parseLong(nextRecord[1]),
            					nextRecord[2],
            					Integer.parseInt(nextRecord[3]),
            					Double.parseDouble(nextRecord[4])
            					);
	                	listaZamowien.add(zamowienie);
	            	} catch (ArrayIndexOutOfBoundsException e) {
	                	zleLinie = true;
	                }
	            }
	        } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		try {
			Reader reader = new BufferedReader(
			        new InputStreamReader(
			                new FileInputStream(args[1]), "cp1250"));
	        SAXBuilder saxBuilder = new SAXBuilder();
	        Document document = saxBuilder.build(reader);
	        Element classElement = document.getRootElement();
	         
	         
	        List<Element> listaZamowien2 = classElement.getChildren();
	        for (int temp = 0; temp < listaZamowien2.size(); temp++) {    
	             Element zamowienie = listaZamowien2.get(temp);
	             try {
	            	 Zamowienie zamowienie2 = new Zamowienie(
			 	zamowienie.getChild("clientId").getText(),
				Long.parseLong(zamowienie.getChild("requestId").getText()),
				zamowienie.getChild("name").getText(),
				Integer.parseInt(zamowienie.getChild("quantity").getText()),
				Double.parseDouble(zamowienie.getChild("price").getText())
				);
	            	 listaZamowien.add(zamowienie2);
         		  	}catch(NullPointerException e){
	             			zleLinie = true;
         		  	}
	        	}
	       } catch (JDOMException e) {
	          e.printStackTrace();
	       } catch (IOException ioe) {
	          ioe.printStackTrace();
	       }

		if (zleLinie) {
			System.out.println("W plikach znaleziono nieprawid³owe "
			        + "linie, które zostaly zignorowane ");
		}
		String menuTekstowe 	= "1- Generuj raport \n"
				+ "2- Generuj raport i zapisz w pliku .csv \n"
				+ "3- Zapisz raport w pliku .csv\n"
				+ "4- Zamknij program";
		String jakiRaport 		= "Wybierz raport?:\n"
				+ "1- Iloœæ zamówieñ ³¹cznie\n"
				+ "2- Iloœæ zamówieñ do klienta"
				+ " o wskazanym identyfikatorze\n"
				+ "3- £¹czna kwota zamówieñ\n"
				+ "4- £¹czna kwota zamówieñ do klienta "
				+ "o wskazanym identyfikatorze\n"
				+ "5- Lista wszystkich zamówieñ\n"
				+ "6- Lista zamówieñ do klienta "
				+ "o wskazanym identyfikatorze\n"
				+ "7- Œrednia wartoœæ zamówienia\n"
				+ "8- Œrednia wartoœæ zamówienia do klienta "
				+ "o wskazanym identyfikatorze\n";
		String podajId 		= "Podaj identyfikator klienta";
		String blednyNumer 	= "Niepoprawny numer, "
		        + "wprowadz jeszcze raz:";
		int zakonczProgram = 0;
		while (true) {
			System.out.println(menuTekstowe);
			int menu1 = input(1, 4);
			if (menu1 == 4) {
				break;
			}
			else {
				System.out.println(jakiRaport);
				int menu2 = input(1, 8);
				if (menu2 == 2 || menu2 == 4 
				        || menu2 == 6 || menu2 == 8) {
					System.out.println(podajId);
					Scanner sc = new Scanner(System.in);
					String clientID = sc.nextLine();
					Raport raport = new Raport(menu1, menu2, clientID, 
					        listaZamowien);
				}
				else {
					Raport raport = new Raport(menu1, menu2, "wszyscy", 
					        listaZamowien);
				}
				
			}
		}
	}
	public static int input(final int min, final int max) {
		    Scanner sc = new Scanner(System.in);
		    int input;
		    while (true) {
		        if (sc.hasNextInt()) {
		             input = sc.nextInt(); // Assign the next integer
		             if (input <= max && input >= min) { // Check
		                   break; // Condition met, break out of loop
		            }
		        } else {
		              sc.next();
		        }
		        System.out.println("Nieprawid³owa liczba."
		                + " Wprowadz jeszcze raz: ");
		    }
		    return input;
	}
}
