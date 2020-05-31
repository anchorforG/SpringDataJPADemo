package com.example.springdatajpa;

import com.example.springdatajpa.entity.Article;
import com.example.springdatajpa.entity.Role;
import com.example.springdatajpa.entity.User;
import com.example.springdatajpa.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.util.List;

/**
 *
 */
@SpringBootTest
class UserRoleArticleTest {

	@Test
	void contextLoads() {
	}

	@Resource
	private UserRepository userRepository;
	@Resource
	private RoleRepository roleRepository;
	@Resource
	private ArticleRepository articleRepository;

	/**
	 *
	 */
	@Test
	public void test() {
		String articleId = "";
		String roleId = "";
		List<User> all = userRepository.findAll((Specification<User>) (root, query, criteriaBuilder) -> {
			// 方式1
			ListJoin<User, Article> articleJoin = root.join(root.getModel().getList("articleList", Article.class), JoinType.LEFT);
			SetJoin<User, Role> roleJoin = root.join(root.getModel().getSet("roles", Role.class), JoinType.LEFT);
			// 方式2
			//Join<User, Article> articleJoin = root.join("articleList", JoinType.LEFT);
			//Join<User, Role> roleJoin = root.join("roles", JoinType.LEFT);

			Predicate predicate = criteriaBuilder.or(
					criteriaBuilder.equal(articleJoin.get("id"), articleId),
					criteriaBuilder.equal(roleJoin.get("id"), roleId)
			);
			return predicate;
		});

		for (User user : all) {
		  System.out.println(user);
		}
	}

}
