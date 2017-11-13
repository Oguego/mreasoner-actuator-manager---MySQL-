package com.va4sh.logreader;

import com.va4sh.logreader.implementation.SwitchableDevice;
import com.va4sh.logreader.implementation.VeraLogTest;
import com.va4sh.ssh.SSHConfigs;
import com.va4sh.utils.Constants;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	public class Logger {
		VeraLogReader vlr;
		SSHConfigs conf = SSHConfigs.getInstance();

		public Logger() {
			vlr = VeraLogReader.getInstance();
		}
		
		public void start() {
			ArrayList<String> possibleKettleFails = new ArrayList<>();
			possibleKettleFails.add("the water is hot"); // time ok
			possibleKettleFails.add("the water is already hot"); // time fail
			possibleKettleFails.add("you forgot to switch it on"); // No consumption
			possibleKettleFails.add("it is empty or the water inside is already hot"); // Consumption finished sooner than expected,

			ArrayList<String> possibleMicroFails = new ArrayList<>();
			possibleMicroFails.add("the food is ready"); // time ok
			possibleMicroFails.add("something went wrong"); // time fail
			possibleMicroFails.add("you forgot to switch it on"); // No consumption
			possibleMicroFails.add("something went wrong"); // Consumption finished sooner than expected

//			VeraLogTest vlt = new VeraLogTest();
			SwitchableDevice sd = new SwitchableDevice("16");
						
			conf.setUsername("root");
			conf.setPassword("smarthouse123");
			conf.setPort(22);

//			vlr.registerObserver(vlt);
			vlr.registerObserver(sd);
			
			vlr.start();
		}

		public void stop() {
			vlr.stop();
		}
	}

	public void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Main of the application
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Main m = new Main();
		Logger log = m.new Logger();
		
		SSHConfigs conf = SSHConfigs.getInstance();
		conf.setUsername("root");
		conf.setPassword("smarthouse123");
		conf.setPort(22);

		Constants.setLocalServerIP("10.12.102.58");
		Constants.hostname = "10.12.102.156";
		
		log.start();
		
		System.out.println("insert a number to close");
		in.nextInt();
		log.stop();
		in.close();
		System.out.println("ok. Bye");
	}


}
