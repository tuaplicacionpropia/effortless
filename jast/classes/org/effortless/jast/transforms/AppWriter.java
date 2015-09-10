package org.effortless.jast.transforms;

import java.io.IOException;

import org.effortless.jast.GApp;

public interface AppWriter {

	public void write(GApp app) throws IOException;

}
