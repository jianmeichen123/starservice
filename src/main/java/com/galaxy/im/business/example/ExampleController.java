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
			
			DepartBeanVO vo = new DepartBeanVO();
			vo.setId(2L);
			
			DepartBean depart = service.getDepartById(vo);
			
			System.out.println(depart.getDepName());
			
		}catch(Exception e){
			
		}
		
	}
	
	
}
