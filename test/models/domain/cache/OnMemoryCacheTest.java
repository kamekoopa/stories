package models.domain.cache;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.concurrent.TimeUnit;

import models.domain.model.OnMemoryCache;

import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class OnMemoryCacheTest {

	static class Fixture{
		final String key; final String value; final long duration; final TimeUnit unit;
		Fixture(String key, String value, long duration, TimeUnit unit){
			this.key = key;
			this.value = value;
			this.duration = duration;
			this.unit = unit;
		}
	}

	private OnMemoryCache instance;



	@DataPoints
	public static Fixture[] fixtures = new Fixture[]{
		new Fixture("key0", "value0", 100, TimeUnit.MILLISECONDS),
		new Fixture("key1", "value1", 110, TimeUnit.MILLISECONDS),
		new Fixture("key2", "value2", 120, TimeUnit.MILLISECONDS),
		new Fixture("key3", "value3", 130, TimeUnit.MILLISECONDS),
		new Fixture("key4", "value4", 140, TimeUnit.MILLISECONDS),
		new Fixture("key5", "value5", 150, TimeUnit.MILLISECONDS),
		new Fixture("key6", "value6", 160, TimeUnit.MILLISECONDS),
		new Fixture("key7", "value7", 170, TimeUnit.MILLISECONDS),
		new Fixture("key8", "value8", 180, TimeUnit.MILLISECONDS),
		new Fixture("key9", "value9", 190, TimeUnit.MILLISECONDS),
	};


	@Before
	public void setup(){
		this.instance = OnMemoryCache.instance;
		this.instance.delete();
	}

	@Theory
	public void setしてすぐget(Fixture f) {

		this.instance.set(f.key, f.value, f.duration, f.unit);
		assertThat(this.instance.get(f.key), is(f.value));
	}

	@Theory
	public void setしてすぐdeleteしてget(Fixture f) {

		this.instance.set(f.key, f.value, f.duration, f.unit);

		this.instance.delete(f.key);

		assertThat(this.instance.get(f.key), is(nullValue()));
	}

	@Theory
	public void setして期限切れまで待ってからget(Fixture f) throws InterruptedException {

		this.instance.set(f.key, f.value, f.duration, f.unit);


		Thread.sleep(f.unit.toMillis(f.duration));

		assertThat(this.instance.get(f.key), is(nullValue()));
	}

	@Theory
	public void 全削除(Fixture f) {

		this.instance.set(f.key, f.value, f.duration, f.unit);

		this.instance.delete();

		assertThat(this.instance.get(f.key), is(nullValue()));
	}
}
