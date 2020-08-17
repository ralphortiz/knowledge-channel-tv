package com.example.kch_androiddev;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Objects;

public class Show {
    private String title;
    private String description;
    private String thumbnail;
    private String category;
    private String subject;
    private String cover_art;
    private String video_file;

    public Show(String title, String description, String thumbnail, String category, String subject, String cover_art, String video_file) {
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.category = category;
        this.subject = subject;
        this.cover_art = cover_art;
        this.video_file = video_file;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCover_art() {
        return cover_art;
    }

    public void setCover_art(String cover_art) {
        this.cover_art = cover_art;
    }

    public String getVideo_file() {
        return video_file;
    }

    public void setVideo_file(String video_file) {
        this.video_file = video_file;
    }

    public Show(String title, String description, String thumbnail, String category, String video_file, String cover_art) {
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.category = category;
        this.video_file = video_file;
        this.cover_art = cover_art;
    }

//    public Show(String title, String description, String thumbnail) {
//        this.title = title;
//        this.description = description;
//        this.thumbnail = thumbnail;
//    }

    public Show(String title, String cover_art, String video_file, String subject) {
        this.title = title;
        this.cover_art = cover_art;
        this.video_file = video_file;
        this.subject = subject;
    }

    public Show(String title, String thumbnail) {
        this.title = title;
        this.thumbnail = thumbnail;
    }

    public Show(String cover_art) {
        this.cover_art = cover_art;
    }

    public Show() {
    }

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Show show = (Show) o;
//        return Objects.equals(title, show.title) &&
//                Objects.equals(description, show.description) &&
//                Objects.equals(thumbnail, show.thumbnail) &&
//                Objects.equals(category, show.category) &&
//                Objects.equals(cover_art, show.cover_art) &&
//                Objects.equals(video_file, show.video_file);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    @Override
//    public int hashCode() {
//        return Objects.hash(title, description, thumbnail, category, cover_art, video_file);
//    }

    @Override
    public String toString() {
        return title;
    }
}
