package com.galaxy.im.business.hologram.project.dao;

import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.BaseDaoImpl;

@Repository
public class ProjectDaoImpl extends BaseDaoImpl<Test, Long> implements IProjectDao{

}
