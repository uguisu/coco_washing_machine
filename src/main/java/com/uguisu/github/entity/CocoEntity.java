package com.uguisu.github.entity;

import lombok.Data;

import java.util.List;

/**
 * CocoEntity
 * @author kakin
 */
@Data
public class CocoEntity {

    /** licenses */
    private List<CocoLicense> licenses;
    /** info */
    private CocoInfo info;
    /** categories */
    private List<CocoCategory> categories;
    /** images */
    private List<CocoImage> images;
    /** annotations */
    private List<CocoAnnotation> annotations;
}
