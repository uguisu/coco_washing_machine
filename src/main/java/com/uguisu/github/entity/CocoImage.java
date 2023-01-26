package com.uguisu.github.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * CocoImage
 * @author kakin
 */
@Data
public class CocoImage {

    /** id */
    private int id;
    /** width */
    private int width;
    /** height */
    private int height;
    /** file_name */
    @SerializedName("file_name")
    private String fileName;
    /** license */
    private int license;
    /** flickr_url */
    @SerializedName("flickr_url")
    private String flickrUrl;
    /** coco_url */
    @SerializedName("coco_url")
    private String cocoUrl;
    /** date_captured */
    @SerializedName("date_captured")
    private int dateCaptured;
}
