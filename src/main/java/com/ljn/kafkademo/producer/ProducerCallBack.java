package com.ljn.kafkademo.producer;

import com.google.gson.Gson;
import com.ljn.kafkademo.common.MessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
public class ProducerCallBack implements ListenableFutureCallback<SendResult<String, MessageEntity>> {

    private final long startTime;
    private final String key;
    private final MessageEntity message;

    private final Gson gson = new Gson();

    public ProducerCallBack(long startTime, String key, MessageEntity message) {
        this.startTime = startTime;
        this.key = key;
        this.message = message;
    }

    @Override
    public void onFailure(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onSuccess(SendResult<String, MessageEntity> result) {
        if (result == null) {
            return;
        }
        long elapsedTime = System.currentTimeMillis() - startTime;
        RecordMetadata metadata = result.getRecordMetadata();
        if (metadata != null) {
            StringBuilder logInfo = new StringBuilder();
            logInfo.append("message(")
                    .append("key = ").append(key).append(",")
                    .append("message = ").append(gson.toJson(message)).append(",")
                    .append("send to partition (").append(metadata.partition()).append("),")
                    .append("with offset (").append(metadata.offset()).append("),")
                    .append("in ").append(elapsedTime).append(" ms)");
            log.info(logInfo.toString());
        }
    }
}
