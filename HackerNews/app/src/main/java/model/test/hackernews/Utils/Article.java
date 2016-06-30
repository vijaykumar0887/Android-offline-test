package model.test.hackernews.Utils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class to handle news response
 */

public class Article {

    @SerializedName("by")    
    private String mBy;
    @SerializedName("descendants")
    private Integer mDescendants;
    @SerializedName("id")
    private Integer mId;
    @SerializedName("kids")
    private List<Integer> mKids = new ArrayList<Integer>();
    @SerializedName("parts")
    private List<Integer> mParts = new ArrayList<Integer>();
    @SerializedName("score")
    private Integer mScore;
    @SerializedName("text")
    private String mText;
    @SerializedName("time")
    private Integer mTime;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("type")
    private String mType;
    @SerializedName("url")
    private String mUrl;

    public String getBy() {
        return mBy;
    }

    public void setBy(String by) {
        mBy = by;
    }

    public Integer getDescendants() {
        return mDescendants;
    }

    public void setDescendants(Integer descendants) {
        mDescendants = descendants;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public List<Integer> getKids() {
        return mKids;
    }

    public void setKids(List<Integer> kids) {
        mKids = kids;
    }

    public List<Integer> getParts() {
        return mParts;
    }

    public void setParts(List<Integer> parts) {
        mParts = parts;
    }

    public Integer getScore() {
        return mScore;
    }

    public void setScore(Integer score) {
        mScore = score;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public Integer getTime() {
        return mTime;
    }

    public void setTime(Integer time) {
        mTime = time;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}