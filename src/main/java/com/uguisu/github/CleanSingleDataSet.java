package com.uguisu.github;

import com.uguisu.github.entity.*;

import java.util.*;

/**
 * CleanSingleDataSet
 * @author kakin
 */
public class CleanSingleDataSet {

    /** cocoEntity */
    private final CocoEntity cocoEntity;

    /** categoryMap: key - id; val - name */
    private final HashMap<Integer, CocoCategory> categoryMap = new HashMap<>();
    private final HashMap<Integer, Integer> categoryKeyMap = new HashMap<>();
    private final TreeMap<Integer, CocoCategory> cleanCategoryMap = new TreeMap<>();
    /** imageMap: key - id; val - CocoImage object */
    private final HashMap<Integer, CocoImage> imageMap = new HashMap<>();
    private final HashMap<Integer, Integer> imageKeyMap = new HashMap<>();
    private final TreeMap<Integer, CocoImage> cleanImageMap = new TreeMap<>();
    /** annotationMap: key - id; val - CocoAnnotation object */
    private final HashMap<Integer, CocoAnnotation> annotationMap = new HashMap<>();
    private final HashMap<Integer, Integer> annotationKeyMap = new HashMap<>();
    private final TreeMap<Integer, CocoAnnotation> cleanAnnotationMap = new TreeMap<>();
    /** licenseMap: key - id; val - CocoLicense object */
    private final HashMap<Integer, CocoLicense> licenseMap = new HashMap<>();
    private final HashMap<Integer, Integer> licenseKeyMap = new HashMap<>();
    private final TreeMap<Integer, CocoLicense> cleanLicenseMap = new TreeMap<>();

    public CleanSingleDataSet(CocoEntity cocoEntity) {
        this.cocoEntity = cocoEntity;
        // init all status
        fetchStatus();
    }

    /**
     * fetch status
     */
    public void fetchStatus() {
        // fetch license map
        this.fetchLicenseMap();
        // fetch category map
        this.fetchCategoryMap();
        // fetch image map
        this.fetchImageMap();
        // fetch annotation map
        this.fetchAnnotationMap();
    }

    /**
     * fetch license map
     */
    private void fetchLicenseMap() {
        // verify
        if(null == this.cocoEntity.getCategories())
            throw new RuntimeException("can not read any license.");

        // make map
        for(CocoLicense cc : this.cocoEntity.getLicenses()) {
            this.licenseMap.put(cc.getId(), cc);
        }
    }

    /**
     * fetch category map
     */
    private void fetchCategoryMap() {

        // verify
        if(null == this.cocoEntity.getCategories())
            throw new RuntimeException("can not read any categories.");

        // make map
        for(CocoCategory cc : this.cocoEntity.getCategories()) {
            this.categoryMap.put(cc.getId(), cc);
        }
    }

    /**
     * fetch image map
     */
    private void fetchImageMap() {

        // verify
        if(null == this.cocoEntity.getImages())
            throw new RuntimeException("can not read any images.");

        // make map
        for(CocoImage cc : this.cocoEntity.getImages()) {
            this.imageMap.put(cc.getId(), cc);
        }
    }

    /**
     * fetch annotation map
     */
    private void fetchAnnotationMap() {
        // verify
        if(null == this.cocoEntity.getAnnotations())
            throw new RuntimeException("can not read any annotations.");

        // make map
        for(CocoAnnotation cc : this.cocoEntity.getAnnotations()) {
            this.annotationMap.put(cc.getId(), cc);
        }
    }

    /**
     * display status
     */
    public void displayStatus() {
        System.out.println("=== display status ===");
        System.out.println("licenses: " + this.cocoEntity.getLicenses().size());
        System.out.println("categories: " + this.categoryMap.size());
        System.out.println("image: " + this.imageMap.size());
        System.out.println("annotations: " + this.annotationMap.size());
    }

    /**
     * clean
     * @return {@link CocoEntity} object
     */
    public CocoEntity clean() {

        int annotationId = 1;
        for(CocoAnnotation cA : this.annotationMap.values()) {

            int imgId = cA.getImageId();
            int categoryId = cA.getCategoryId();

            if(isKeyMatched(imgId, categoryId)) {

                // match annotation, use NEW ID
                this.cleanAnnotationMap.put(annotationId++, cA);
                // match image
                this.cleanImageMap.putIfAbsent(imgId, this.imageMap.get(imgId));
                // match category
                this.cleanCategoryMap.putIfAbsent(categoryId, this.categoryMap.get(categoryId));
                // match license
                this.cleanLicenseMap.putIfAbsent(this.imageMap.get(imgId).getLicense(),
                        this.licenseMap.get(this.imageMap.get(imgId).getLicense()));
            }
        }

        // verify if license has duplicate key

        // TODO debug
        System.out.println("clean categories: " + this.cleanCategoryMap.size());
        System.out.println("clean image: " + this.cleanImageMap.size());
        System.out.println("clean annotations: " + this.cleanAnnotationMap.size());
        System.out.println("clean license: " + this.cleanLicenseMap.size());

        // build new coco entity
        CocoEntity rtn = new CocoEntity();

        // license
        ArrayList<CocoLicense> licenseList = new ArrayList<>(this.cleanLicenseMap.values());
        // license - renew id
        for(int i = 0; i < licenseList.size(); i++) {
            // backup old key
            licenseKeyMap.put(licenseList.get(i).getId(), i);
            // assign new one
            licenseList.get(i).setId(i);
        }

        // category
        ArrayList<CocoCategory> categoryList = new ArrayList<>(this.cleanCategoryMap.values());
        // category - renew id
        for(int i = 0; i < categoryList.size(); i++) {
            // backup old key
            categoryKeyMap.put(categoryList.get(i).getId(), i + 1);
            // assign new one
            categoryList.get(i).setId(i + 1);
        }

        // image
        ArrayList<CocoImage> imageList = new ArrayList<>(this.cleanImageMap.values());
        // image - renew id
        for(int i = 0; i < imageList.size(); i++) {

            CocoImage cI = imageList.get(i);

            // backup old key
            imageKeyMap.put(cI.getId(), i + 1);
            // assign new one
            cI.setId(i + 1);
            // assign new license key
            cI.setLicense(this.licenseKeyMap.get(cI.getLicense()));
        }

        // annotation
        ArrayList<CocoAnnotation> annotationList = new ArrayList<>(this.cleanAnnotationMap.values());
        // annotation - renew id
        for(int i = 0; i < annotationList.size(); i++) {

            CocoAnnotation cA = annotationList.get(i);

            // backup old key
            annotationKeyMap.put(cA.getId(), i + 1);
            // assign new one
            cA.setId(i + 1);
            // assign new image id
            cA.setImageId(this.imageKeyMap.get(cA.getImageId()));
            // assign new category id
            cA.setCategoryId(this.categoryKeyMap.get(cA.getCategoryId()));
        }

        // assemble
        rtn.setLicenses(licenseList);
        rtn.setInfo(this.cocoEntity.getInfo());
        rtn.setCategories(categoryList);
        rtn.setImages(imageList);
        rtn.setAnnotations(annotationList);

        return rtn;
    }

    /**
     * according to annotation, verify if image, category, license are exist
     * @param imgId image id
     * @param categoryId category id
     * @return is match
     */
    private boolean isKeyMatched(int imgId, int categoryId) {
        return null != this.imageMap.get(imgId)
                && null != this.categoryMap.get(categoryId)
                && null != this.licenseMap.get(this.imageMap.get(imgId).getLicense());
    }

}
