package org.xmlcml.norma;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.cproject.args.DefaultArgProcessor;
import org.xmlcml.cproject.util.Utils;

public class Norma {

	private static final Logger LOG = Logger.getLogger(Norma.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	public final static String NORMA_RESOURCE_BASE = "/org/xmlcml/norma";
	public final static String NORMA_OUTPUT_RESOURCE = NORMA_RESOURCE_BASE+"/output";
	private DefaultArgProcessor argProcessor;

	public static void main(String[] args) {
            try {
		Norma norma = new Norma();
		norma.run(args);
            } catch (Exception ex) {
                LOG.trace(ex.getMessage());
                LOG.trace(ex.getStackTrace());
                System.err.println(ex.getMessage());
            }
            // Ensure command prompt is on a new line
            // after any runtime outputs
            System.out.println();
	}

	public void run(String[] args) {
		argProcessor = new NormaArgProcessor(args);
		argProcessor.runAndOutput();
	}

	public void run(String args) {
		args = args == null ? null : args.trim();
		argProcessor = new NormaArgProcessor(args.split("\\s+"));
		argProcessor.runAndOutput();
	}

	public DefaultArgProcessor getArgProcessor() {
		return argProcessor;
	}

	/** converts a projectDirectory to a project and the PDFs to SVG
	 * 
	 * @param projectDir
	 */
	public static void convertRawPDFToProjectToSVG(File projectDir) {
                // Ensure fileFilter is cross-platform compatible
                String fileFilterString = Utils.convertPathRegexToCurrentPlatform(".*/(.*)\\.pdf");
		new Norma().run("--project "+projectDir+" --makeProject (\\1)/fulltext.pdf --fileFilter "+fileFilterString);
		new Norma().run("--project " + projectDir + " --input fulltext.pdf "+ " --outputDir " + projectDir + " --transform pdf2svg ");
	}
	
	/** converts a projectDirectory to a project and the PDFs to SVG
	 * 
	 * @param projectDir
	 */
	public static void convertRawTEIXMLToProject(File projectDir) {
		new Norma().run("--project "+projectDir+" --makeProject (\\1)/fulltext.xml --fileFilter .*\\/(.*)\\.xml");
//		new Norma().run("--project " + projectDir + " --input fulltext.tei.xml "+ " --outputDir " + projectDir + " --transform tei2html ");
	}
}
