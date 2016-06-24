package annikatsai.nytimessearch.models;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Headline {

    private String main;
    private String contentKicker;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The main
     */
    public String getMain() {
        return main;
    }

    /**
     *
     * @param main
     * The main
     */
    public void setMain(String main) {
        this.main = main;
    }

    /**
     *
     * @return
     * The contentKicker
     */
    public String getContentKicker() {
        return contentKicker;
    }

    /**
     *
     * @param contentKicker
     * The content_kicker
     */
    public void setContentKicker(String contentKicker) {
        this.contentKicker = contentKicker;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}