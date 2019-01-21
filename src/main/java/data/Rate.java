package data;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import com.google.gson.annotations.SerializedName;

/**
 * Representation of rates per one country
 */
public class Rate {
	
	private String name;
    private String code;
    
    @SerializedName("country_code")
    private String countryCode;
    
    private SortedSet<Period> periods = new TreeSet<>();
    
    public Rate() {
    	super();
    }
    
    public Rate(String name, String code, String countryCode, Collection<Period> periods) {
    	this.name = name;
    	this.code = code;
    	this.countryCode = countryCode;
        this.periods.addAll(periods);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.code);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Rate r = (Rate) o;
        return this.code.equalsIgnoreCase(r.code);
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }

    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
    	this.code = code;
    }

    public String getCountryCode() {
        return this.countryCode;
    }
    
    public void setCountryCode(String countryCode) {
    	this.countryCode = countryCode;
    }
    
    public void addPeriods(Collection<Period> periods) {
    	this.periods.addAll(periods);
    }
    
    public Map<String, Double> getLatestRates() {
        return this.periods.last().getRates();
    }
    
    /**
     * Represents one set of rates with one effective date 
     */
    public static class Period implements Comparable<Period> {
    	@SerializedName("effective_from")
    	private Date effFrom;
        private Map<String, Double> rates;
        
        public Period(Date effFrom, Map<String, Double> rates) {
            this.effFrom = effFrom;
            this.rates = rates;
        }

        Date getEffectiveFrom() {
            return effFrom;
        }

        Map<String, Double> getRates() {
            return Collections.unmodifiableMap(rates);
        }
        
		@Override
		public int compareTo(Period o) {
			return effFrom.compareTo(o.getEffectiveFrom());
		}
    }
}
