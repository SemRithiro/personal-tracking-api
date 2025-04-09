package com.rithiro.personaltracking.modules.eventGroup.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.rithiro.personaltracking.modules.eventGroup.models.databases.EventGroup;

@Mapper
public interface EventGroupMapper {
    public List<EventGroup> getList(@Param("id") Integer id);
}
