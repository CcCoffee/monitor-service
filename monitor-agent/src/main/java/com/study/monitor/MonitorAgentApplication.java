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
import java.util.Date;
import java.util.List;
import java.util.Random;
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

			Random random = new Random();
			if(System.currentTimeMillis()%8 == 0){
				writer.write(new Date().toString() + " ERROR "+ random.nextInt(25692) +" --- [ool-"+ random.nextInt(17) +"-thread-1] c.s.monitor.monitor.impl.ProcessMonitor  : NullPointerException e\n");
			} else if(System.currentTimeMillis()%7 == 0){
                writer.write(new Date().toString() + " ERROR "+ random.nextInt(25692) +" --- [ool-"+ random.nextInt(17) +"-thread-1] c.s.monitor.monitor.MonitorAgentApplication  : test e\n");
			} else if(System.currentTimeMillis()%6 == 0 || System.currentTimeMillis()%5 == 0){
                writer.write(new Date().toString() + "  ERROR "+ random.nextInt(25692) +" --- [trap-executor-0] c.n.d.s.r.aws.ConfigClusterResolver      : Resolving eureka endpoints via configuration\n");
			}else {
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
