package com.biziitech.mlfm;

import java.io.PrintStream;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class MasbuyerApplication {

	public static void main(String[] args) {
		//SpringApplication.run(MasbuyerApplication.class, args);
		SpringApplication app=new SpringApplication(MasbuyerApplication.class);
		
		app.setBanner(new Banner() {
			@Override
			public void printBanner(Environment environment, Class<?> sourceClass,
					
			PrintStream out	) {
				out.print("###################################");
				out.print("\n\n\tBiziitech\n\n".toUpperCase());
				out.print("###################################\n");
			}
			
			
		});
		app.run(args);
	}
}
