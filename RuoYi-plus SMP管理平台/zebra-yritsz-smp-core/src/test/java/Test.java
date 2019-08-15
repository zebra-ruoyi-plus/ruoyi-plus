import com.zebra.common.utils.http.HttpUtil;

public class Test {
	public static void main(String[] args) {
		String url = "https://www.airbnb.cn/authenticate?email=2848021249@qq.com&password=qq13264676322";
		HttpUtil httpUtil = HttpUtil.getInstance("utf-8", 10000, 10000);
		String a = httpUtil.sendHttpsPostByUnilateral(url, null, null, null,
				org.springframework.util.MimeTypeUtils.ALL);
		System.out.println(a);
	}

}
