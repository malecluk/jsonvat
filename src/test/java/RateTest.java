import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import data.Rate;

public class RateTest extends TestBase {
	
	private Rate rate;

	@Before
	public void initTest() {
		long nowMilis = new Date().getTime();
		
		List<Rate.Period> periods = new LinkedList<>();
		periods.add(newPeriod(new Date(nowMilis - 60000), "testName", 20.));
		periods.add(newPeriod(new Date(nowMilis - 120000), "testName", 22.));
		periods.add(newPeriod(new Date(nowMilis - 180000), "testName", 24.));
		
		this.rate = new Rate("Test country", "TS", "TST", periods);
	}
	
	@Test
	public void testBasicFields() {
		assertEquals("Test country", this.rate.getName());
		assertEquals("TS", this.rate.getCode());
		assertEquals("TST", this.rate.getCountryCode());
	}
	
	@Test
	public void testLatestPeriod() {
		assertEquals(new Double(20.), rate.getLatestRates().get("testName"));
	}

}
