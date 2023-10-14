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
import java.text.SimpleDateFormat;
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
			String pattern = "yyyy-MM-dd HH:mm:ss.SSS";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			Random random = new Random();
			if(System.currentTimeMillis()%8 == 0){
//				writer.write(simpleDateFormat.format(new Date()) + " ERROR "+ random.nextInt(25692) +" --- [ool-"+ random.nextInt(17) +"-thread-1] c.s.monitor.monitor.impl.ProcessMonitor  : NullPointerException e\n");
			} else if(System.currentTimeMillis()%7 == 0){
//                writer.write(simpleDateFormat.format(new Date()) + " ERROR "+ random.nextInt(25692) +" --- [ool-"+ random.nextInt(17) +"-thread-1] c.s.monitor.monitor.MonitorAgentApplication  : test e\n");
			} else if(System.currentTimeMillis()%6 == 0 || System.currentTimeMillis()%5 == 0){
                writer.write(simpleDateFormat.format(new Date()) + " ERROR "+ random.nextInt(25692) +" --- [nio-8090-exec-4] o.a.coyote.http11.Http11NioProtocol      : Error reading request, ignored\n" +
						"\n" +
						"java.lang.NullPointerException: Cannot invoke \"org.apache.catalina.Context.decrementInProgressAsyncCount()\" because \"this.context\" is null\n" +
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
						"\n");
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
