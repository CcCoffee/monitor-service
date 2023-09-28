package com.study.monitor.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/log")
public class LogController {

    private String getApplicationLogPath(String application) {
        // Implement the logic to get the path of the log file for the given application
        // Return the path as a string
        return "test.log";
    }

    @GetMapping(value = "/tail", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter tail(@RequestParam(required = false) String application, @RequestParam(required = true) Integer lineNumber) {
        // Implement the logic to tail the latest logs and send them as Server-Sent Events
        // Create and return an instance of SseEmitter
        SseEmitter emitter = new SseEmitter();
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        Path logFile = Path.of(getApplicationLogPath(application));
        AtomicLong lastLineCount = null;
        try {
            lastLineCount = new AtomicLong(Files.lines(logFile).count() - lineNumber);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        AtomicLong finalLastLineCount = lastLineCount;
        executorService.scheduleAtFixedRate(() -> {
            try {
                long linesToSkip = Math.max(0, finalLastLineCount.get());
                Files.lines(logFile)
                        .skip(linesToSkip)
                        .forEach(line -> {
                            try {
                                emitter.send(line, MediaType.TEXT_PLAIN);
                            } catch (IOException e) {
                                // Handle exception
                            }
                        });
                finalLastLineCount.set(Files.lines(logFile).count());
            } catch (IOException e) {
                // Handle exception
            }
        }, 0, 1, TimeUnit.SECONDS);
        return emitter;
    }
}

                                                                                                                         