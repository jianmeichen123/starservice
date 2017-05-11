package com.galaxy.im.business.example;

import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.galaxy.im.bean.DepartBean;
import com.galaxy.im.bean.DepartBeanVO;
import com.galaxy.im.business.example.service.IDepartService;
import com.galaxy.im.common.cache.redis.IRedisCache;

@Controller
@RequestMapping("/example")
public class ExampleController {
	//private static Logger log = LoggerFactory.getLogger(DaoException.class);
	
	@Autowired
	private IDepartService service;
	
	@Autowired
	private IRedisCache<String,Object> cache;

	@RequestMapping("hello")
	public void hello(PrintWriter out){
		try{
//			Test testBean = new Test();
//			testBean.setName("乔利岩");
//			testBean.setAddress("辽宁省兴城市");
//			
//			service.saveTest(testBean);
			
			//{"type":2,"name":'乔乔添加的拜访',"projectType":1,"projectId":1,"startTime":"2017-05-10 14:41:50","endTime":"2017-05-10 23:41:50","isAllday":0,"wakeupId":1,"remark":"乔乔添加的备注","significance":1}
			
			DepartBeanVO vo = new DepartBeanVO();
			vo.setId(2L);
			
			DepartBean depart = service.getDepartById(vo);
			
			System.out.println(depart.getDepName());
			
		}catch(Exception e){
			
		}
		
	}
	
	
}
