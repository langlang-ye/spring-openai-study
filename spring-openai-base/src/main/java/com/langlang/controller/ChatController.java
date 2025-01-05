package com.langlang.controller;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @Autowired
    private OpenAiChatModel openAiChatModel;


    @RequestMapping(value = "/ai/chat")
    public String chat(@RequestParam("message") String message) {

        System.out.println(openAiChatModel);
        String call = openAiChatModel.call(message);
        System.out.println(call);
        return call;
    }


}
