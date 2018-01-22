package com.galaxy.im.business.idea.dao;

import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.BaseDaoImpl;

@Repository
public class IdeaDaoImpl extends BaseDaoImpl<Test, Long> implements IIdeaDao{

}
