package pa.iscde.demo;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pa.iscde.codegenerator.extensability.CodeStringGeneratorService;
import pa.iscde.codegenerator.service.CodeGeneratorService;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	private CodeGeneratorService codeGeneratorService;
	private JavaEditorServices javaService;
	private CodeStringGeneratorService stringGeneratorService;
	private static Activator instance;

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		instance = this;
		Activator.context = bundleContext;
		ServiceReference<CodeGeneratorService> serviceReference = bundleContext.getServiceReference(CodeGeneratorService.class);
		codeGeneratorService = bundleContext.getService(serviceReference);
		ServiceReference<JavaEditorServices> serviceReference2 = bundleContext.getServiceReference(JavaEditorServices.class);
		javaService = bundleContext.getService(serviceReference2);
		ServiceReference<CodeStringGeneratorService> serviceReference3 = bundleContext.getServiceReference(CodeStringGeneratorService.class);
		stringGeneratorService = bundleContext.getService(serviceReference3);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}
	
	public static Activator getInstance() {
		return instance;
	}

	public CodeGeneratorService getCodeGeneratorService() {
		return codeGeneratorService;
	}
	
	public JavaEditorServices getJavaService() {
		return javaService;
	}

	public CodeStringGeneratorService getStringGeneratorService() {
		return stringGeneratorService;
	}
	
}
