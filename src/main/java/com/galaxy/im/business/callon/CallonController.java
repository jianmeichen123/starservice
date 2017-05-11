package com.galaxy.im.business.callon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.im.bean.schedule.ScheduleInfo;
import com.galaxy.im.business.callon.service.ICallonService;
import com.galaxy.im.common.ResultBean;

@Controller
@RequestMapping("/callon")
public class CallonController {
	
	@Autowired
	private ICallonService callonService;

	@RequestMapping("save")
	@ResponseBody
	public Object save(@RequestBody ScheduleInfo paramString){
		ResultBean<Object> resultBean = new ResultBean<Object>();
		try{
			Long id = callonService.insert(paramString);
			if(id!=null){
				resultBean.setStatus("OK");
			}
		}catch(Exception e){
		}
		return resultBean;
	}

}
