package com.spark.submitter.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InputStreamReaderRunnable implements Runnable {

	private final BufferedReader reader;

	private final String name;

	public InputStreamReaderRunnable(final InputStream is, final String name) {
		this.reader = new BufferedReader(new InputStreamReader(is));
		this.name = name;
	}

	@Override
	public void run() {
		System.out.println("InputStream " + this.name + ":");
		try {
			String line = this.reader.readLine();
			while (line != null) {
				System.out.println(line);
				line = this.reader.readLine();
			}
			this.reader.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
