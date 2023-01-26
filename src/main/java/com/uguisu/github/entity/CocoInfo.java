package com.uguisu.github.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * CocoInfo
 * @author kakin
 */
@Data
public class CocoInfo {

    /** contributor */
    private String contributor;
    /** date_created */
    @SerializedName("date_created")
    private String dateCreated;
    /** description */
    private String description;
    /** url */
    private String url;
    /** version */
    private String version;
    /** year */
    private String year;
}
