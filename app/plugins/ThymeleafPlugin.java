package plugins;

import java.io.InputStream;
import java.util.Map;

import nz.net.ultraq.web.thymeleaf.LayoutDialect;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.context.Context;
import org.thymeleaf.exceptions.ConfigurationException;
import org.thymeleaf.resourceresolver.FileResourceResolver;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import play.Application;
import play.Configuration;
import play.Logger;
import play.Plugin;
import play.api.templates.Html;
import play.mvc.Result;
import play.mvc.Results;

public class ThymeleafPlugin extends Plugin {

	private final Configuration config;

	private static TemplateEngine engine;

	public ThymeleafPlugin(Application app) {
		this.config = app.configuration().getConfig("stories.thymeleaf");
	}

	@Override
	public boolean enabled() {

		if(this.config == null){

			Logger.error("thymeleaf設定が取得できませんでした。キー値\"stories.thymeleaf\"が設定されているかを確認してください。");
			return false;

		}else{

			String viewLookupPath = this.config.getString("views")     == null ? "app/views/" : this.config.getString("views");
			String suffix         = this.config.getString("suffix")    == null ? ".html"      : this.config.getString("suffix");
			String mode           = this.config.getString("mode")      == null ? "HTML5"      : this.config.getString("mode");
			Long   ttl            = this.config.getMilliseconds("ttl") == null ? 1*60*1000    : this.config.getMilliseconds("ttl");

			PlayFileTemplateResolver resolver = new PlayFileTemplateResolver(viewLookupPath);
			resolver.setTemplateMode(mode);
			resolver.setSuffix(suffix);
			resolver.setCacheTTLMs(ttl);

			TemplateEngine engine = new TemplateEngine();
			engine.setTemplateResolver(resolver);
			engine.addDialect(new LayoutDialect());

			ThymeleafPlugin.engine = engine;

			return ThymeleafPlugin.engine != null;
		}
	}

	@Override
	public void onStart() {
		Logger.info("Tymeleaf Plugin Initialized");
	}

	@Override
	public void onStop() {
	}


	public static Result ok(final String templatePath, final Map<String, Object> variables){
		return Results.ok(process(templatePath, variables));
	}

	public static Result badRequest(final String templatePath, final Map<String, Object> variables){
		return Results.badRequest(process(templatePath, variables));
	}

	public static Result unauthorized(final String templatePath, final Map<String, Object> variables){
		return Results.unauthorized(process(templatePath, variables));
	}

	private static Html process(final String templatePath, final Map<String, Object> variables){

		Context ctx = new Context();
		ctx.setVariables(variables);

		return new Html(engine.process(templatePath, ctx));
	}


	public static class PlayFileTemplateResolver extends TemplateResolver {

		public PlayFileTemplateResolver(final String viewLookupPath){
			super();
			super.setResourceResolver(new PlayFileResourceResolver(viewLookupPath));
		}

		@Override
	    public void setResourceResolver(final IResourceResolver resourceResolver) {
			throw new ConfigurationException(
				"Cannot set a resource resolver on " + this.getClass().getName() + ". If " +
				"you want to set your own resource resolver, use " + TemplateResolver.class.getName() +
				"instead"
			);
		}
	}

	public static class PlayFileResourceResolver implements IResourceResolver {

		private final FileResourceResolver fileResourceResolver;

		private final String viewLookupPath;

		public PlayFileResourceResolver(final String viewLookupPath){
			super();
			this.fileResourceResolver = new FileResourceResolver();
			this.viewLookupPath = viewLookupPath;
		}

		@Override
		public String getName() {
			return "PLAY_FILE";
		}

		@Override
		public InputStream getResourceAsStream (
			TemplateProcessingParameters templateProcessingParameters,
			String resourceName
		) {

			StringBuilder sb = new StringBuilder(this.viewLookupPath).append(resourceName);
			return this.fileResourceResolver.getResourceAsStream(templateProcessingParameters, sb.toString());
		}
	}
}
