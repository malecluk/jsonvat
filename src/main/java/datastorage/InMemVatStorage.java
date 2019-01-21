package datastorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import data.Rate;

public class InMemVatStorage implements VatStorage {

	private Set<Rate> rates = new HashSet<>();
	
	@Override
	public boolean add(Rate rate) {
		return this.rates.add(rate);
	}

	@Override
	public Collection<Rate> getAll() {
		return this.rates;
	}

	@Override
	public List<Rate> getAllSortedBy(String rateType, boolean reversed) {
		Comparator<Rate> comparator = Comparator.comparing(rate -> rate.getLatestRates().get(rateType));
		return rates.stream()
                .filter(rate -> rate.getLatestRates().get(rateType) != null)
                .sorted(reversed ? comparator : comparator.reversed())
                .collect(Collectors.toList());
	}
}
