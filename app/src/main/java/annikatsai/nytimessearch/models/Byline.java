package annikatsai.nytimessearch.models;

import java.util.Map;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;
        import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Byline {

    private List<Person> person = new ArrayList<Person>();
    private String original;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The person
     */
    public List<Person> getPerson() {
        return person;
    }

    /**
     *
     * @param person
     * The person
     */
    public void setPerson(List<Person> person) {
        this.person = person;
    }

    /**
     *
     * @return
     * The original
     */
    public String getOriginal() {
        return original;
    }

    /**
     *
     * @param original
     * The original
     */
    public void setOriginal(String original) {
        this.original = original;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}