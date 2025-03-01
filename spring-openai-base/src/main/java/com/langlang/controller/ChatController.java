package com.langlang.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class ChatController {

    @Resource
    private OpenAiChatModel openAiChatModel;

    @RequestMapping(value = "/ai/chat")
    public String chat(@RequestParam("message") String message) {
        String call = openAiChatModel.call(message);
        System.out.println(call);
        return call;
    }

    /**
     * Prompt 设置选择的模型版本
     * 设置温度: 温度越高, 回复的内容更具有多样性和创新性, 模型生成文本更愿意冒险, 可能让文本的连贯性和合理性降低. 例如进行写作, 诗歌创作需要灵感和创新的时候, 较高的温度可以创新独特的内容.
     * 温度越低, 使概率分布更加尖锐，增加高概率事件的概率，同时降低低概率事件的概率。这使得模型在生成文本时倾向于选择更确定、更常见的词汇, 生成的文本更加流畅和合理, 较高的准确性和连贯性, 但
     * 可能会缺乏创意. 比如在生成技术文档、法律文件、科学研究报告等对准确性要求高的文本时，使用较低的温度能确保内容准确无误
     * 温度适中时：能在一定程度上平衡文本的连贯性和多样性，既不会过于保守导致内容平淡，也不会过于冒险而使文本失去逻辑，适用于大多数普通的文本生成场景，如日常对话、一般性的文章写作等
      */

    @RequestMapping(value = "/ai/model")
    public Object model(@RequestParam("message") String message) {
        // ChatResponse response = openAiChatModel.call(new Prompt(message,
        //         OpenAiChatOptions.builder().model("gpt-3.5-turbo").temperature(0.4).build()));

        ChatResponse response = openAiChatModel.call(new Prompt(message,
                ChatOptions.builder().model("gpt-3.5-turbo").temperature(0.2).build()));

        System.out.println(response);
        return response.getResult().getOutput();
    }


    /**
     * 流式接口
     * @param message
     * @return
     */
    @RequestMapping(value = "/ai/stream")
    public Object stream(@RequestParam("message") String message) {
        Flux<ChatResponse> flux = openAiChatModel.stream(new Prompt(message));

        flux.toStream().forEach(chatResponse -> {
            System.out.println(chatResponse.getResult());
            if(chatResponse.getResult() != null) {
                System.out.println(chatResponse.getResult().getOutput());
                if(chatResponse.getResult().getOutput() != null) {
                    // System.out.println("实际序列:  " + chatResponse.getResult().getOutput().getContent());
                    // 版本升级后 getContent 去掉了, 可以使用getText 方法代替
                    System.out.println("实际序列:  " + chatResponse.getResult().getOutput().getText());
                }
            }
        });

        return flux.collectList();
    }


}
