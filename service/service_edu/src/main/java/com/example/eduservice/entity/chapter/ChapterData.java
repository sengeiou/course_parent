package com.example.eduservice.entity.chapter;

import lombok.Data;

import java.util.List;

/*
章节
 */
@Data
public class ChapterData {
    private String id;
    private String title;
    private List<VideoData> children ;
}
