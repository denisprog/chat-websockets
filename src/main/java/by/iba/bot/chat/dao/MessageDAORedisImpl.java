package by.iba.bot.chat.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class MessageDAORedisImpl implements MessageDAO {

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Override
	public String getValueByKey(String key) {

		return redisTemplate.opsForValue().get(key);
	}

	@Override
	public void updateValuesByKeys(Map<String, String> messageTexts) {
		
		redisTemplate.execute(new SessionCallback<List<Object>>() {
			public List<String> execute(RedisOperations operations) throws DataAccessException {
				operations.multi();
				for (String key : messageTexts.keySet()) {
					operations.opsForValue().set(key, messageTexts.get(key));
				}
				return operations.exec();
			}
		});
	}

}