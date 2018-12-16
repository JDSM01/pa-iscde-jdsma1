package pt.iscte.pidesco.codegenerator.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import pt.iscte.pidesco.codegenerator.service.CodeGeneratorService;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class CodeGeneratorActivator implements BundleActivator {

	private CodeGeneratorService codeGeneratorService;
	private JavaEditorServices javaService;
	private ServiceRegistration<CodeGeneratorService> serviceRef;
	private static CodeGeneratorActivator instance;



	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		instance = this;
		ServiceReference<JavaEditorServices> serviceReference = bundleContext.getServiceReference(JavaEditorServices.class);
		javaService = bundleContext.getService(serviceReference);
		codeGeneratorService = new CodeGeneratorController();
		serviceRef = bundleContext.registerService(CodeGeneratorService.class, codeGeneratorService, null);
	}

	public CodeGeneratorService getCodeGeneratorService() {
		return codeGeneratorService;
	}

	public JavaEditorServices getJavaEditorServices() {
		return javaService;
	}

	public static CodeGeneratorActivator getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		instance = null;
		serviceRef.unregister();
		codeGeneratorService = null;
		javaService = null;
	}

}
