package datastorage;

import java.util.Collection;
import java.util.List;

import data.Rate;

/**
 * Storage definition for rates
 */
public interface VatStorage {
	
	/**
	 * Adds new {@link Rate} into storage
	 * @param rate 
	 * @return true if added
	 */
	boolean add(Rate rate);
	
	/**
	 * Returns all rates stored
	 * @return all rates
	 */
	Collection<Rate> getAll();
	
	/**
	 * Returns all rates sorted by given rat type (name)
	 * @param rateType type (name) of rate (ex.: super_reduced, reduced, standard, standard1, etc).
	 * @param reversed affect sort order (asc, desc) 
	 * @return sorted rates
	 */
	List<Rate> getAllSortedBy(String rateType, boolean reversed);

}
