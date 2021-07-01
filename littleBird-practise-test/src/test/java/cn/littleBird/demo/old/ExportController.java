package cn.littleBird.demo.old;//package com.ztesoft.gis.controller;
//
//import java.io.OutputStream;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.ztesoft.gis.base.service.BaseService;
//import com.ztesoft.gis.common.GisConsts;
//import com.ztesoft.gis.model.RoleInfo;
//import com.ztesoft.gis.model.UserInfo;
//import com.ztesoft.gis.utils.RequestUtils;
//
///**
// * 导出Excel表
// * @author Administrator
// *
// */
//
//@Controller
//@RequestMapping("/export")
//public class ExportController {
//
//	@Autowired
//	protected BaseService baseService;
//	
//	@RequestMapping(value = "/excel", method = { RequestMethod.GET,
//			RequestMethod.POST })
//	public @ResponseBody
//	void excel(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		
//		UserInfo user = (UserInfo) request.getSession().getAttribute(GisConsts.USER_SESSION_KEY);//取出session转为UserInfo
//		String userId = user.getUser_id();
//		List<RoleInfo> roleList= user.getListRole();		
//		Iterator<RoleInfo> roleInfo = roleList.iterator();
//		String roleId =  "";	//roleId 关键字
//		while(roleInfo.hasNext()){ 	//循环判断所有roleId，其中有管理员的，roleId=1
//			RoleInfo role = roleInfo.next();
//			String role_id = role.getRole_id();
//			if(role_id.equals("1")||role_id=="1"){
//				roleId = "1";   //管理员为1
//			}
//		}
//		
//		String sqlid = request.getParameter("sqlid");
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("sqlid",sqlid);
//		params.put("userId", userId);
//		params.put("state", request.getParameter("state"));
//		params.put("startTime", request.getParameter("start_date"));
//		params.put("endTime", request.getParameter("end_date"));
//		params.put("userName", request.getParameter("user_name"));
//		params.put("roleId", roleId);
//		
//		List<Map<String, Object>> list = this.baseService.queryForList(sqlid, params); 
//		
//		//创建HSSFWorkbook对象(excel的文档对象)
//	    HSSFWorkbook wb = new HSSFWorkbook();
//		//建立新的sheet对象（excel的表单）
//		HSSFSheet sheet=wb.createSheet("下载记录表");
//		sheet.autoSizeColumn(1); 
//		//在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
//		//HSSFRow row1=sheet.createRow(0);
//		//创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
//		//HSSFCell cell=row1.createCell(0);
//		//设置单元格内容
//		//cell.setCellValue("用户下载记录表");
//		//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
//		//sheet.addMergedRegion(new CellRangeAddress(0,0,0,8));
//		//在sheet里创建第二行
//		HSSFRow row2=sheet.createRow(0);    
//	    //创建单元格并设置单元格内容
//	    row2.createCell(0).setCellValue("记录ID");
//	    row2.createCell(1).setCellValue("用户ID");  
//	    row2.createCell(2).setCellValue("用户姓名"); 
//	    row2.createCell(3).setCellValue("下载路径");
//	    row2.createCell(4).setCellValue("下载名称");
//	    row2.createCell(5).setCellValue("下载数据量");
//	    row2.createCell(6).setCellValue("下载状态");
//	    row2.createCell(7).setCellValue("下载时间");
//			
//		Iterator<Map<String,Object>> it = list.iterator();
//
//		short num =1;
//		while(it.hasNext()){
//			HSSFRow row=sheet.createRow(num);
//			Map<String,Object> rows = it.next();
//			String record_id=(String) rows.get("RECORD_ID");
//			String user_id="";
//				if(rows.get("USER_ID")!=null){
//					user_id=rows.get("USER_ID").toString();
//				}
//			String user_name = (String) rows.get("USER_NAME");
//			String down_url = (String) rows.get("DOWN_URL");
//			String down_name = (String) rows.get("DOWN_NAME");
//			String t_num = "";
//				if(rows.get("T_NUM")!=null){
//					t_num =  rows.get("T_NUM").toString();
//				}
//			String state = (String) rows.get("STATE");
//			String sys_time = "";
//				if(rows.get("SYS_TIME")!=null){
//					sys_time = rows.get("SYS_TIME").toString().substring(0,10);
//				}
//			if(state!=null){
//				if(state == "1" || state.equals("1")){
//			 		state="成功";
//			 	}else{
//			 		state="失败";
//			 	}
//			}else{
//		 		state="失败";
//		 	}
//		 	
//	        row.createCell(0).setCellValue(record_id);
//	        row.createCell(1).setCellValue(user_id);
//	        row.createCell(2).setCellValue(user_name);
//	        row.createCell(3).setCellValue(down_url);
//	        row.createCell(4).setCellValue(down_name);
//	        row.createCell(5).setCellValue(t_num);
//	        row.createCell(6).setCellValue(state);
//	        row.createCell(7).setCellValue(sys_time);
//	        num++;
//		 }
//	 
//	    //输出Excel文件
//	    OutputStream output=response.getOutputStream();
//	    response.reset();
//	    response.setHeader("Content-disposition", "attachment; filename=down.xls");
//	    response.setContentType("application/msexcel");        
//	    wb.write(output);
//	    output.close();
//	
//	}
//	
//	
//	//传输管道专题导出
//	@RequestMapping(value = "/toexcel", method = { RequestMethod.GET,RequestMethod.POST })
//	public @ResponseBody void excell(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		
//		Map<String, Object> params = RequestUtils.getParameterMap(request);
//		String flag = request.getParameter("flag");
//		String statitemid = request.getParameter("statitemid");
//		String sqlid = request.getParameter("sqlid");
//		List<Map<String, Object>> list = baseService.queryForList(sqlid, params);
//		String filename="";
//		//创建HSSFWorkbook对象(excel的文档对象)
//	    HSSFWorkbook wb = new HSSFWorkbook();
//		//建立新的sheet对象（excel的表单）
//		HSSFSheet sheet=wb.createSheet("下载记录表");
//		
//		HSSFRow row2=sheet.createRow(0);
//		
//		//取到map中的数据
//		Iterator<Map<String,Object>> it = list.iterator();
//
//	    row2.createCell(0).setCellValue("区划编码"); 
//	    row2.createCell(1).setCellValue("城市");
//	    row2.createCell(2).setCellValue("区县");
//	    row2.createCell(3).setCellValue("分类");
//	    if(flag=="pipe-thematic-map"||flag.equals("pipe-thematic-map")){
//	    	filename="传输管道专题图";
//	    	row2.createCell(4).setCellValue("管道覆盖率/100%");
//	    }else if(statitemid=="301001"||statitemid.equals("301001")){
//	    	filename="区县总面积统计";
//	    	row2.createCell(4).setCellValue("区县总面积/平方千米");
//	    }else if(statitemid=="301002"||statitemid.equals("301002")){
//	    	filename="建成区面积统计";
//	    	row2.createCell(4).setCellValue("建成区面积/平方千米");
//	    }else if(statitemid=="301003"||statitemid.equals("301003")){
//	    	filename="建成区面积占比";
//	    	row2.createCell(4).setCellValue("建成区面积占比/100%");
//	    }else if(statitemid=="302001"||statitemid.equals("302001")){
//	    	filename="行政道路总长度";
//	    	row2.createCell(4).setCellValue("道路总长度/千米");
//	    }else if(statitemid=="302002"||statitemid.equals("302002")){
//	    	filename="建成区道路长度";
//	    	row2.createCell(4).setCellValue("建成区道路长度/千米");
//	    }else if(statitemid=="302003"||statitemid.equals("302003")){
//	    	filename="建成区道路占比";
//	    	row2.createCell(4).setCellValue("建成区道路占比/100%");
//	    }
//	    
//	    row2.createCell(5).setCellValue("时间");
//		int num =1;
//		while(it.hasNext()){
//			HSSFRow row=sheet.createRow(num);
//			Map<String,Object> rows = it.next();   
//			//取数据，非string类型，如果为空，可能会异常，if判断下
//			String dcode ="";
//			if(flag=="pipe-thematic-map"||flag.equals("pipe-thematic-map")){
//				if(rows.get("DCODE")!=null){
//					dcode=rows.get("DCODE").toString();
//				}
//			}else{
//				if(rows.get("MAPID")!=null){
//					dcode=rows.get("MAPID").toString();
//			}
//			}
//			
//			String dname ="";
//			if(rows.get("CITY")!=null){
//				dname=rows.get("CITY").toString();
//			}
//			String cname=rows.get("DNAME").toString();
//			String cls = rows.get("STATITEMNAME").toString();
//			String road_pipe_rate= "";
//			if(flag=="pipe-thematic-map"||flag.equals("pipe-thematic-map")){
//				if(rows.get("ROAD_PIPE_RATE")!=null){
//					road_pipe_rate=rows.get("ROAD_PIPE_RATE").toString();
//				}
//			}else{
//				if(rows.get("STATITEMVAL")!=null){
//					road_pipe_rate=rows.get("STATITEMVAL").toString();
//			}
//			}
//			String c_time="";
//			if(flag=="pipe-thematic-map"||flag.equals("pipe-thematic-map")){
//				if(rows.get("C_TIME")!=null){
//					c_time=rows.get("C_TIME").toString();
//				}
//			}else{
//				if(rows.get("STATDATE")!=null){
//					c_time=rows.get("STATDATE").toString();
//			}
//			}
//			
//	        row.createCell(0).setCellValue(dcode);
//	        row.createCell(1).setCellValue(dname);
//	        row.createCell(2).setCellValue(cname);
//	        row.createCell(3).setCellValue(cls);
//	        row.createCell(4).setCellValue(road_pipe_rate);
//	        row.createCell(5).setCellValue(c_time);
//	        num++;
//		}
//		//sheet.setColumnWidth(列数,  (int)256*宽度+184);
//		sheet.setColumnWidth(0,  (int)256*8+184);
//		sheet.setColumnWidth(1,  (int) (256*13.5+184));
//		sheet.setColumnWidth(2,  (int)256*13+184);
//		sheet.setColumnWidth(3,  (int)256*51+184);
//		sheet.setColumnWidth(4,  (int)256*20+184);
//		sheet.setColumnWidth(5,  (int)256*20+184);
//		
//	    //输出Excel文件
//	    OutputStream output=response.getOutputStream();
//	    response.reset();
//	    response.setHeader("Content-disposition", "attachment; filename="+new String(filename.getBytes("gb2312"), "ISO8859-1")+".xls");
//	    response.setContentType("application/msexcel");        
//	    wb.write(output);
//	    output.close();
//	}
//	
//	//传输管道数据导出
//	@RequestMapping(value = "/toexcel1", method = { RequestMethod.GET,RequestMethod.POST })
//	public @ResponseBody void excell1(HttpServletRequest request,HttpServletResponse response) throws Exception {		
//		Map<String, Object> params = RequestUtils.getParameterMap(request);
//		String flag = request.getParameter("flag");
//		String sqlid = request.getParameter("sqlid");//这是什么
//		List<Map<String, Object>> list = baseService.queryForList(sqlid, params);
//		String filename="";
//		//创建HSSFWorkbook对象(excel的文档对象)
//	    HSSFWorkbook wb = new HSSFWorkbook();
//		//建立新的sheet对象（excel的表单）
//		HSSFSheet sheet=wb.createSheet("管道数据表");
//		
//		HSSFRow row2=sheet.createRow(0);
//		
//		//取到map中的数据
//		Iterator<Map<String,Object>> it = list.iterator();
//
//		if(flag=="map-pipe-line"||flag.equals("map-pipe-line")){
//			//filename= "管道数据表";
//		    row2.createCell(0).setCellValue("管道ID"); 
//		    row2.createCell(1).setCellValue("管道名称");
//		    row2.createCell(2).setCellValue("起点横坐标");
//		    row2.createCell(3).setCellValue("起点纵坐标");
//		    row2.createCell(4).setCellValue("终点横坐标");
//		    row2.createCell(5).setCellValue("终点纵坐标");
//		    row2.createCell(6).setCellValue("管道状态");
//		    row2.createCell(7).setCellValue("管孔占用情况");
//			int num =1;
//			while(it.hasNext()){
//				HSSFRow row=sheet.createRow(num);
//				Map<String,Object> rows = it.next();   
//				//取数据，非string类型，如果为空，可能会异常，if判断下
//				String id ="";
//				if(rows.get("ID")!=null){
//					id=rows.get("ID").toString();
//				}
//				String name ="";
//				if(rows.get("NAME")!=null){
//					name=rows.get("NAME").toString();
//				}
//				String a_x ="";
//				if(rows.get("A_Y")!=null){
//					a_x=rows.get("A_Y").toString();
//				}
//				String a_y ="";
//				if(rows.get("A_Y")!=null){
//					a_y=rows.get("A_Y").toString();
//				}
//				String z_x ="";
//				if(rows.get("Z_X")!=null){
//					z_x=rows.get("Z_X").toString();
//				}
//				String z_y ="";
//				if(rows.get("Z_Y")!=null){
//					z_y=rows.get("Z_Y").toString();
//				}
//				String status ="";
//				if(rows.get("STATUS")!=null){
//					status=rows.get("STATUS").toString();
//				}
//				if (("1").equals(status)) {
//					status = "正常";
//	            }else{
//	            	status = "不正常";
//	            }
//				String no_through= "";
//				if(rows.get("NO_THROUGH")!=null){
//					no_through=rows.get("NO_THROUGH").toString();
//				}
//				if (("1").equals(no_through)) {
//					no_through = "被占用";
//	            }else{
//	            	no_through = "可用";
//	            }
//				
//		        row.createCell(0).setCellValue(id);
//		        row.createCell(1).setCellValue(name);
//		        row.createCell(2).setCellValue(a_x);
//		        row.createCell(3).setCellValue(a_y);
//		        row.createCell(4).setCellValue(z_x);
//		        row.createCell(5).setCellValue(z_y);
//		        row.createCell(6).setCellValue(status);
//		        row.createCell(7).setCellValue(no_through);
//		        num++;
//			}
//		//sheet.setColumnWidth(列数,  (int)256*宽度+184);
//		sheet.setColumnWidth(0,  (int)256*8+184);
//		sheet.setColumnWidth(1,  (int) (256*8+184));
//		sheet.setColumnWidth(2,  (int)256*13+184);
//		sheet.setColumnWidth(3,  (int)256*13+184);
//		sheet.setColumnWidth(4,  (int)256*13+184);
//		sheet.setColumnWidth(5,  (int)256*13+184);
//		sheet.setColumnWidth(6,  (int)256*10+184);
//		sheet.setColumnWidth(7,  (int)256*16+184);
//		}
//		
//	    //输出Excel文件
//	    OutputStream output=response.getOutputStream();
//	    response.reset();
//	    response.setHeader("Content-disposition", "attachment; filename=a.xls");//"+new String(filename.getBytes("gb2312"), "ISO8859-1")+"
//	    response.setContentType("application/msexcel");        
//	    wb.write(output);
//	    output.close();
//	}
//	
//	//操作日志导出
//	@RequestMapping(value = "/toexcel2", method = { RequestMethod.GET,RequestMethod.POST })
//	public @ResponseBody void excell2(HttpServletRequest request,HttpServletResponse response) throws Exception {		
//		Map<String, Object> params = RequestUtils.getParameterMap(request);
//		String flag = request.getParameter("flag");
//		String sqlid = request.getParameter("sqlid");//这是什么
//		List<Map<String, Object>> list = baseService.queryForList(sqlid, params);
//		String filename="";
//		//创建HSSFWorkbook对象(excel的文档对象)
//	    HSSFWorkbook wb = new HSSFWorkbook();
//		//建立新的sheet对象（excel的表单）
//		HSSFSheet sheet=wb.createSheet("管道数据表");
//		
//		HSSFRow row2=sheet.createRow(0);
//		
//		//取到map中的数据
//		Iterator<Map<String,Object>> it = list.iterator();
//
//		if(flag=="visit-log2"||flag.equals("visit-log2")){//-------
//			//filename= "管道数据表";
//		    row2.createCell(0).setCellValue("日志ID"); 
//		    row2.createCell(1).setCellValue("操作类型");
//		    row2.createCell(2).setCellValue("菜单类型");
//		    row2.createCell(3).setCellValue("用户ID");
//		    row2.createCell(4).setCellValue("用户名");
//		    row2.createCell(5).setCellValue("创建日期");
//			int num =1;
//			while(it.hasNext()){
//				HSSFRow row=sheet.createRow(num);
//				Map<String,Object> rows = it.next();   
//				//取数据，非string类型，如果为空，可能会异常，if判断下
//				String log_id ="";
//				if(rows.get("LOG_ID")!=null){
//					log_id=rows.get("LOG_ID").toString();
//				}
//				String oper_type ="";
//				if(rows.get("OPER_TYPE")!=null){
//					oper_type=rows.get("OPER_TYPE").toString();
//				}
//				if (("001").equals(oper_type)) {
//					oper_type = "增加";
//	            } else if(("002").equals(oper_type)){
//	            	oper_type = "删除";
//	            }else{
//	            	oper_type = "修改";
//	            }
//				String menu_type ="";
//				if(rows.get("MENU_TYPE")!=null){
//					menu_type=rows.get("MENU_TYPE").toString();
//				}
//				if (("001").equals(menu_type)) {
//					menu_type = "用户";
//		        } else if(("002").equals(menu_type)){
//		        	menu_type = "角色";
//		        }else if(("003").equals(menu_type)){
//		        	menu_type = "菜单";
//		        }else{
//		        	menu_type = "组织";
//		        }
//				String user_id ="";//----
//				if(rows.get("USER_ID")!=null){
//					user_id=rows.get("USER_ID").toString();
//				}
//				String user_name ="";
//				if(rows.get("USER_NAME")!=null){
//					user_name=rows.get("USER_NAME").toString();
//				}
//				String create_date ="";
//				if(rows.get("CREATE_DATE")!=null){
//					create_date=rows.get("CREATE_DATE").toString();
//				}
//				
//		        row.createCell(0).setCellValue(log_id);
//		        row.createCell(1).setCellValue(oper_type);
//		        row.createCell(2).setCellValue(menu_type);
//		        row.createCell(3).setCellValue(user_id);
//		        row.createCell(4).setCellValue(user_name);
//		        row.createCell(5).setCellValue(create_date);
//
//		        num++;
//			}
//		//sheet.setColumnWidth(列数,  (int)256*宽度+184);
//		sheet.setColumnWidth(0,  (int)256*8+184);
//		sheet.setColumnWidth(1,  (int) (256*13+184));
//		sheet.setColumnWidth(2,  (int)256*13+184);
//		sheet.setColumnWidth(3,  (int)256*13+184);
//		sheet.setColumnWidth(4,  (int)256*13+184);
//		sheet.setColumnWidth(5,  (int)256*20+184);
//		}
//		
//	    //输出Excel文件
//	    OutputStream output=response.getOutputStream();
//	    response.reset();
//	    response.setHeader("Content-disposition", "attachment; filename=b.xls");//"+new String(filename.getBytes("gb2312"), "ISO8859-1")+"
//	    response.setContentType("application/msexcel");        
//	    wb.write(output);
//	    output.close();
//	}
//	
//	//登录日志导出
//	@RequestMapping(value = "/toexcel3", method = { RequestMethod.GET,RequestMethod.POST })
//	public @ResponseBody void excell3(HttpServletRequest request,HttpServletResponse response) throws Exception {		
//		Map<String, Object> params = RequestUtils.getParameterMap(request);
//		String flag = request.getParameter("flag");
//		String sqlid = request.getParameter("sqlid");//这是什么
//		List<Map<String, Object>> list = baseService.queryForList(sqlid, params);
//		String filename="";
//		//创建HSSFWorkbook对象(excel的文档对象)
//	    HSSFWorkbook wb = new HSSFWorkbook();
//		//建立新的sheet对象（excel的表单）
//		HSSFSheet sheet=wb.createSheet("管道数据表");
//		
//		HSSFRow row2=sheet.createRow(0);
//		
//		//取到map中的数据
//		Iterator<Map<String,Object>> it = list.iterator();
//
//		if(flag=="visit-log"||flag.equals("visit-log")){//-------
//			//filename= "管道数据表";
//		    row2.createCell(0).setCellValue("日志ID"); 
//		    row2.createCell(1).setCellValue("用户ID");
//		    row2.createCell(2).setCellValue("用户名");
//		    row2.createCell(3).setCellValue("登录IP");
//		    row2.createCell(4).setCellValue("登录描述");
//		    row2.createCell(5).setCellValue("登录时间");
//		    row2.createCell(6).setCellValue("退出时间");
//		    
//
//			int num =1;
//			while(it.hasNext()){
//				HSSFRow row=sheet.createRow(num);
//				Map<String,Object> rows = it.next();   
//				//取数据，非string类型，如果为空，可能会异常，if判断下
//				String log_id ="";
//				if(rows.get("LOG_ID")!=null){
//					log_id=rows.get("LOG_ID").toString();
//				}
//				String user_id ="";//----
//				if(rows.get("USER_ID")!=null){
//					user_id=rows.get("USER_ID").toString();
//				}
//				String user_name ="";
//				if(rows.get("USER_NAME")!=null){
//					user_name=rows.get("USER_NAME").toString();
//				}
//				String login_ip ="";
//				if(rows.get("LOGIN_IP")!=null){
//					login_ip=rows.get("LOGIN_IP").toString();
//				}
//				String login_desc ="";
//				if(rows.get("LOGIN_DESC")!=null){
//					login_desc=rows.get("LOGIN_DESC").toString();
//				}
//				String login_time ="";
//				if(rows.get("LOGIN_TIME")!=null){
//					login_time=rows.get("LOGIN_TIME").toString();
//				}
//				String out_time ="";
//				if(rows.get("OUT_TIME")!=null){
//					out_time=rows.get("OUT_TIME").toString();
//				}
//		        row.createCell(0).setCellValue(log_id);		        
//		        row.createCell(1).setCellValue(user_id);
//		        row.createCell(2).setCellValue(user_name);
//		        row.createCell(3).setCellValue(login_ip);
//		        row.createCell(4).setCellValue(login_desc);
//		        row.createCell(5).setCellValue(login_time);
//		        row.createCell(6).setCellValue(out_time);
//
//
//		        num++;
//			}
//		//sheet.setColumnWidth(列数,  (int)256*宽度+184);
//		sheet.setColumnWidth(0,  (int)256*8+184);
//		sheet.setColumnWidth(1,  (int)256*13+184);
//		sheet.setColumnWidth(2,  (int)256*13+184);
//		sheet.setColumnWidth(3,  (int)256*13+184);
//		sheet.setColumnWidth(4,  (int)256*20+184);
//		sheet.setColumnWidth(5,  (int)256*20+184);
//		sheet.setColumnWidth(6,  (int)256*20+184);
//
//		}
//		
//	    //输出Excel文件
//	    OutputStream output=response.getOutputStream();
//	    response.reset();
//	    response.setHeader("Content-disposition", "attachment; filename=c.xls");//"+new String(filename.getBytes("gb2312"), "ISO8859-1")+"
//	    response.setContentType("application/msexcel");        
//	    wb.write(output);
//	    output.close();
//	}
//	
//	//修改地点
//	@RequestMapping(value = "/toPOIexcel", method = { RequestMethod.GET,RequestMethod.POST })
//	public @ResponseBody void toPOIexcel(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		Map<String, Object> params = RequestUtils.getParameterMap(request);
//		String remarkSql="com.ztesoft.gis.dao.AlterPOIMapper.qryRemark";
//		List<Map<String, Object>> listRemark = baseService.queryForList(remarkSql, params);
//		Map<String, Object> remarkMap = listRemark.get(0);
//		Object objRemark = remarkMap.get("REMARK");
//		String remark = objRemark.toString();
//		String sqlid = request.getParameter("sqlid");
//		params.put("remark", remark);
//		List<Map<String, Object>> list = baseService.queryForList(sqlid, params);
//		//创建HSSFWorkbook对象(excel的文档对象)
//	    HSSFWorkbook wb = new HSSFWorkbook();
//		//建立新的sheet对象（excel的表单）
//		HSSFSheet sheet=wb.createSheet("POI记录表");
//		HSSFRow row2=sheet.createRow(0);
//		row2.createCell(0).setCellValue("地点名称"); 
//		row2.createCell(1).setCellValue("地市");
//	    row2.createCell(2).setCellValue("区县");
//	    row2.createCell(3).setCellValue("X");
//	    row2.createCell(4).setCellValue("Y");
//	    row2.createCell(5).setCellValue("地址信息");
//	    row2.createCell(6).setCellValue("详细描述");
//	    row2.createCell(7).setCellValue("时间");
//	    row2.createCell(8).setCellValue("操作类型/0添加地点，1位置信息错误，2批量添加，3删除地点");
//	    
//	    String city = "";
//	    String county = "";
//	    String data = "";
//	    String x = "";
//	    String y = "";
//	    String address = "";
//	    String description = "";
//	    String time = "";
//	    String type = "";
//	    //取到map中的数据
//		Iterator<Map<String,Object>> it = list.iterator();
//		int num =1;
//		while(it.hasNext()){
//			HSSFRow row=sheet.createRow(num);
//			Map<String,Object> rows = it.next();   
//			//取数据，非string类型，如果为空，可能会异常，if判断下
//			if(rows.get("C_TIME")!=null){
//				time=rows.get("C_TIME").toString();
//				int length = time.length();
//				time=time.substring(0, length-2);
//			}
//			city=rows.get("CITY").toString();
//			county=rows.get("COUNTY").toString();
//			data=rows.get("DATA").toString();
//			x=rows.get("POI_X").toString();
//			y=rows.get("POI_Y").toString();
//			address=rows.get("ADDRESS").toString();
//			description=rows.get("DESCRIPTION").toString();
//			type=rows.get("TYPE").toString();
//			row.createCell(0).setCellValue(data);
//	        row.createCell(1).setCellValue(city);
//	        row.createCell(2).setCellValue(county);
//	        row.createCell(3).setCellValue(x);
//	        row.createCell(4).setCellValue(y);
//	        row.createCell(5).setCellValue(address);
//	        row.createCell(6).setCellValue(description);
//	        row.createCell(7).setCellValue(time);
//	        row.createCell(8).setCellValue(type);
//	        num++;
//		}
//		//sheet.setColumnWidth(列数,  (int)256*宽度+184);
//		sheet.setColumnWidth(0,  (int)256*9+184);
//		sheet.setColumnWidth(1,  (int) (256*14+184));
//		sheet.setColumnWidth(2,  (int)256*20+184);
//		sheet.setColumnWidth(3,  (int)256*20+184);
//		sheet.setColumnWidth(4,  (int)256*24+184);
//		sheet.setColumnWidth(5,  (int)256*24+184);
//		sheet.setColumnWidth(6,  (int)256*14+184);
//		sheet.setColumnWidth(7,  (int)256*21+184);
//		sheet.setColumnWidth(8,  (int)256*10+184);
//		
//	    //输出Excel文件
//		Date now = new Date(); 
//		DateFormat dFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//        String nowString = dFormat.format(now);
//	    OutputStream output=response.getOutputStream();
//	    response.reset();
//	    response.setHeader("Content-disposition", "attachment; filename="+new String("POI记录表".getBytes("gb2312"), "ISO8859-1")+nowString+".xls");
//	    response.setContentType("application/msexcel");        
//	    wb.write(output);
//	    output.close();
//	}
//}