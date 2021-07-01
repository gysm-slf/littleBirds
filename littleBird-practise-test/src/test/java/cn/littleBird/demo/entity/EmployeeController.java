package cn.littleBird.demo.entity;//package com.su.crud.controller;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.su.crud.bean.Employee;
//import com.su.crud.bean.Msg;
//import com.su.crud.service.EmployeeService;
//
//@Controller
//public class EmployeeController {
//
//	// GET:查询                      POST:保存                         PUT:修改                             DELETE:删除
//	// /emp/{id}      /emp            /emp/{id}         /emp/{id}
//	
//	@Autowired
//	EmployeeService employeeService;
//	
//	
//	@RequestMapping(value="/delEmp/{ids}",method=RequestMethod.DELETE)
//	@ResponseBody
//	public Msg delEmp(@PathVariable("ids")String ids) {
//		//单个批量二合一删除
//		if(ids.contains("-")) {
//			String[] split = ids.split("-");
//			List<Integer> list=new ArrayList<>();
//			Integer empId=0;
//			for(int i=0;i<split.length;i++) {
//				empId=Integer.parseInt(split[i]);
//				list.add(empId);
//			}
//			System.out.println(split[0]);
//			System.out.println(split[1]);
//			//批量删除
//			employeeService.deleteBatch(list);
//		}else {
//			Integer empId=Integer.parseInt(ids);
//			employeeService.deleteEmp(empId);
//		}
//		return Msg.success();
//	}
//	
//	
//	
//	
//	@RequestMapping(value="/deleteEmp/{id}",method=RequestMethod.DELETE)
//	@ResponseBody
//	public Msg deleteEmp(@PathVariable("id")Integer id) {
//		employeeService.deleteEmp(id);
//		return Msg.success();
//	}
//	
//	
//	/*
//	 * 如果直接发送ajax=PUT请求
//	 * 封装的数据
//	 * Employee
//	 * [empId=1060, empName=null, gender=null, email=null, dId=null, department=null]
//	 * 
//	 * 问题：请求体中有数据
//	 * 但是Employee对象封装不上
//	 * 
//	 * 原因：Tomcat：
//	 * 	1.将请求体中的数据，封装一个map。
//	 * 	2.request.getParameter("empName")就会从这个map中取值
//	 * 	3.SpringMVC封装pojo对象的时候
//	 * 			会把POJO中每个属性的值，request.getParameter("email");
//	 * AJAX发送PUT请求引发的血案：
//	 * 		PUT请求，请求体中的数据，request,getParameter("empName")拿不到
//	 * 		tomcat一看是put请求不会封装请求体中的数据为map，只有POST形式的请求才会封装请求体作为map
//	 * 
//	 * */
//	@RequestMapping(value="/updateEmp/{empId}",method=RequestMethod.PUT)
//	@ResponseBody
//	public Msg updateEmp(Employee employee) {
//		System.out.println(employee);
//		employeeService.updateEmp(employee);
//		return Msg.success();
//	}
//	
//	
//	
//	
//	@RequestMapping(value="/getEmp/{id}",method=RequestMethod.GET)
//	@ResponseBody	//表明此参数是从路径中获取值
//	public Msg getEmp(@PathVariable("id")Integer id) {
//		Employee emp = employeeService.getEmp(id);
//		return Msg.success().add("emp", emp);
//	}
//	
//	@RequestMapping("/checkUser")
//	@ResponseBody		//取出ajax请求中    data的值
//	public Msg checkUser(@RequestParam("empName")String empName) {
//		//先判断用户名是否是合法的表达式；
//		String regx="(^[a-zA-Z0-9_-]{3,16}$)|([\\u2E80-\\u9FFF]{2,5})";
//		if(!empName.matches(regx)) {
//			return Msg.fail().add("controller_validate", "用户名必须是2-5位中文或3-16位英文和数字的组合字符");
//		}
//		//数据库用户名重复校验
//		boolean b=employeeService.checkUser(empName);
//		if(b) {
//			return Msg.success();
//		}else {
//			return Msg.fail().add("controller_validate", "用户名已存在");
//		}
//	}
//	
//	
//	@RequestMapping("/showEmps")
//	@ResponseBody
//	public Msg getEmpsWithJson(@RequestParam(value="pageNumber",defaultValue="1")Integer pageNumber,
//			Model model) {
//		//这不是一个分页
//		//引入PageHelper插件
//		//在查询之前只需要调用
//		//来到第几页，每页几条记录
//		PageHelper.startPage(pageNumber,5);
//		
//		//startPage后面紧跟的这个查询就是一个分页查询
//		List<Employee> emps = employeeService.getAll();
//		//使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了
//		//封装了详细的分页信息，包括有我们查询出的数据,传入连续显示的页数
//		PageInfo page = new PageInfo(emps,6);
//		
//		return Msg.success().add("pageInfo", page);
//	}
//	/*
//	 * @ResponseBody返回json数据
//	 * 只有当post请求的时候
//	 * 
//	 * 员工保存
//	 * 1、支持JSR303校验
//	 * 2、导入Hibernate-Validator
//	 * 
//	 * 
//	 * */
//	@RequestMapping(value="/emp",method=RequestMethod.POST)
//	@ResponseBody
//	public Msg addEmp(@Valid Employee employee,BindingResult result) {
//		if(result.hasErrors()) {
//			//校验失败，应该返回失败，在模态框中显示校验失败的错误信息
//			Map<String, Object> map = new HashMap<>();
//			List<FieldError> fieldErrors = result.getFieldErrors();
//			for(FieldError error:fieldErrors) {
//				System.out.println("错误的字段名"+error.getField());
//				System.out.println("错误信息"+error.getDefaultMessage());
//				map.put(error.getField(), error.getDefaultMessage());
//			}
//			return Msg.fail().add("error", map);
//		}else {
//		employeeService.addEmp(employee);
//		return Msg.success();
//	}
//	}
//	
//	
//	@RequestMapping("/showEmpsLow")
//	public String getEmps(@RequestParam(value="pageNumber",defaultValue="1")Integer pageNumber,
//			Model model) {
//		//这不是一个分页
//		//引入PageHelper插件
//		//在查询之前只需要调用
//		//来到第几页，每页几条记录
//		PageHelper.startPage(pageNumber,5);
//		
//		//startPage后面紧跟的这个查询就是一个分页查询
//		List<Employee> emps = employeeService.getAll();
//		//使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了
//		//封装了详细的分页信息，包括有我们查询出的数据,传入连续显示的页数
//		PageInfo page = new PageInfo(emps,6);
//		//请求域
//		model.addAttribute("pageInfo",page);
//		return "list";
//	}
//
//}
