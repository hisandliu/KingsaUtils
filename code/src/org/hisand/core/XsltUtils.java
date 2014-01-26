package org.hisand.core;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.Writer;

//import javax.servlet.ServletContext;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

//import org.dom4j.io.DocumentResult;

public class XsltUtils {

	//private ServletContext servletContext;

//	public XsltUtils(ServletContext context) {
//		super();
//		servletContext = context;
//	}
	
	public XsltUtils() {
		super();
	}

	private Transformer getTransformer(String xsltFile) throws Exception {
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
//		if (servletContext != null) {
//			InputStream is = servletContext.getResourceAsStream(xsltFile);
//			if (is==null)
//				throw new Exception("xslt file not found: " + xsltFile) ;
//			transformer = tFactory.newTransformer(new StreamSource(is));
//		} else {
//			transformer = tFactory.newTransformer(new StreamSource(xsltFile));
//		}
		transformer = tFactory.newTransformer(new StreamSource(xsltFile));
		return transformer;
	}

	public void transformToFile(String xsltFile, String oriXmlFile,
			String outXmlFile) throws Exception {
		Transformer transformer = getTransformer(xsltFile);
		StreamSource source = new StreamSource(oriXmlFile);
		StreamResult result = new StreamResult(new FileOutputStream(outXmlFile));
		transformer.transform(source, result);
	}

	public void transform(String xsltFile, String oriXmlFile, Writer writer)
			throws Exception {
		Transformer transformer = getTransformer(xsltFile);
		transformer.transform(new StreamSource(oriXmlFile), new StreamResult(
				writer));
	}

	public void transform(String xsltFile, String oriXmlFile, OutputStream out)
			throws Exception {
		Transformer transformer = getTransformer(xsltFile);
		transformer.transform(new StreamSource(oriXmlFile), new StreamResult(
				out));
	}

	public void transformFromXmlContentToFile(String xsltFile,
			String oriXmlContent, String outXmlFile) throws Exception {
		Transformer transformer = getTransformer(xsltFile);
		StringReader reader = new StringReader(oriXmlContent);
		transformer.transform(new StreamSource(reader), new StreamResult(
				new FileOutputStream(outXmlFile)));
		reader.close();
	}

	public void transformFromXmlContent(String xsltFile, String oriXmlContent,
			Writer writer) throws Exception {
		Transformer transformer = getTransformer(xsltFile);
		StringReader reader = new StringReader(oriXmlContent);
		StreamSource source = new StreamSource(reader);
		transformer.transform(source, new StreamResult(writer));
		reader.close();
	}
	
//	public void transformToDocument(String xsltFile,
//			String oriXmlFile, DocumentResult documentResult) throws Exception {
//		Transformer transformer = getTransformer(xsltFile);
//		StreamSource source = new StreamSource(oriXmlFile);
//		transformer.transform(source, documentResult);
//	}
//	
//	public void transformFromXmlContentToDocument(String xsltFile,
//			String oriXmlContent, DocumentResult documentResult) throws Exception {
//		Transformer transformer = getTransformer(xsltFile);
//		StringReader reader = new StringReader(oriXmlContent);
//		transformer.transform(new StreamSource(reader), documentResult);
//		reader.close();
//	}
//	
//	public void transformFromXmlFileToDocument(String xsltFile,
//			String filename, DocumentResult documentResult) throws Exception {
//		Transformer transformer = getTransformer(xsltFile);
//		StreamSource ss = new StreamSource(filename);
//		transformer.transform(ss, documentResult);
//		//reader.close();
//	}
	
//	public void transformFromXmlStream(String xsltFile, Reader reader,
//			Writer writer) throws Exception {
//		Transformer transformer = getTransformer(xsltFile);
//		transformer.transform(new StreamSource(reader),
//				new StreamResult(writer));
//		reader.close();
//	}

	public void transformFromXmlContent(String xsltFile, String oriXmlContent,
			OutputStream out) throws Exception {
		Transformer transformer = getTransformer(xsltFile);
		StringReader reader = new StringReader(oriXmlContent);
		transformer.transform(new StreamSource(reader), new StreamResult(out));
		reader.close();
	}

	private static void testSimple() throws Exception {
		// Use the static TransformerFactory.newInstance() method to instantiate
		// a TransformerFactory. The javax.xml.transform.TransformerFactory
		// system property setting determines the actual class to instantiate --
		// org.apache.xalan.transformer.TransformerImpl.
		TransformerFactory tFactory = TransformerFactory.newInstance();

		// Use the TransformerFactory to instantiate a Transformer that will
		// work with
		// the stylesheet you specify. This method call also processes the
		// stylesheet
		// into a compiled Templates object.
		Transformer transformer = tFactory.newTransformer(new StreamSource(
				"c:/test/birds.xsl"));

		// Use the Transformer to apply the associated Templates object to an
		// XML document
		// (foo.xml) and write the output to a file (foo.out).
		transformer.transform(new StreamSource("c:/test/birds.xml"),
				new StreamResult(new FileOutputStream("c:/test/birds.out")));

		System.out
				.println("************* The result is in birds.out *************");
	}

	public static void main(String[] args) {
		try {
			XsltUtils.testSimple();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
