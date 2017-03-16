package connectWebservice;

import java.security.MessageDigest;

/**
 */

public class TVSFunction {
    public String Cytography(String userName,String passWord)
    {
        /** Mã hóa userName và passWord bằng công thức như sau :
         *      —Bước 1:  Đảo ngược chuỗi đầu vào
         *                  +)Đảo ngược userName
         *                  +)Đảo ngược passWord
         *      —Bước 2:  Thay thế lần lượt các vị trí chẵn (0,2,4,6...) thành các chử số ASCII (A->65,B->66...)
         *                ...
         *      — Tạm thời thế đả ..../
        * */
        String name = rolateString(userName);
        String pass = rolateString(passWord);
        //



        return "";
    }
    public String rolateString(String str)   {
        char[] tmp = str.toCharArray();
        char[] result = new char[str.length()];
        for (int i =0 ; i < tmp.length; i ++)
        {
            result[i] = tmp[tmp.length - i ];
        }
        return result.toString();
    }
    public  String MD5Cytography(String str) {
        byte[] result = null;
        try {
            byte[] byteOfString = str.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = md.digest(byteOfString);
        }
        catch (Exception e)
        {
            if (result==null) return "";
        }
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : result) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        }
    }

