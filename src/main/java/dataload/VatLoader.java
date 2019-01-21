package dataload;

import java.io.IOException;
import java.util.function.Consumer;
import data.Rate;

/**
 * Loader definition for rates
 */
public interface VatLoader {

	/**
	 * Used to load all records from json into storage
	 * @param consumer used for each one record
	 * @throws IOException when there is an issue with data source
	 * @throws IllegalArgumentException when there is an issue during parsing of one record
	 */
	void loadAll(Consumer<Rate> consumer) throws IOException, IllegalArgumentException;
	
}
