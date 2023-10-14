package com.study.monitor;

import com.study.monitor.util.LevenshteinUtil;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LevenshteinTest {

    @Test
    public void test(){
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            System.out.println(LevenshteinUtil.calculateSimilarityScore("2023-09-29 09:06:48.914 ERROR 6044 --- [ool-12-thread-1] c.s.monitor.monitor.impl.ProcessMonitor  : NullPointerException e\n",
                    "2023-09-29 09:17:23.637 ERROR 25692 --- [ool-13-thread-1] c.s.monitor.monitor.impl.ProcessMonitor  : NullPointerException e\n"));
            System.out.println(LevenshteinUtil.calculateSimilarityScore("2023-09-29 09:06:48.914 ERROR 6044 --- [ool-12-thread-1] c.s.monitor.monitor.impl.ProcessMonitor  : NullPointerException e\n",
                    "2023-09-29 09:18:32.862  INFO 25692 --- [ionShutdownHook] com.netflix.discovery.DiscoveryClient    : Completed shut down of DiscoveryClient\n"));
        }
        System.out.println("spent time: " + (System.currentTimeMillis() - startTime) + "ms");
    }


    @Test
    public void testLongLog() {
        String firstLog = "2023-10-14 15:30:36.895 ERROR 14991 --- [nio-8090-exec-4] o.a.coyote.http11.Http11NioProtocol      : Error reading request, ignored\n" +
                "\njava.lang.NullPointerException: Cannot invoke \"org.apache.catalina.Context.decrementInProgressAsyncCount()\" because \"this.context\" is null\n" +
                "\tat org.apache.catalina.core.AsyncContextImpl.decrementInProgressAsyncCount(AsyncContextImpl.java:440) ~[tomcat-embed-core-9.0.80.jar:9.0.80]\n" +
                "\tat org.apache.coyote.AsyncStateMachine.asyncPostProcess(AsyncStateMachine.java:295) ~[tomcat-embed-core-9.0.80.jar:9.0.80]\n" +
                "\tat org.apache.coyote.AbstractProcessor.asyncPostProcess(AbstractProcessor.java:196) ~[tomcat-embed-core-9.0.80.jar:9.0.80]\n" +
                "\tat org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:78) ~[tomcat-embed-core-9.0.80.jar:9.0.80]\n" +
                "\tat org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:926) ~[tomcat-embed-core-9.0.80.jar:9.0.80]\n" +
                "\tat org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1790) ~[tomcat-embed-core-9.0.80.jar:9.0.80]\n" +
                "\tat org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:52) ~[tomcat-embed-core-9.0.80.jar:9.0.80]\n" +
                "\tat org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1191) ~[tomcat-embed-core-9.0.80.jar:9.0.80]\n" +
                "\tat org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659) ~[tomcat-embed-core-9.0.80.jar:9.0.80]\n" +
                "\tat org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61) ~[tomcat-embed-core-9.0.80.jar:9.0.80]\n" +
                "\tat java.base/java.lang.Thread.run(Thread.java:1589) ~[na:na]\n" +
                "\n" +
                "f17231c5-7f41-4c27-8445-c9ed37377831\n" +
                "92ae9bda-fb94-4c95-a20a-63c6c7c92d3a\n" +
                "8d18c059-ca80-431e-afa2-8e4e2b1ce365";

        String secondLog = "2023-10-14 15:44:42.695 ERROR 2991 --- [nio-8090-exec-4] o.a.coyote.http11.Http11NioProtocol      : Error reading request, ignored\n" +
                "\njava.lang.NullPointerException: Cannot invoke \"org.apache.catalina.Context.a()\" because \"this.context\" is null\n" +
                "\tat org.apache.catalina.core.AsyncContextImpl.decrementInProgressAsyncCount(AsyncContextImpl.java:440) ~[tomcat-embed-core-9.0.80.jar:9.0.80]\n" +
                "\tat org.apache.coyote.AsyncStateMachine.asyncPostProcess(AsyncStateMachine.java:295) ~[tomcat-embed-core-9.0.80.jar:9.0.80]\n" +
                "\tat org.apache.coyote.AbstractProcessor.asyncPostProcess(AbstractProcessor.java:196) ~[tomcat-embed-core-9.0.80.jar:9.0.80]\n" +
                "\tat org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:78) ~[tomcat-embed-core-9.0.80.jar:9.0.80]\n" +
                "\tat org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:926) ~[tomcat-embed-core-9.0.80.jar:9.0.80]\n" +
                "\tat org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1790) ~[tomcat-embed-core-9.0.80.jar:9.0.80]\n" +
                "\tat org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:52) ~[tomcat-embed-core-9.0.80.jar:9.0.80]\n" +
                "\tat org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1191) ~[tomcat-embed-core-9.0.80.jar:9.0.80]\n" +
                "\tat org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659) ~[tomcat-embed-core-9.0.80.jar:9.0.80]\n" +
                "\tat org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61) ~[tomcat-embed-core-9.0.80.jar:9.0.80]\n" +
                "\tat java.base/java.lang.Thread.run(Thread.java:1589) ~[na:na]\n" +
                "\n" +
                "396c5f48-8b16-4496-8fb9-27421cc068c1\n" +
                "9e3af223-bd8b-44f6-b368-966204db8ff4";
        assertTrue(LevenshteinUtil.isSimilarLog(firstLog, secondLog));

    }
}
