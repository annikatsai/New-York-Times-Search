package annikatsai.nytimessearch.models;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Keyword {

    private String rank;
    private String isMajor;
    private String name;
    private String value;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The rank
     */
    public String getRank() {
        return rank;
    }

    /**
     *
     * @param rank
     * The rank
     */
    public void setRank(String rank) {
        this.rank = rank;
    }

    /**
     *
     * @return
     * The isMajor
     */
    public String getIsMajor() {
        return isMajor;
    }

    /**
     *
     * @param isMajor
     * The is_major
     */
    public void setIsMajor(String isMajor) {
        this.isMajor = isMajor;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The value
     */
    public String getValue() {
        return value;
    }

    /**
     *
     * @param value
     * The value
     */
    public void setValue(String value) {
        this.value = value;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}