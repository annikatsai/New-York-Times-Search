package annikatsai.nytimessearch.models;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Legacy {

    private String thumbnailheight;
    private String thumbnail;
    private String thumbnailwidth;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The thumbnailheight
     */
    public String getThumbnailheight() {
        return thumbnailheight;
    }

    /**
     *
     * @param thumbnailheight
     * The thumbnailheight
     */
    public void setThumbnailheight(String thumbnailheight) {
        this.thumbnailheight = thumbnailheight;
    }

    /**
     *
     * @return
     * The thumbnail
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     *
     * @param thumbnail
     * The thumbnail
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     *
     * @return
     * The thumbnailwidth
     */
    public String getThumbnailwidth() {
        return thumbnailwidth;
    }

    /**
     *
     * @param thumbnailwidth
     * The thumbnailwidth
     */
    public void setThumbnailwidth(String thumbnailwidth) {
        this.thumbnailwidth = thumbnailwidth;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
