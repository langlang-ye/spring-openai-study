package com.langlang.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 生成图片
 */
@RestController
public class ImageController {

    @Resource
    private OpenAiImageModel openAiImageModel;

    @RequestMapping("/ai/image")
    private Object image(@RequestParam(value = "message") String msg) {
        System.out.println(openAiImageModel);
        ImageResponse imageResponse = openAiImageModel.call(new ImagePrompt(msg, OpenAiImageOptions.builder()
                .withQuality("hd")
                .withN(1) // 可以一次生成多张图片, dall-e-3 每次只能生成一张
                .withHeight(1024)
                .withWidth(1024)
                .build()));

        System.out.println(imageResponse);
        String url = imageResponse.getResult().getOutput().getUrl();
        // 对图片的地址进行处理

        return imageResponse;
    }





}
