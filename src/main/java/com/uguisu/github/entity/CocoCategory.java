package com.uguisu.github.entity;

import lombok.Data;

/**
 * CocoCategory
 * @author kakin
 */
@Data
public class CocoCategory {

    /** id */
    private int id;
    /** name */
    private String name;
    /** supercategory */
    private String supercategory;
}
