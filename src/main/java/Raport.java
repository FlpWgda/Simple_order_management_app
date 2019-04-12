import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.text.DecimalFormat;

public class Raport {
	private int pierwsza;
	private int druga;
	private String clientID;
	private boolean print;
	private boolean csv;
	Raport(final int pierwsza, final int druga, final String clientID, final List<Zamowienie> lista) {
		this.pierwsza = pierwsza;
		this.druga = druga;
		this.clientID = clientID;
		if (pierwsza == 1) {
			print = true;
			csv = false;
		}else if (pierwsza == 2) {
			print = true;
			csv = true;
		}
		else {
			print = false;
			csv = true;
		}
		if(druga == 1 || druga == 2) {
			iloscZamowien(this.clientID,print,csv,lista);
		}
		else if(druga == 3 || druga == 4) {
			kwotaZamowien(this.clientID,print,csv,lista);
		}
		else if(druga == 5 || druga == 6) {
			listaZamowien(this.clientID,print,csv,lista);
		}
		else {
			sredniaWartoscZamowien(this.clientID,print,csv,lista);
		}
	}
	
	public int getPierwsza() {
        return pierwsza;
    }

    public void setPierwsza(int pierwsza) {
        this.pierwsza = pierwsza;
    }

    public int getDruga() {
        return druga;
    }

    public void setDruga(int druga) {
        this.druga = druga;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public boolean isPrint() {
        return print;
    }

    public void setPrint(boolean print) {
        this.print = print;
    }

    public boolean isCsv() {
        return csv;
    }

    public void setCsv(boolean csv) {
        this.csv = csv;
    }

    public final int iloscZamowien(final String clientID,final boolean print,final boolean csv,final List<Zamowienie> lista) {
		int iloscZamowien=0;
		List<String > gotowaLista = new ArrayList<String >();
		if(clientID == "wszyscy") {
			for(Zamowienie z:lista) {
				iloscZamowien++;
			}
			gotowaLista.add("Liczba wszystkich zamówień to: "+iloscZamowien);
		}
		else {
			for(Zamowienie z:lista) {
				if (z.getClient_id().equals(clientID)) {
					iloscZamowien++;
				}
			}	
			gotowaLista.add("Liczba wszystkich zamówień klienta o identyfikatorze " +clientID+" to: "+iloscZamowien);
		}
		if(print) {
			printRaport(gotowaLista);
		}
		if(csv) {
			csvRaport(gotowaLista);
		}
		return iloscZamowien;
	}
	public final double kwotaZamowien(final String clientID,final boolean print,final boolean csv,final List<Zamowienie> lista) {
		double kwotaZamowien=0;
		List<String > gotowaLista = new ArrayList<String >();
		if(clientID == "wszyscy") {
			for(Zamowienie z:lista) {
				kwotaZamowien= kwotaZamowien+ z.getPrice();
			}
			gotowaLista.add("Kwota wszystkich zamówień to: "+kwotaZamowien);
		}
		else {
			for(Zamowienie z:lista) {
				if (z.getClient_id().equals(clientID)) {
					kwotaZamowien=kwotaZamowien+ z.getPrice();
				}
			}	
			gotowaLista.add("Kwota wszystkich zamówień klienta o identyfikatorze " +clientID+" to: "+kwotaZamowien);
		}
		if(print) {
			printRaport(gotowaLista);
		}
		if(csv) {
			csvRaport(gotowaLista);
		}
		return kwotaZamowien;
	}
	public final void listaZamowien(final String clientID,final boolean print,final boolean csv,final List<Zamowienie> lista) {
		List<String > gotowaLista = new ArrayList<String >();
		if(clientID == "wszyscy") {
			for(Zamowienie z:lista) {
				gotowaLista.add(z.toString());
			}
			gotowaLista.add(0, "Client_Id,Request_id,Name,Quantity,Price");
			gotowaLista.add(0, "Lista wszystkich zamówień: ");
			if(print) {
				printRaport(gotowaLista);
			}
			if(csv) {
				csvRaport(gotowaLista);
			}
		}	
		else {
			for(Zamowienie z:lista) {
				if (z.getClient_id().equals(clientID)) {
					gotowaLista.add(z.toString2());
				}
			}
			gotowaLista.add(0, "Request_id,Name,Quantity,Price");
			gotowaLista.add(0, "Lista wszystkich zamówień klienta o identyfikatorze " +clientID+": ");
			if(print) {
				printRaport(gotowaLista);
			}
			if(csv) {
				csvRaport(gotowaLista);
			}	
		}
	}
	public final void sredniaWartoscZamowien(final String clientID,final boolean print,final boolean csv,final List<Zamowienie> lista) {
		double kwotaZamowien = kwotaZamowien(clientID,false,false,lista);
		int iloscZamowien = iloscZamowien(clientID,false,false,lista);
		double sredniaZamowien = 0;
		if (iloscZamowien != 0) {
		    sredniaZamowien  = kwotaZamowien/iloscZamowien;
		}
		DecimalFormat df = new DecimalFormat("####0.00");
		List<String> gotowaLista = new ArrayList<String>();
		if(clientID == "wszyscy") {
			gotowaLista.add("Średnia wartość zamówienia to: "+df.format(sredniaZamowien));
		}
		else {
			gotowaLista.add("Średnia wartość zamówienia klienta o identyfikatorze " +clientID+" to: "+ df.format(sredniaZamowien));
		}
		if(print) {
			printRaport(gotowaLista);
		}
		if(csv) {
			csvRaport(gotowaLista);
		}
	}
	public final void csvRaport(final List<String> list) {
		Iterator iter = list.iterator();
		Scanner sc = new Scanner(System.in);
		System.out.println("Podaj ścieżkę zapisu pliku CSV: ");
		String path = sc.nextLine();
		try {
				BufferedWriter writer = new BufferedWriter (new FileWriter (path));
	    	    
	    	while(iter.hasNext()) {
				String s = (String) iter.next();
				writer.write(s);
				writer.newLine();
			}
			writer.close();
			System.out.println("Raport zapisano do pliku CSV "+ path);}
		catch(IOException e) {
			System.out.println("Nie można zapisać pliku");
	    	e.printStackTrace();
		}
	}
	public final void printRaport(final List<String> list) {
		Iterator iter = list.iterator();
		while(iter.hasNext()) {
			String s = (String) iter.next();
			System.out.println(String.join(", ", s));
		}	
	}
}
