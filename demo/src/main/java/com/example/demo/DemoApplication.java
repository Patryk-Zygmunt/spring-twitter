package com.example.demo;

import com.example.demo.doku.DokuService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DemoApplication  implements CommandLineRunner {

	public static void main(String[] args) {
		
	    SpringApplication.run(DemoApplication.class, args);
	}

    @Override public void run(  String... args) throws Exception {
        DokuService s=  new DokuService();
        //System.out.print(s.getDoku());
 
   int dayOfWeek = 6;
switch(dayOfWeek) {
 case 0:
 System.out.println("Sunday");
 default:
 System.out.println("Weekday");
 case 6:
 System.out.println("Saturday");
 break;
}
            
	    
	    
    }
}
