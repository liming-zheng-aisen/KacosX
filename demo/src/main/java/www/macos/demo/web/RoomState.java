package www.macos.demo.web;

import java.util.ArrayList;
import java.util.Iterator;  
import java.util.List;  
import java.util.Map.Entry;  
  

  
/** 
 * 房间操作 
 *  
 * @author Allen 2017年3月31日 
 * 
 */  
public class RoomState {  
  
    /** 
     * 创建一个房间信息 
     *  
     * @param roomKey 
     * @param roomName 
     * @return 
     */  
    public boolean insert(String roomKey, String roomName) {  
  
       
        try {  
            if (!Ram.roomHm.containsKey(roomKey)) {  
                RoomVo rVo = new RoomVo();  
                rVo.setRoomName(roomName);  
                Ram.roomHm.put(roomKey, rVo);  
                return true;  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return false;  
    }  
  
    /** 
     * 返回所有房间信息 
     *  
     * @return 
     */  
    public List<RoomVo> selectAll() {  
  
        try {  
            List<RoomVo> resultList = new ArrayList<>();  
            Iterator<Entry<String, RoomVo>> it = Ram.roomHm.entrySet().iterator();  
            while (it.hasNext()) {  
                Entry<String, RoomVo> entry = it.next();  
                resultList.add(entry.getValue());  
            }  
            return resultList;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    /** 
     * 删除一个房间信息 
     *  
     * @param roomKey
     * @return 
     */  
    public boolean delete(String roomKey) {  
        try {  
            Ram.roomHm.remove(roomKey);  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        return false;  
    }  
  
    /** 
     * 统计房间总数 
     *  
     * @return 
     */  
    public int count() {  
        return Ram.roomHm.size();  
    }  
  
}  