package plugins;

import org.apache.commons.lang3.StringUtils;

import play.Application;
import play.Logger;
import play.Plugin;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class GuicePlugin extends Plugin {

	private Injector injector = null;

	private final String className;

	public GuicePlugin(Application app) throws ReflectiveOperationException{
		this.className = app.configuration().getString("stories.guice.module");
		Logger.info("利用するModuleクラス: " + this.className);
	}

	@Override
	public boolean enabled() {

		if(StringUtils.isEmpty(this.className)){

			Logger.error("conf/*.confに利用するModuleクラスが設定されていません。キー値\"stories.guice.module\"に利用するModuleクラスを完全修飾名で指定してください。");
			return false;

		}else{
			try {

				Module module = (Module) Class.forName(this.className).newInstance();
				this.injector = Guice.createInjector(module);

				return true;

			}catch(ReflectiveOperationException e){

				Logger.error("injectorの生成に失敗しました", e);
				return false;
			}catch(Throwable e){
				throw e;
			}
		}
	}

	@Override
	public void onStart() {
		Logger.info("Guice Plugin Initialized");
	}

	@Override
	public void onStop() {
	}


	public <T> T get(Class<T> clazz){

		if(this.injector == null){

			IllegalStateException e = new IllegalStateException("injectorが初期化されていません");
			Logger.error("クラスの取得に失敗しました", e);

			throw e;

		}else{
			return this.injector.getInstance(clazz);
		}
	}
}
