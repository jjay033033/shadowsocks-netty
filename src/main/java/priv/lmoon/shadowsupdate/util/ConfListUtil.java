package priv.lmoon.shadowsupdate.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import priv.lmoon.shadowsupdate.config.XmlConfig;
import priv.lmoon.shadowsupdate.vo.ConfVO;

public class ConfListUtil {

	public static Map<String, Object> compareList(List<ConfVO> oldList, List<ConfVO> newList) {
		boolean isChange = false;
		if (newList == null || newList.isEmpty()) {

		} else if (oldList == null || oldList.isEmpty()) {
			isChange = true;
		} else {
			Map<String, Map<String, ConfVO>> nMap = new HashMap<String, Map<String, ConfVO>>();
			for (ConfVO nVo : newList) {
				String serverId = nVo.getRemarks();
				Map<String, ConfVO> sMap = nMap.get(serverId);
				if (sMap == null) {
					sMap = new HashMap<String, ConfVO>();
				}
				sMap.put(nVo.getServer(), nVo);
				nMap.put(serverId, sMap);
			}
			for (ConfVO oVo : oldList) {
				Map<String, ConfVO> map = nMap.get(oVo.getRemarks());
				if (map == null) {
//					if(XmlConfig.getInstance().getServerConfigMap().containsKey(oVo.getRemarks())){
//						newList.add(oVo);
//					}
				} else {
					ConfVO nVo = map.get(oVo.getServer());
					if (nVo == null) {
						isChange = true;
					} else {
						if (!oVo.equals(nVo)) {
							isChange = true;
						}
					}
				}
			}
			if(newList.size()!=oldList.size()){
				isChange = true;
			}
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("isChange", isChange);
		resultMap.put("confList", newList);
		return resultMap;
	}

}
