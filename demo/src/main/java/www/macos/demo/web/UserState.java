package www.macos.demo.web;

import java.text.SimpleDateFormat;
import java.util.Date;  
import java.util.Iterator;  
import java.util.List;  
  

/** 
 * 用户操作 
 *  
 * @author Allen 2017年3月31日 
 * 
 */  
public class UserState {  
    /** 
     * 插入用户到房间中 
     * @param red5Id 
     * @param red5Name 
     * @param red5CreateTime 
     * @param roomKey 
     * @return 
     */  
    public boolean insert(String red5Id, String red5Name, Long red5CreateTime, String roomKey) {  
        try {  
            if (Ram.roomHm.containsKey(roomKey)) {  
                UserVo uvo = new UserVo();  
                uvo.setRed5Id(red5Id);  
                uvo.setRed5Name(red5Name);  
                uvo.setRed5CreateTime(red5CreateTime);  
                Ram.roomHm.get(roomKey).getUserList().add(uvo);  
                return true;  
            }  
            selectAll(roomKey);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return false;  
    }  
    /** 
     * 获取房间中全部用户 
     * @param roomKey 
     * @return 
     */  
    public List<UserVo> selectAll(String roomKey) {  
  
        try {  
            if (Ram.roomHm.containsKey(roomKey)) {  
                Iterator<UserVo> it = Ram.roomHm.get(roomKey).getUserList().iterator();  
                System.out.println("================================================");  
                while (it.hasNext()) {  
                    UserVo temp = it.next();  
                    System.out.println(temp.getRed5Id() + "," + temp.getRed5Name() + ","  
                            + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(temp.getRed5CreateTime())));  
                }  
                System.out.println("================================================");  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
    /** 
     * 删除房间中某用户 
     * @param redId 
     * @param roomKey 
     * @return 
     */  
    public boolean delete(String redId, String roomKey) {  
  
        try {  
            if (Ram.roomHm.containsKey(roomKey)) {  
                Iterator<UserVo> it = Ram.roomHm.get(roomKey).getUserList().iterator();  
                while (it.hasNext()) {  
                    if (it.next().getRed5Id().equals(redId))  
                    {  
                        it.remove();  
                        break;  
                    }   
                }  
            }  
            return true;  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return false;  
    }  
    /** 
     * 统计房间中用户数 
     * @param roomKey 
     * @return 
     */  
    public int count(String roomKey) {  
        return Ram.roomHm.containsKey(roomKey) ? Ram.roomHm.get(roomKey).getUserList().size() : 0;  
    }  
  
}  