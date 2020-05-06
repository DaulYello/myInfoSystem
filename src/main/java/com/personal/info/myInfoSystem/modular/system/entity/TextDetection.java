package com.personal.info.myInfoSystem.modular.system.entity;

import lombok.Data;

/**
 * src处理图片文字的model
 */
@Data
public class TextDetection {

    private String detectedText;

    private String advancedInfo;

    private Long confidence;
}
