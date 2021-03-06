/*
 *
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package cn.mzy.dubboconsumer;

import cn.mzy.dubboconsumer.action.HelloServiceConsumer;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.CountDownLatch;

/**
 * @author: mazhiyong
 * @date: 2021/10/28 4:32 下午
 */
public class ConsumerBootstrap {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();

        HelloServiceConsumer helloServiceConsumer = context.getBean(HelloServiceConsumer.class);
        String hello = helloServiceConsumer.doSayHello("nacos");
        System.out.println("result: " + hello);

        // 如果不想让spring容器退出，可以使用以下代码
        new CountDownLatch(1).await();
    }

    @Configuration
    @EnableDubbo(scanBasePackages = "cn.mzy.dubboconsumer.action")
    @PropertySource("classpath:dubbo-consumer.properties")
    @ComponentScan(value = {"cn.mzy.dubboconsumer.action"})
    static class ConsumerConfiguration {

    }
}
