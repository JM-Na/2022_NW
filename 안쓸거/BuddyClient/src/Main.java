// https://tychejin.tistory.com/312 HttpURLConnection을 통해 GET, POST 메소드
// https://loosie.tistory.com/467 json 라이브러리 추가
// https://ktko.tistory.com/entry/JAVA%EC%97%90%EC%84%9C-JSON-%ED%8C%8C%EC%8B%B1%ED%95%98%EA%B8%B0 Json 데이터 파싱

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;

public class Main {

    private static String URL = "http://localhost:8080";

    public static void main(String[] args) {
        try {

            // GET 메소드 url : http://localhost:8080/test
            String getMsg = HttpConnectionUtils.getRequest(URL + "/test");

            JSONParser jsonParse = new JSONParser();

            //JSONParse에 json데이터를 넣어 파싱한 다음 JSONObject로 변환한다.
            JSONObject jsonObj = (JSONObject) jsonParse.parse(getMsg);

            boolean success =Boolean.parseBoolean(jsonObj.get("isSuccess").toString());
            System.out.println(success);

            if(success) {
                int code = Integer.parseInt(jsonObj.get("code").toString());
                String message = jsonObj.get("message").toString();
                String testResult = jsonObj.get("result").toString();

                System.out.println(code);
                System.out.println(message);
                System.out.println(testResult);
            }
            else {
                int code = Integer.parseInt(jsonObj.get("code").toString());
                String message = jsonObj.get("message").toString();

                System.out.println(code);
                System.out.println(message);
            }


            // POST 메소드, 회원가입, url = http://localhost:8080/user
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", "tester7");
            map.put("pwd", "1234");
            map.put("pwd_retype", "1234");
            map.put("nickname", "tester7");
            map.put("email", "tester7@gmail.com");
            map.put("birthdate", "2000-00-00");
            map.put("phone", "010-0000-0000");
            map.put("address", "gachon");
            map.put("web", "github.com");

            String postMsg = HttpConnectionUtils.postRequest(URL + "/user", map);

            jsonObj = (JSONObject) jsonParse.parse(postMsg);

            success = Boolean.parseBoolean(jsonObj.get("isSuccess").toString());
            System.out.println(success);

            if(success) {
                int code = Integer.parseInt(jsonObj.get("code").toString());
                String message = jsonObj.get("message").toString();
                JSONObject userObject = (JSONObject) jsonObj.get("result");

                System.out.println(code);
                System.out.println(message);
                System.out.println(Integer.parseInt(userObject.get("u_id").toString()));
            }
            else {
                int code = Integer.parseInt(jsonObj.get("code").toString());
                String message = jsonObj.get("message").toString();

                System.out.println(code);
                System.out.println(message);
            }

            // POST 메소드, 로그인, url = http://localhost:8080/user/log-in
            Map<String, Object> loginMap = new HashMap<String, Object>();
            loginMap.put("id", "tester7");
            loginMap.put("pwd", "1234");

            String loginMsg = HttpConnectionUtils.postRequest(URL + "/user/log-in", loginMap);

            jsonObj = (JSONObject) jsonParse.parse(loginMsg);

            success = Boolean.parseBoolean(jsonObj.get("isSuccess").toString());
            System.out.println(success);

            if(success) {
                int code = Integer.parseInt(jsonObj.get("code").toString());
                String message = jsonObj.get("message").toString();
                JSONObject userObject = (JSONObject) jsonObj.get("result");

                System.out.println(code);
                System.out.println(message);
                System.out.println(Integer.parseInt(userObject.get("u_id").toString()));
            }
            else {
                int code = Integer.parseInt(jsonObj.get("code").toString());
                String message = jsonObj.get("message").toString();

                System.out.println(code);
                System.out.println(message);
            }

        } catch(ParseException e){
            e.printStackTrace();
        }
    }
}