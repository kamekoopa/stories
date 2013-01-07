package models.domain.user;

import static org.junit.Assert.fail;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

public class AccountServiceTest {

	@Test
	public void test() {

		String hoge = DigestUtils.sha1Hex("hogehoge");

		fail(hoge);
	}

}
