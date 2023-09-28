package com.study.monitor;

import com.study.monitor.monitor.Monitor;
import com.study.monitor.monitor.impl.LogMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@EnableFeignClients
@EnableScheduling
@SpringBootApplication
public class MonitorAgentApplication implements CommandLineRunner {

	@Autowired
	private List<Monitor> monitorList;
	public static void main(String[] args) {
		SpringApplication.run(MonitorAgentApplication.class, args);
	}
	
	@Scheduled(fixedRate = 1000)
	private void appendRandomStringInTestFile(){
		String filePath = "test.log";
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter writer = new FileWriter(file, true);
			if(System.currentTimeMillis()%8 == 0){
				writer.write("[Error] test\n");
			} else {
				writer.write(UUID.randomUUID().toString() + "\n");
			}
			writer.close();
		} catch (IOException e) {
			// Handle exception
		}
	}

	@Override
	public void run(String... args) throws Exception {
		monitorList.forEach(Monitor::monitor);
	}
}
