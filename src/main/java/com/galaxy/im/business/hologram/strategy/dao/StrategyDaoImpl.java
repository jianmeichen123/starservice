package com.galaxy.im.business.hologram.strategy.dao;

import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.Test;
import com.galaxy.im.common.db.BaseDaoImpl;

@Repository
public class StrategyDaoImpl extends BaseDaoImpl<Test, Long> implements IStrategyDao{

}
