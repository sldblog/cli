package com.structurizr.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class StructurizrCliApplication implements CommandLineRunner {

	private static final String PUSH_COMMAND = "push";
	private static final String PULL_COMMAND = "pull";
	private static final String EXPORT_COMMAND = "export";

	@Autowired
	private ApplicationContext context;

	@Override
	public void run(String... args) throws Exception {
		String version = context.getBean(BuildProperties.class).getVersion();
		System.out.println("Structurizr CLI v" + version);

		if (args == null || args.length == 0) {
			printUsageMessageAndExit();
		}

		if (PUSH_COMMAND.equalsIgnoreCase(args[0])) {
			new PushCommand().run(Arrays.copyOfRange(args, 1, args.length));
		} else if (PULL_COMMAND.equalsIgnoreCase(args[0])) {
			new PullCommand().run(Arrays.copyOfRange(args, 1, args.length));
		} else if (EXPORT_COMMAND.equalsIgnoreCase(args[0])) {
			new ExportCommand().run(Arrays.copyOfRange(args, 1, args.length));
		} else {
			printUsageMessageAndExit();
		}
	}

	private void printUsageMessageAndExit() {
		System.out.println("Usage: structurizr push|pull|export [options]");
		System.exit(1);
	}

	public static void main(String[] args) {
		SpringApplication.run(StructurizrCliApplication.class, args);
	}

}