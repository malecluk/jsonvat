import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import data.Rate;
import data.Rate.Period;

/**
 * Helper with methods used by more tests
 */
public abstract class TestBase {

	Rate createTestRate(String cntryCode, String rateName, Double rateValue) {
		
		Period testPeriod = newPeriod(new Date(), rateName, rateValue);
		
		Rate r = new Rate();
		r.setCountryCode(cntryCode);
		r.setCode("Code-" + cntryCode);
		r.setName("Name-" + cntryCode);
		r.addPeriods(Collections.singleton(testPeriod));
		
		return r;
	}
	
	Rate.Period newPeriod(Date effFrom, String rateName, Double rateValue) {
        Map<String, Double> map = new HashMap<>(1);
        map.put(rateName, rateValue);
        return new Rate.Period(effFrom, map);
    }
}
