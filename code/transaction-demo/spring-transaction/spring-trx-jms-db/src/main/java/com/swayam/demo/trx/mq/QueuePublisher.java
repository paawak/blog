package com.swayam.demo.trx.mq;

import com.swayam.demo.trx.dto.AuthorRequest;

public interface QueuePublisher {

    void publish(AuthorRequest authorRequest);

}