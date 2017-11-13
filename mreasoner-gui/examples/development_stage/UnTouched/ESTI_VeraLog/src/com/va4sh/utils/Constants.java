package com.va4sh.utils;

import java.util.ArrayList;
import java.util.HashMap;

public class Constants {
	public 			static String hostname = "";
	public final 	static int actions_port = 3480;
	public 			enum SWITCH_STATE {SWITCH_ON, SWITCH_OFF}
	private 		static String LOCAL_SERVER_IP = "";
	public final	static int LOCAL_SERVER_PORT = 6020;
	private			static String position = "";
	
	public static void setLocalServerIP(String ip ) {
		if( LOCAL_SERVER_IP.equals(""))
			LOCAL_SERVER_IP = ip;
	}
	
	public static String getLocalServerIP() {
		return LOCAL_SERVER_IP;
	}
	
	public static HashMap<String, String> deviceID;
	public static HashMap<String, String> deviceName;
	public static HashMap<String, String> deviceEnvironment;
	public static HashMap<String, String> deviceState;
	public static HashMap<String, String> deviceStatus;
	public static ArrayList<String> ignoredDevicePosition;
	public static ArrayList<String> dangerousDevices;
	
	static {
//		[fridge, kitchen back door, garden door]
		dangerousDevices = new ArrayList<>();
		dangerousDevices.add("16");
		dangerousDevices.add("15");
		dangerousDevices.add("11");
		//TEST
		dangerousDevices.add("7");
		
		ignoredDevicePosition = new ArrayList<>();
		ignoredDevicePosition.add("21");
		ignoredDevicePosition.add("20");
		ignoredDevicePosition.add("19");
		ignoredDevicePosition.add("6");
		ignoredDevicePosition.add("39");


		deviceEnvironment = new HashMap<>();
		deviceEnvironment.put("21", "kitchen");
		deviceEnvironment.put("20", "kitchen");
		deviceEnvironment.put("19", "kitchen");
		deviceEnvironment.put("18", "kitchen");
		deviceEnvironment.put("17", "kitchen");
		deviceEnvironment.put("16", "kitchen");
		deviceEnvironment.put("15", "kitchen");
		deviceEnvironment.put("14", "kitchen");
		deviceEnvironment.put("29", "kitchen");
		deviceEnvironment.put("6", "bedroom");
		deviceEnvironment.put("7", "bedroom");
		deviceEnvironment.put("8", "bedroom");
		deviceEnvironment.put("9", "bedroom");
		deviceEnvironment.put("24", "bedroom");
		deviceEnvironment.put("25", "bedroom");
		deviceEnvironment.put("26", "bedroom");
		deviceEnvironment.put("27", "bedroom");
		deviceEnvironment.put("11", "living room");
		deviceEnvironment.put("12", "living room");
		deviceEnvironment.put("10", "living room");
		deviceEnvironment.put("43", "living room");
		deviceEnvironment.put("44", "living room");
		deviceEnvironment.put("45", "living room");
		deviceEnvironment.put("30", "toilet");
		deviceEnvironment.put("31", "toilet");
		deviceEnvironment.put("32", "toilet");
		deviceEnvironment.put("33", "toilet");
		deviceEnvironment.put("35", "corridor");
		deviceEnvironment.put("36", "corridor");
		deviceEnvironment.put("13", "corridor");
		deviceEnvironment.put("34", "shower");
		deviceEnvironment.put("69", "kitchen");
		deviceEnvironment.put("70", "kitchen");
		deviceEnvironment.put("71", "kitchen");

	
		deviceID = new HashMap<>();
		deviceID.put("21", "Radio");
		deviceID.put("20", "Microwave");
		deviceID.put("19", "Kettle");
		deviceID.put("18", "CupboardKitchen4");
		deviceID.put("17", "CupboardKitchen6");
		deviceID.put("16", "Fridge");
		deviceID.put("15", "KitchenBackDoor");
		deviceID.put("14", "KitchenDoor");
		deviceID.put("6", "LightBedroom");
		deviceID.put("7", "DrawLeftWardrobe");
		deviceID.put("8", "DrawRighWardrobe");
		deviceID.put("9", "BedroomDoor");
		deviceID.put("24", "BedroomPIR");
		deviceID.put("11", "GardenDoor");
		deviceID.put("12", "LivingRoomDoor");
		deviceID.put("10", "LivingRoomPIR");
		deviceID.put("39", "LivingRoomLight");
		deviceID.put("30", "ToiletMove");
		deviceID.put("35", "CorridorFrontMove");
		deviceID.put("36", "CorridorMove");
		deviceID.put("13", "FronDoor");
		deviceID.put("34", "ShowerMove");
		deviceID.put("24", "BedroomMulti");
		deviceID.put("25", "BedroomTemperature");
		deviceID.put("26", "BedroomLight");
		deviceID.put("27", "BedroomHumidity");
		deviceID.put("29", "KitchenCupboard");
		deviceID.put("43", "LivingRoomPressure");
		deviceID.put("44", "LivingRoomMovement1");
		deviceID.put("45", "LivingRoomMovement2");
		deviceID.put("31", "ToiletTemperature");
		deviceID.put("32", "ToiletLightSensor");
		deviceID.put("33", "ToiletHumidity");
		deviceID.put("69", "KitchenMove");
		deviceID.put("70", "KitchenLight");
		deviceID.put("71", "KitchenTemp");
		

		deviceName = new HashMap<>();
		deviceName.put("Radio", "21");
		deviceName.put("Microwave", "20");
		deviceName.put("Kettle", "19");
		deviceName.put("Cupboard4", "18");
		deviceName.put("Cupboard6", "17");
		deviceName.put("Fridge", "16");
		deviceName.put("KitchenBackDoor", "15");
		deviceName.put("KitchenEntranceDoor", "14");
		deviceName.put("BedroomLight", "6");
		deviceName.put( "DrawLeftWardrobe", "7");
		deviceName.put("DrawRightWardrobe", "8");
		deviceName.put("BedroomDoor", "9");
		deviceName.put("bedroomPIR", "24");
		deviceName.put("GardenDoor", "11");
		deviceName.put("LivingRoomLight", "39");
		deviceName.put("LivingRoomDoor", "12");
		deviceName.put("LivingRoomPIR", "10");
		deviceName.put("ToiletMove", "30");
		deviceName.put("CorridorFrontMove", "35");
		deviceName.put("CorridorMove", "36");
		deviceName.put("FrontDoor", "13");
		deviceName.put("ShowerMove", "34");
		deviceName.put("BedroomTemperature", "25");
		deviceName.put("BedroomLight", "26");
		deviceName.put("BedroomHumidity","27");
		deviceName.put("Cupboard3","29");
		deviceName.put("LivingRoomPressure", "43");
		deviceName.put("LivingRoommMovement",  "44");
		deviceName.put("LivingRoommMovement", "45");
		deviceName.put("ToiletTemperature", "31");
		deviceName.put("ToiletLight", "32");
		deviceName.put("ToiletHumidity", "33");
		deviceName.put("KitchenMotion", "69");
		deviceName.put("KitchenLight", "70");
		deviceName.put("KitchenTemperature", "71");
		
		deviceStatus = new HashMap<>();
		deviceStatus.put("21", null);
		deviceStatus.put("20", null);
		deviceStatus.put("19", null);
		deviceStatus.put("18", null);
		deviceStatus.put("17", null);
		deviceStatus.put("16", null);
		deviceStatus.put("15", null);
		deviceStatus.put("14", null);
		deviceStatus.put("6", null);
		deviceStatus.put("7", null);
		deviceStatus.put("8", null);
		deviceStatus.put("9", null);
		deviceStatus.put("24", null);
		deviceStatus.put("11", null);
		deviceStatus.put("39", null);
		deviceStatus.put("12", null);
		deviceStatus.put("10", null);
		deviceStatus.put("30", null);
		deviceStatus.put("35", null);
		deviceStatus.put("36", null);
		deviceStatus.put("13", null);
		deviceStatus.put("25", null);
		deviceStatus.put("26", null);
		deviceStatus.put("27", null);
		deviceStatus.put("29", null);
		deviceStatus.put("43", null);
		deviceStatus.put("44", null);
		deviceStatus.put("45", null);
		deviceStatus.put("31", null);
		deviceStatus.put("32", null);
		deviceStatus.put("33", null);
		deviceStatus.put("69", null);
		deviceStatus.put("70", null);
		deviceStatus.put("71", null);
		
		deviceState = new HashMap<>();
		deviceState.put("21", null);
		deviceState.put("20", null);
		deviceState.put("19", null);
		deviceState.put("18", null);
		deviceState.put("17", null);
		deviceState.put("16", null);
		deviceState.put("15", null);
		deviceState.put("14", null);
		deviceState.put("6", null);
		deviceState.put("7", null);
		deviceState.put("8", null);
		deviceState.put("9", null);
		deviceState.put("24", null);
		deviceState.put("11", null);
		deviceState.put("39", null);
		deviceState.put("12", null);
		deviceState.put("10", null);
		deviceState.put("30", null);
		deviceState.put("35", null);
		deviceState.put("36", null);
		deviceState.put("13", null);
		deviceState.put("25", null);
		deviceState.put("26", null);
		deviceState.put("27", null);
		deviceState.put("29", null);
		deviceState.put("43", null);
		deviceState.put("44", null);
		deviceState.put("45", null);
		deviceState.put("31", null);
		deviceState.put("32", null);
		deviceState.put("33", null);
		deviceState.put("69", null);
		deviceState.put("70", null);
		deviceState.put("71", null);
		
	}
}
