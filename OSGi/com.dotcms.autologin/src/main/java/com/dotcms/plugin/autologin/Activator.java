package com.dotcms.plugin.autologin;

import com.dotcms.filters.interceptor.FilterWebInterceptorProvider;
import com.dotcms.filters.interceptor.WebInterceptorDelegate;
import com.dotmarketing.filters.AutoLoginFilter;
import com.dotmarketing.osgi.GenericBundleActivator;
import com.dotmarketing.util.Config;
import org.osgi.framework.BundleContext;

public class Activator extends GenericBundleActivator {

	public void start(BundleContext context) throws Exception {

		final FilterWebInterceptorProvider filterWebInterceptorProvider =
				FilterWebInterceptorProvider.getInstance(Config.CONTEXT);
		final WebInterceptorDelegate delegate =
				filterWebInterceptorProvider.getDelegate(AutoLoginFilter.class);

		if (null != delegate) {
			System.out.println("Adding the ExampleAutoLoginWebInterceptor");
			delegate.addFirst(new ExampleAutoLoginWebInterceptor());
		}

	}

	public void stop(BundleContext context) throws Exception {

		final FilterWebInterceptorProvider filterWebInterceptorProvider =
				FilterWebInterceptorProvider.getInstance(Config.CONTEXT);
		final WebInterceptorDelegate delegate =
				filterWebInterceptorProvider.getDelegate(AutoLoginFilter.class);

		if (null != delegate) {
			System.out.println("Removing the ExampleAutoLoginWebInterceptor");
			delegate.remove(ExampleAutoLoginWebInterceptor.class.getName(), true);
		}
	}

}