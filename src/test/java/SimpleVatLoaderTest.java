import static org.junit.Assert.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import data.Rate;
import dataload.SimpleVatLoader;


public class SimpleVatLoaderTest {

	@Test(expected = IOException.class)
    public void testLoadWrongUrlException() throws Exception {
        new SimpleVatLoader("wrong url").loadAll(null);
    }
	
	@Test
    public void testCorrectLoad() throws Exception {
        List<Rate> rates = new LinkedList<>();
        new SimpleVatLoader(getClass().getClassLoader().getResource("datamock.json").toString())
                .loadAll(rates::add);
        assertEquals(28, rates.size());
    }
}
