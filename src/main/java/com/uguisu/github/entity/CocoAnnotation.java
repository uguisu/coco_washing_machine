package com.uguisu.github.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

/**
 * CocoAnnotation
 * @author kakin
 */
@Data
public class CocoAnnotation {

    /** id */
    private int id;
    /** image_id */
    @SerializedName("image_id")
    private int imageId;
    /** category_id */
    @SerializedName("category_id")
    private int categoryId;
    /** segmentation */
    private List<List<Float>> segmentation;
    /** area */
    private float area;
    /** bbox */
    private List<Float> bbox;
    /** iscrowd */
    private int iscrowd;
    /** attributes */
    private CocoAnnotationAttribute attributes;
}
