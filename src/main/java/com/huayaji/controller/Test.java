package com.huayaji.controller;

import java.util.Arrays;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;

import java.security.Security;

@Controller
public class Test {

    /**
     *
     * @Title: getPhoneNumber
     * @Description: 获取手机号
     * @author: DreamFly
     * @date: 2019年4月13日
     * @param encryptedData
     * @param code
     * @param iv
     * @return
     * @return: Object
     */
    @RequestMapping("/getPhoneNumber")
    @ResponseBody
    public Object getPhoneNumber(String encryptedData, String code, String iv) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
        url = url.replaceAll("APPID", "wx2f546747cf23bc66");
        url = url.replaceAll("SECRET", "17bf4b259cd3d189a9eb14cfb7fb528a");
        url = url.replaceAll("JSCODE", code);
        String doPost = HttpClientUtil.doGet(url);
        // 解析相应内容（转换成json对象）
        JSONObject json = JSONObject.parseObject(doPost);
        // 获取会话密钥（session_key）
        String session_key = (String) json.get("session_key");
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(session_key);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

