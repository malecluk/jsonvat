package dataload;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.function.Consumer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import data.Rate;

public class SimpleVatLoader implements VatLoader {

	private String sourceUrl;
	
	public SimpleVatLoader(String src) {
		this.sourceUrl = src;
	}
	
	public String getSource() {
        return sourceUrl;
    }
	
	public void setSource(String src) {
		this.sourceUrl = src;
	}
	
	/**
	 * Returns reader for source data.
	 * Override this method for loading from different source (file etc.)
	 * @return reader for source
	 * @throws IOException when there were problems with source
	 */
	protected Reader getReader() throws IOException {
        URL url = new URL(getSource());
        return new BufferedReader(new InputStreamReader(url.openStream()));
    }
	
	private JsonArray readVatRates(Reader reader) throws IllegalArgumentException {
        JsonParser parser = new JsonParser();
        JsonElement jsonElm = parser.parse(reader);
        
        if (!jsonElm.isJsonObject()) {
            throw new IllegalArgumentException("Readed text is not json object!");
        }
        JsonObject jsonObject = jsonElm.getAsJsonObject();

        JsonElement jsonRt = jsonObject.get("rates");
        if (jsonRt == null || !jsonRt.isJsonArray()) {
            throw new IllegalArgumentException("There are no rates in readed text");
        }
        
        return jsonRt.getAsJsonArray();
    }
	
	@Override
	public void loadAll(Consumer<Rate> consumer) throws IOException, IllegalArgumentException {
		Gson gson = new Gson();
		TypeAdapter<Rate> adapter = gson.getAdapter(Rate.class).nullSafe();
		
		readVatRates(getReader())
		  .forEach(el -> {
			  try {
                  consumer.accept(adapter.fromJsonTree(el));
              } catch (JsonSyntaxException | JsonIOException ex) {
                  throw new IllegalArgumentException("Cannot parse rate element");
              }
		  });
	}

}
