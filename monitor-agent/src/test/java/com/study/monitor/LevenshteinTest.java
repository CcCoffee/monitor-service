package com.study.monitor;

import com.study.monitor.util.LevenshteinUtil;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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


}
