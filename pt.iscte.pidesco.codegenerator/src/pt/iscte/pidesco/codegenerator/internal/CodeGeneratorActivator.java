package pt.iscte.pidesco.codegenerator.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import pa.iscde.search.services.SearchService;
import pt.iscte.pidesco.codegenerator.extensability.CodeStringGeneratorService;
import pt.iscte.pidesco.codegenerator.service.CodeGeneratorService;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class CodeGeneratorActivator implements BundleActivator {

	private CodeStringGeneratorService codeGeneratorService;
	private JavaEditorServices javaService;
	private ServiceRegistration<CodeStringGeneratorService> stringGeneratorServiceRef;
	private ServiceRegistration<CodeGeneratorService> codeGeneratorServiceRef;
	private SearchService searchService;
	private ProjectBrowserServices browserService;
	private static CodeGeneratorActivator instance;



	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		instance = this;
		ServiceReference<JavaEditorServices> javaServiceReference = bundleContext.getServiceReference(JavaEditorServices.class);
		ServiceReference<SearchService> searchServiceReference = bundleContext.getServiceReference(SearchService.class);
		ServiceReference<ProjectBrowserServices> browserServiceReference = bundleContext.getServiceReference(ProjectBrowserServices.class);
		javaService = bundleContext.getService(javaServiceReference);
		searchService = bundleContext.getService(searchServiceReference);
		browserService = bundleContext.getService(browserServiceReference);
		codeGeneratorService = new CodeGeneratorController();
		stringGeneratorServiceRef = bundleContext.registerService(CodeStringGeneratorService.class, codeGeneratorService, null);
		codeGeneratorServiceRef = bundleContext.registerService(CodeGeneratorService.class, 
				new CodeGeneratorServiceImpl(javaService, codeGeneratorService, new CodeGeneratorModel(javaService)), null);
	}

	public CodeStringGeneratorService getCodeGeneratorService() {
		return codeGeneratorService;
	}

	public JavaEditorServices getJavaEditorServices() {
		return javaService;
	}

	public SearchService getSearchService() {
		return searchService;
	}
	
	public ProjectBrowserServices getBrowserService() {
		return browserService;
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
		if(stringGeneratorServiceRef != null) {
			stringGeneratorServiceRef.unregister();
		}
		if(codeGeneratorServiceRef != null) {
			codeGeneratorServiceRef.unregister();	
		}
		codeGeneratorService = null;
		javaService = null;
	}

}
