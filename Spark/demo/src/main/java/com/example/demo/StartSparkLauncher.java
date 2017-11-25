package com.example.demo;

import org.apache.spark.launcher.SparkLauncher;

public class StartSparkLauncher {
	//static final Logger THE_LOGGER = Logger.getLogger(App.class);

	public static void main(String[] args) throws Exception {
		
		//final String javaHome = "/usr";
		final String sparkHome = "/usr/local/spark-2.2.0-bin-hadoop2.7";
		final String appResource = "/usr/local/cibussmart.jar";
		final String mainClass = "Recommendation.recom.App";
		
		Process proc = null;
		ReadStream s1 = null;
		ReadStream s2 = null;
		
		SparkLauncher spark = new SparkLauncher()
				.setVerbose(true)
				//.setJavaHome(javaHome)
				.setSparkHome(sparkHome)
				.setAppResource(appResource)
				.setMainClass(mainClass)
				.setMaster("spark://osboxes:7077")
				//.setConf("spark.default.parallelism", "5000")
				//.setConf("spark.network.timeout", "420000")
				.setConf(SparkLauncher.DRIVER_MEMORY, "4g")
				.setConf(SparkLauncher.EXECUTOR_MEMORY, "4g")
				.setConf(SparkLauncher.EXECUTOR_CORES, "2")
				//.setConf("spark.shuffle.consodilateFiles", "true")
				//.setConf("spark.executor.heartbeatInterval", "360000")
				//.setConf(SparkLauncher.DRIVER_EXTRA_JAVA_OPTIONS, "-XX:MarPermSize=1024M Xmx2048m")
				//.setConf("spark.yarn.am.memory", "1g")
				//.setConf("spark.yarn.am.memoryOverhead", "1g")
				//.setConf("spark.driver.memoryOverhead", "2g")
				//.setConf("spark.executor.memoryOverhead", "2g");
				.addAppArgs(args[0]);

		try {
		proc = spark.launch();
		
		s1 = new ReadStream("stdin", proc.getInputStream());
		s2 = new ReadStream("stderr", proc.getErrorStream());
		s1.start();
		s2.start();
		proc.waitFor();
		
		}catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (proc != null)
				proc.destroy();
		}
	}
}
