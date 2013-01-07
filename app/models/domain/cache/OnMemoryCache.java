package models.domain.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.tuple.ImmutablePair;

public final class OnMemoryCache {

	public final static OnMemoryCache instance;
	static {
		instance = new OnMemoryCache();
	}

	private final Map<String, ImmutablePair<Long, String>> cache;

	private OnMemoryCache(){
		this.cache = Collections.synchronizedMap(new HashMap<String, ImmutablePair<Long, String>>());
	}

	public String get(String key){

		ImmutablePair<Long, String> tupple = this.cache.get(key);
		if(tupple == null){
			return null;
		}

		long expire = tupple.left.longValue();

		if(this.now() >= expire){
			return null;
		}else{
			return tupple.right;
		}
	}

	public void set(String key, String value){

		this.set(key, value, 20, TimeUnit.MINUTES);
	}

	public void set(String key, String value, long expireDuration, TimeUnit unit){

		Long expire = Long.valueOf(this.now() + unit.toMillis(expireDuration));
		this.cache.put(key, ImmutablePair.of(expire, value));
	}

	public void delete(String key){
		if(key != null){
			this.cache.remove(key);
		}
	}

	public void delete(){
		this.cache.clear();
	}

	private long now(){
		return System.currentTimeMillis();
	}
}
