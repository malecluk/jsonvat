import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.assertj.core.groups.Tuple;
import org.junit.Before;
import org.junit.Test;

import data.Rate;
import datastorage.InMemVatStorage;


public class InMemVatStorageTest extends TestBase {

	private InMemVatStorage storage;
	
	@Before
	public void initTest() {
		storage = new InMemVatStorage();
        storage.add(createTestRate("AB", "testName", 11.));
        storage.add(createTestRate("CD", "testName", 12.));
		
        // country with more than one rate
        Map<String, Double> moreRates = new HashMap<>(1);
        moreRates.put("testName", 21.);
        moreRates.put("secondName", 22.);
        List<Rate.Period> pers = new LinkedList<>();
        pers.add(new Rate.Period(new Date(), moreRates));
        storage.add(new Rate("rateXY", "Code-XY", "XY", pers));
	}
	
	@Test
	public void testGetAllReturnsAll() {
		Collection<Rate> rates = storage.getAll();
		
		assertEquals(3, rates.size());
		assertThat(rates)
		    .extracting(Rate::getCode, rt -> rt.getLatestRates().get("testName"))
		    .contains(Tuple.tuple("Code-AB", 11.), Tuple.tuple("Code-XY", 21.), Tuple.tuple("Code-CD", 12.));	
	}
	
	@Test
	public void testGetAllSortedCorrectSorting() {
		assertThat(storage.getAllSortedBy("testName", true))
	        .extracting(Rate::getCode, rt -> rt.getLatestRates().get("testName"))
	        .containsExactly(Tuple.tuple("Code-AB", 11.), Tuple.tuple("Code-CD", 12.), Tuple.tuple("Code-XY", 21.));
		
		assertThat(storage.getAllSortedBy("testName", false))
            .extracting(Rate::getCode, rt -> rt.getLatestRates().get("testName"))
            .containsExactly(Tuple.tuple("Code-XY", 21.), Tuple.tuple("Code-CD", 12.), Tuple.tuple("Code-AB", 11.));
	}
	
	@Test
	public void testGetAllSortedIgnoresNoRateCountries() {
		assertThat(storage.getAllSortedBy("secondName", true))
            .extracting(Rate::getCode, rt -> rt.getLatestRates().get("secondName"))
            .containsExactly(Tuple.tuple("Code-XY", 22.));
	}
	
	@Test
	public void testAddRateIgnoresDoublets() {
		assertFalse("False must be returned in case of second addition of existing country and rate name!",
                storage.add(createTestRate("AB", "testName", 13.)));
	}

}
