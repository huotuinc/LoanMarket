package com.huotu.loanmarket.service.service.impl;

import com.huotu.loanmarket.service.base.AbstractCrudService;
import com.huotu.loanmarket.service.base.JpaCrudRepository;
import com.huotu.loanmarket.service.entity.AppVersion;
import com.huotu.loanmarket.service.entity.LoanCategory;
import com.huotu.loanmarket.service.repository.AppVersionRepository;
import com.huotu.loanmarket.service.service.AppVersionService;
import org.eclipse.persistence.jpa.jpql.parser.TrimExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppVersionServiceImpl extends AbstractCrudService<AppVersion ,Integer> implements AppVersionService {
    AppVersionRepository appVersionRepository;

    @Autowired
    public AppVersionServiceImpl(AppVersionRepository repository) {
        super(repository);
        this.appVersionRepository= repository;
    }

    @Override
    public AppVersion check(int versionCode) {

        Specification<AppVersion> specification = (root, criteriaQuery, criteriaBuilder) -> {
//            List<Predicate> predicates=new ArrayList<>();
//            predicates.add( criteriaBuilder.greaterThan( root.get("versionCode").as(Integer.class), versionCode ) );
//
            return criteriaBuilder.greaterThan(root.get("versionCode").as(Integer.class), versionCode);
        };

        Sort sort = new Sort(Sort.Direction.DESC, "versionCode");

        List<AppVersion> list = this.appVersionRepository.findAll(specification, sort);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;

    }
}
