package com.example.springdatajpa;

import com.example.springdatajpa.entity.Grade;
import com.example.springdatajpa.entity.RoleInfo;
import com.example.springdatajpa.entity.Student;
import com.example.springdatajpa.entity.UserInfo;
import com.example.springdatajpa.repository.RoleInfoRepository;
import com.example.springdatajpa.repository.StudentRepository;
import com.example.springdatajpa.repository.UserTestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Optional;

@SpringBootTest
class SpringdatajpaApplicationTests {

	@Test
	void contextLoads() {
	}

	@Resource
	private UserTestRepository userRepository;
	@Resource
	private StudentRepository studentRepository;
	@Resource
	private RoleInfoRepository roleRepository;

	@Test
	public void test(){
		long count = userRepository.count();
    	System.out.println(count);
	}

	/**
	 * 一对一添加：添加学生同时添加年级
	 */
	@Test
	public void testAdd(){
		//创建年级对象
		Grade grade = new Grade();
		grade.setGradeName("一年级");
		//创建学生对象
		Student student = new Student();
		student.setStudentName("AAA");
		//设置关联关系
		student.setGrade(grade);
		//保存
		studentRepository.save(student);
	}

	/**
	 * 根据学生 ID 查询学生，同时查询年级
	 */
	@Test
	public void testSearch(){
		Optional<Student> student = studentRepository.findById(1);
		System.out.println("学生信息："+student);
		System.out.println("年级信息："+student.get().getGrade());
	}

	/**
	 * 级联添加：添加角色同时添加用户
	 */
	@Test
	public void testUserRole(){
		//创建角色对象
		RoleInfo roles = new RoleInfo();
		roles.setRoleName("DDD");

		//创建两个用户
		UserInfo users1 = new UserInfo();
		users1.setUserName("AAAA");
		users1.setRoles(roles);
		UserInfo users2 = new UserInfo();
		users2.setUserName("BBBB");
		users2.setRoles(roles);
		//设置关联关系
		roles.getUsers().add(users1);
		roles.getUsers().add(users2);
		//级联保存用户
		roleRepository.save(roles);
	}
	/**
	 * 级联查询：查询角色同时查询角色下的用户列表（关闭延迟加载）
	 */
	@Test
	public void testUserRoleSearch(){
		Optional<RoleInfo> role = roleRepository.findById(1);
		System.out.println("角色名称："+role.get().getRoleName());
		for (UserInfo user :role.get().getUsers()){
			System.out.println(user);
		}
	}



}
