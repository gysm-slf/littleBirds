package cn.littleBird.demo.old;//package com.ztesoft.gis.controller;
//
//import java.io.InputStream;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.DateUtil;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.ss.usermodel.WorkbookFactory;
//import org.apache.poi.ss.util.NumberToTextConverter;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.commons.CommonsMultipartFile;
//
//import com.ztesoft.gis.base.service.BaseService;
//import com.ztesoft.gis.common.GisConsts;
//import com.ztesoft.gis.model.UserInfo;
//import com.ztesoft.gis.utils.RequestUtils;
//
///**
// * 
// * @author li.zhuang
// * 行政道路覆盖专题图的导入excel表，数据插入到数据库并且验证数据
// * 只有 city-road-thematic 一个页面调用
// * 
// * 批量导入地点信息 lotAdd.html
// *
// */
//@Controller
//@RequestMapping("/pipeMap")
//public class PipeMapController {
//	
//	@Autowired
//    protected SqlSessionTemplate session;
//	@Autowired
//	protected BaseService baseService;
//	
//	@ResponseBody  
//	@RequestMapping(value = "/improtData", method = { RequestMethod.POST},consumes = MediaType.MULTIPART_FORM_DATA_VALUE)  
//	public JSONObject improtData(@RequestParam("uploadFile") CommonsMultipartFile uploadFile,HttpServletRequest request, HttpServletResponse response) {  
//		
//		JSONObject	obj = new JSONObject();  //返回结果
//		try {
//			
//			HttpClient client=new DefaultHttpClient();
//	        HttpPost hp=new HttpPost("http://10.216.129.39:6080/arcgis/rest/services/HB_QUERY_Map/MapServer/42/query");
//			
//	        UserInfo user = (UserInfo) request.getSession().getAttribute(GisConsts.USER_SESSION_KEY);
//			String userId = user.getUser_id();
//	        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
//	        List<Map<String, Object>> resList = new ArrayList<Map<String,Object>>();
//	        int btnType=Integer.parseInt(request.getParameter("btnType"));
//	        InputStream is = null;
//	        int imp_remark; //插入标记，同一批导入，remark相同,
//	        String start_lng= "";
//        	String start_lat= "";
//        	String end_lng= "";
//        	String end_lat= "";
//        	String street= "";
//        	String city= "";
//        	String county ="";
//        	String state = "";
//	        
//	        String remark = session.selectOne("com.ztesoft.gis.dao.PipeImportMapper.getImp_remark");
//	        if(remark==null||remark.equals("")){//初始化remark的值
//	        	imp_remark=1;
//	        }else{
//	        	imp_remark=Integer.parseInt(remark)+1;
//	        }
//
//	        is = uploadFile.getInputStream(); //文件流 
//        	Workbook workbook = WorkbookFactory.create(is);  
//	    	int sheetCount = workbook.getNumberOfSheets(); //Sheet的数量
//	    	//存储每条数据容器 
//	    	Map<String, Object> rowMap = null;
//	    	String colValue = null;
//	    	//遍历每个Sheet 
//	    	for (int s = 0; s < sheetCount; s++) { 
//	            Sheet sheet = workbook.getSheetAt(s); 
//	            int rowCount = sheet.getLastRowNum(); //获取总行数
//	            //Row firstRow = sheet.getRow(1);//取第0行的字段名
//	            //遍历每一行 
//	            for (int r = 2; r <= rowCount; r++) { //第一行为标题
//	            	Row row = sheet.getRow(r);
//	            	if(row!=null){
//	            		rowMap = new HashMap<String, Object>();
//		            	int firstCellNum = row.getFirstCellNum();//获取第一列
//		            	int lastCellNum = row.getLastCellNum();//获取最后一列
//		            	for(int j = firstCellNum;j < lastCellNum;j++){//取每一列的值
//		            		colValue = row.getCell(j) != null ? getCellValue(row.getCell(j)) : "";
//		            		rowMap.put(getTitle(j), colValue);
//				            list.add(rowMap);
//		            	}
//		            	rowMap.put("imp_remark", imp_remark);
//		            	rowMap.put("user_id", userId);
//		            	rowMap.put("imp_type",btnType);
//		            	start_lng = (String) rowMap.get("start_lng");
//		            	start_lat = (String) rowMap.get("start_lat");
//		            	end_lng = (String) rowMap.get("end_lng");
//		            	end_lat = (String) rowMap.get("end_lat");
//		            	street = (String) rowMap.get("street");
//		            	city = (String) rowMap.get("company");
//		            	county = (String) rowMap.get("county");
//		            	resList.add(rowMap);
//		            	state="0";
//		            	if(street.indexOf("(")!=-1){  //判断街道名是否有括号，有了去掉
//		            		street=street.substring(0,street.indexOf("("));
//		            	}
//		            	//请求服务
//		            	List<NameValuePair> params = new ArrayList<NameValuePair>();  
//		            	String geometry = start_lng+","+start_lat+","+end_lng+","+end_lat;
//		            	String str =" 1=1 and data like '%"+street+"%' and city like '%"+city+"%' and county like '%"+county+"%'";
//		            	params.add(new BasicNameValuePair("geometry",geometry));
//		     	        params.add(new BasicNameValuePair("where",str));
//		     	        params.add(new BasicNameValuePair("spatialRel","esriSpatialRelIntersects"));
//		     	        params.add(new BasicNameValuePair("geometryType","esriGeometryEnvelope"));
//		     	        params.add(new BasicNameValuePair("returnGeometry","true"));
//		     	        params.add(new BasicNameValuePair("outFields","*"));
//		     	        params.add(new BasicNameValuePair("f", "json"));
//		     	        
//		     	        HttpEntity entity = new UrlEncodedFormEntity(params,"utf-8");
//		     	        hp.setEntity(entity); 
//		     	        long startDate = System.currentTimeMillis();
//		     	        HttpResponse response1=client.execute(hp);
//		     	        long endDate = System.currentTimeMillis();
//		     	        System.out.println("请求服务接口用时  -------->"+(endDate-startDate));
//		     	        if(response1.getStatusLine().getStatusCode()==200){// 200请求成功
//			   	            String result=EntityUtils.toString(response1.getEntity());//请求成功，但是参数有误会返回{"error":{"code":
//			   	            String string = "error";
//			   	            if(result.indexOf(string)!=-1){//条件为真，输入参数有误
//			   	            	System.out.println("error:Unable to complete operation"); 
//			   	            }else{
//			   	            	JSONObject jsonArray = JSONObject.parseObject(result); 
//			   	            	JSONArray features = JSON.parseArray((jsonArray.get("features").toString()));
//			   	            	int num = features.size();
//			   	            	if(num>=1){
//			   	            		state="1";
//			   	            	}
//			   	            }
//		     	        }
//		     	       rowMap.put("state",state);
//	            	}
//	            } 
//            } 
//        	is.close();   
//        	int num = session.insert("com.ztesoft.gis.dao.PipeImportMapper.insertData",list);
//        	obj.put("data", resList);
//        	obj.put("imp_remark", imp_remark);
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	    }  
//	    return obj;  
//	} 
//	
//	/**
//	 * 获得单元格表头信息，写固定了，switch单元格的索引
//	 * @param i
//	 * @return
//	 */
//	public String getTitle(int i){
//		String result = "";
//		switch(i){
//		case 0:
//			result="company";
//		    break;
//		case 1:
//			result="county";
//		    break;
//		case 2:
//			result="street";
//		    break;
//		case 3:
//			result="start_site";
//		    break;
//		case 4:
//			result="start_lng";
//		    break;
//		case 5:
//			result="start_lat";
//		    break;
//		case 6:
//			result="end_site";
//		    break;
//		case 7:
//			result="end_lng";
//		    break;
//		case 8:
//			result="end_lat";
//		    break;
//		case 9:
//			result="building_type";
//		    break;
//		case 10:
//			result="pipe_length";
//		    break;
//		case 11:
//			result="pipe_num";
//		    break;
//		case 12:
//			result="child_num";
//		    break;
//		case 13:
//			result="pipe_model";
//		    break;
//		case 14:
//			result="compute_value";
//		    break;
//		case 15:
//			result="pipe_type";
//		    break;
//		case 16:
//			result="used";
//		    break;
//		case 17:
//			result="stage";
//		    break;
//		}
//		
//		return result;
//	}
//	
//	
//	public String getCellValue(Cell cell){
//    	SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd"); 
//    	String cellValue = "";  
//        if (cell != null) {
//        	switch(cell.getCellType()) { 
//	            case Cell.CELL_TYPE_STRING: //文本 
//	            	cellValue = cell.getStringCellValue(); 
//	            	break; 
//	            case Cell.CELL_TYPE_NUMERIC: //数字、日期 
//	            	if(DateUtil.isCellDateFormatted(cell)) { 
//	            		cellValue = fmt.format(cell.getDateCellValue()); //日期型 
//	            	} else { 
//	            		cellValue = NumberToTextConverter.toText(cell.getNumericCellValue()); //数字 
//	            	} 
//	             	break; 
//	            case Cell.CELL_TYPE_BOOLEAN: //布尔型 
//	            	cellValue = String.valueOf(cell.getBooleanCellValue()); 
//	            	break; 
//	            case Cell.CELL_TYPE_BLANK: //空白 
//	            	cellValue = null; 
//	            	break; 
//	            case Cell.CELL_TYPE_FORMULA://公式
//	    			try {
//	    				cellValue = cell.getStringCellValue();
//	    			} catch (IllegalStateException e) {
//	    				cellValue = String.valueOf(cell.getNumericCellValue());
//	    			}
//	            	break;
//	            default: 
//	            	cellValue = " "; 
//        	} 
//        } else {
//        	cellValue = " ";
//        }
//        return cellValue;
//    }
//	
//	
//	/**
//	 * 
//	 * 批量导入poi页面   lotAdd
//	 * 
//	 */
//	@ResponseBody  
//	@RequestMapping(value = "/improtPOI", method = { RequestMethod.POST},consumes = MediaType.MULTIPART_FORM_DATA_VALUE)  
//	public JSONObject improtPOI(@RequestParam("uploadFile") CommonsMultipartFile uploadFile,HttpServletRequest request, HttpServletResponse response) {  
//		JSONObject	obj = new JSONObject();  //返回结果
//		String remarkSql="com.ztesoft.gis.dao.AlterPOIMapper.qryRemark";
//		Map<String, Object> params = RequestUtils.getParameterMap(request);
//		List<Map<String, Object>> listRemark = baseService.queryForList(remarkSql, params);
//		Map<String, Object> remarkMap = listRemark.get(0);
//		String remark="0";
//		if(remarkMap==null){
//			 remark = "1";
//		}else{
//			Object objRemark = remarkMap.get("REMARK");
//			 remark = objRemark.toString();
//		}
//		
//		try {
//			
//	        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
//	        List<Map<String, Object>> resList = new ArrayList<Map<String,Object>>();
//	        InputStream is = null;
//	        
//
//	        is = uploadFile.getInputStream(); //文件流 
//        	Workbook workbook = WorkbookFactory.create(is);  
//	    	int sheetCount = workbook.getNumberOfSheets(); //Sheet的数量
//	    	//存储每条数据容器 
//	    	Map<String, Object> rowMap = null;
//	    	String colValue = null;
//	    	//遍历每个Sheet 
//	    	for (int s = 0; s < sheetCount; s++) { 
//	            Sheet sheet = workbook.getSheetAt(s); 
//	            int rowCount = sheet.getLastRowNum(); //获取总行数
//	            //Row firstRow = sheet.getRow(1);//取第0行的字段名
//	            //遍历每一行 
//	            for (int r = 1; r <= rowCount; r++) { //第一行为标题
//	            	Row row = sheet.getRow(r);
//	            	if(row!=null){
//	            		rowMap = new HashMap<String, Object>();
//		            	int firstCellNum = row.getFirstCellNum();//获取第一列
//		            	int lastCellNum = row.getLastCellNum();//获取最后一列
//		            	rowMap.put("remark",remark);
//		            	rowMap.put("is_submit",'0');
//		            	rowMap.put("type",'2');
//		            	rowMap.put("userID1",params.get("userID"));
//		            	rowMap.put("userID",params.get("userID"));
//		            	for(int j = firstCellNum;j < lastCellNum;j++){//取每一列的值
//		            		colValue = row.getCell(j) != null ? getCellValue(row.getCell(j)) : "";
//		            		rowMap.put(getPOITitle(j), colValue);
//				            list.add(rowMap);
//		            	}
//	            	}
//	            } 
//            } 
//        	is.close();   
//        	int num = session.insert("com.ztesoft.gis.dao.AlterPOIMapper.excelToData",list);
//        	if(num>0){
//        		System.out.println(num);
//        	}
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	    }  
//	    return obj;  
//	} 
//	
//	/**
//	 * 批量导入lotAdd
//	 * @param i
//	 * @return
//	 */
//	public String getPOITitle(int i){
//		String result = "";
//		switch(i){
//		case 0:
//			result="data";
//		    break;
//		case 1:
//			result="city";
//		    break;
//		case 2:
//			result="county";
//		    break;
//		case 3:
//			result="poi_x";
//		    break;
//		case 4:
//			result="poi_y";
//		    break;
//		case 5:
//			result="address";
//		    break;
//		case 6:
//			result="description";
//		    break;
//		}
//		
//		return result;
//	}
//
//}
//
//
