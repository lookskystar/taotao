package com.taotao.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.uitl.JsonUtils;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamItemExample.Criteria;
import com.taotao.service.ItemParamItemService;

/**
 * 商品规格参数
 * @author Administrator
 *
 */
@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	
	@Override
	public String getItemParamByItemId(Long itemId) {
		//根据商品id查询规格参数
		TbItemParamItemExample example=new TbItemParamItemExample();
		Criteria criteria=example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		//执行查询
		List<TbItemParamItem> list=itemParamItemMapper.selectByExampleWithBLOBs(example);
		if(list==null||list.size()==0){
			return "";
		}
		//取商品规格参数信息
		TbItemParamItem itemParamItem=list.get(0);
		String paramData=itemParamItem.getParamData();
		//生成html
		//把JSON数据转换成Java对象。不知道对象是什么，是用Map存储。
		List<Map> jsonList=JsonUtils.jsonToList(paramData, Map.class);
		StringBuffer sb=new StringBuffer();
		sb.append
		("<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"1\" class=\"Ptable\">");
		sb.append("   <tbody>\n");
		for(Map m1:jsonList){
			sb.append("		 <tr>\n");
			//取分组
			sb.append("		 	<th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
			sb.append("		 </tr>\n");
			List<Map> mapList=(List<Map>)m1.get("params");
			for(Map m2:mapList){
				sb.append("	 <tr>\n");
				//取项以及参数
				sb.append("	 	<td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
				sb.append("	 	<td>"+m2.get("v")+"</td>\n");
				sb.append("	 </tr>\n");
			}
		}
		sb.append("   </tbody>\n");
		sb.append("</table>\n");
		return sb.toString();
	}
}
