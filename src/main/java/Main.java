import java.util.List;
import data.Rate;
import dataload.SimpleVatLoader;
import dataload.VatLoader;
import datastorage.InMemVatStorage;
import datastorage.VatStorage;

public class Main {
	
	private static final String NAME = "standard";
	private static final int LIMIT = 3;
    
	private static void printOneRate(Rate rate) {
	    System.out.println(rate.getName() + " (" + rate.getCountryCode() + "): " + rate.getLatestRates().get(NAME));
	}
	
	private static void writeTopCountryRates(List<Rate> countryRates) {
        countryRates.subList(0, LIMIT).forEach(Main::printOneRate);
    }
	
	public static void main(String[] args) {
		VatStorage store = new InMemVatStorage();
		VatLoader loader = new SimpleVatLoader("http://jsonvat.com");
		
		try {
			System.out.println("Starting data load");
            loader.loadAll(store::add);
        } catch (Exception ex) {
        	System.out.println("Load failed");
            ex.printStackTrace();
            return;
        }
		System.out.println("Data load finished");

		System.out.println();
		System.out.println("Lowest standard vat:");
		writeTopCountryRates(store.getAllSortedBy(NAME, true));
		
		System.out.println();
		System.out.println("Highest standard vat:");
		writeTopCountryRates(store.getAllSortedBy(NAME, false));
	}
}
