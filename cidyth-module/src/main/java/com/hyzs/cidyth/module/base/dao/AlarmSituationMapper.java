package com.hyzs.cidyth.module.base.dao;

import com.hyzs.cidyth.module.base.entity.AlarmSituation;
import com.hyzs.psd.gafa.daf.mybatis.tex.DataSourceName;
import com.hyzs.psd.gafa.daf.mybatis.tk.common.Mapper;

@DataSourceName(name = "masterSqlSessionFactory")
public interface AlarmSituationMapper extends Mapper<AlarmSituation> {
}