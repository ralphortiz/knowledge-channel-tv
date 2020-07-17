package com.example.kch_androiddev;

public class Show {
    private String title;
    private String description;
    private String thumbnail;
    private String category;
    private String cover_art;
    private String video_file;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public String getCover_art() { return cover_art; }

    public void setCover_art(String cover_art) { this.cover_art = cover_art; }

    public String getVideo_file() { return video_file; }

    public void setVideo_file(String video_file) { this.video_file = video_file; }

    public Show(String title, String description, String thumbnail, String category, String video_file, String cover_art) {
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.category = category;
        this.video_file = video_file;
        this.cover_art = cover_art;
    }

    public Show(String title, String description, String thumbnail) {
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public Show(String title, String thumbnail) {
        this.title = title;
        this.thumbnail = thumbnail;
    }
    public Show(String title) {
        this.title = title;
    }

    public Show() {
    }

//    @Override
//    public String toString() {
//        return title;
//    }
}
