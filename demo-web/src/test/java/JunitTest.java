import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.RedisStringCommands.BitOperation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.demo.redis.RedisUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=JunitTest.class)
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
@ComponentScan(basePackages="com.demo")
public class JunitTest {
	
	@Autowired
	private RedisUtil util;
	
	@Test
	public void testBit(){
		
		try {
			util.setBit("{count}:count1", 1, true);
			util.setBit("{count}:count1", 3, true);
			util.setBit("{count}:count1", 5, true);
			
			System.out.println(String.format("count1里面有%s条数据",util.bitCount("{count}:count1")));
			
			util.setBit("{count}:count2", 2, true);
			util.setBit("{count}:count2", 3, true);
			util.setBit("{count}:count2", 6, true);
			
			System.out.println(String.format("count2里面有%s条数据",util.bitCount("{count}:count2")));
			
			util.setBit("{count}:count3", 4, true);
			util.setBit("{count}:count3", 3, true);
			util.setBit("{count}:count3", 7, true);
			
			System.out.println(String.format("count3里面有%s条数据",util.bitCount("{count}:count3")));
			
			String[] keys = new String[] { "{count}:count1", "{count}:count2", "{count}:count3" };
			
			/**
			 * CROSSSLOT Keys in request don't hash to the same slot
			 * 要解决此错误，请使用哈希标签强制将密钥放入相同的哈希槽。当密钥包含“{...}”这种样式时，只有大括号“{”和“}”之间的子字符串得到哈希以获得哈希槽。
			 */
			
			//逻辑或
			util.bitOp(BitOperation.AND, "{count}:jiaoji", keys);
			System.out.println("取交集的数量：" + util.bitCount("{count}:jiaoji"));//1
			
			//逻辑与
			util.bitOp(BitOperation.OR, "{count}:bingji", keys);
			System.out.println("取并集的数量：" + util.bitCount("{count}:bingji"));//7
			
			//对给定 key 求逻辑非
			util.bitOp(BitOperation.NOT, "{count}:not", "{count}:count1");
			System.out.println("逻辑非的数量：" + util.bitCount("{count}:not"));//5
			
			//逻辑异或
			util.bitOp(BitOperation.XOR, "{count}:xor", keys);
			System.out.println("逻辑异或的数量：" + util.bitCount("{count}:xor"));//7
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
